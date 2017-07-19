package org.hsweb.printer.fx.utils.scan;

/**
 * Created by wangziqing on 16/3/3.
 */

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

import static org.hsweb.printer.fx.utils.scan.Validate.checkNotNull;


/**
 * Scans the source of a {@link ClassLoader} and finds all loadable classes and
 * resources.
 *
 * @author Ben Yu
 * @since 14.0
 */
public final class ClassPath {
    //private static final Logger LOG = LoggerFactory.getLogger(ClassPath.class);


    private static final String CLASS_FILE_NAME_EXTENSION = ".class";

    private final Set<ResourceInfo> resources;

    private ClassPath(Set<ResourceInfo> resources) {
        this.resources = resources;
    }

    /**
     * Returns a {@code ClassPath} representing all classes and resources
     * loadable from {@code classloader} and its parent class loaders.
     * <p>
     * <p>
     * Currently only {@link URLClassLoader} and only {@code file://} urls are
     * supported.
     *
     * @throws IOException if the attempt to read class path resources (jar files or
     *                     directories) failed.
     */
    public static ClassPath from(ClassLoader classloader) throws IOException {
        Scanner scanner = new Scanner();
        for (Map.Entry<URI, ClassLoader> entry : getClassPathEntries(
                classloader).entrySet()) {
            scanner.scan(entry.getKey(), entry.getValue());
        }
        return new ClassPath(scanner.getResources());
    }

    /**
     * Returns all resources loadable from the current class path, including the
     * class files of all loadable classes but excluding the
     * "META-INF/MANIFEST.MF" file.
     */
    public Set<ResourceInfo> getResources() {
        return resources;
    }

    /**
     * Returns all top level classes loadable from the current class path.
     */
    public Set<ClassInfo> getTopLevelClasses() {
        Set<ClassInfo> classInfos = new HashSet<ClassInfo>();
        for (ResourceInfo resourceInfo : resources) {
            if (resourceInfo instanceof ClassInfo) {
                ClassInfo classInfo = (ClassInfo) resourceInfo;
                classInfos.add(classInfo);
            }
        }
        return classInfos;
    }

    /**
     * Returns all top level classes whose package name is {@code packageName}.
     */
    public Set<ClassInfo> getTopLevelClasses(String packageName) {
        checkNotNull(packageName);
        Set<ClassInfo> builder = new HashSet<ClassInfo>();
        for (ClassInfo classInfo : getTopLevelClasses()) {
            if (classInfo.getPackageName().equals(packageName)) {
                builder.add(classInfo);
            }
        }
        return builder;
    }

    /**
     * Returns all top level classes whose package name is {@code packageName}
     * or starts with {@code packageName} followed by a '.'.
     */
    public Set<ClassInfo> getTopLevelClassesRecursive(String packageName) {
        checkNotNull(packageName);
        String packagePrefix = packageName + '.';
        Set<ClassInfo> builder = new HashSet<ClassInfo>();
        for (ClassInfo classInfo : getTopLevelClasses()) {
            if (classInfo.getName().startsWith(packagePrefix)) {
                builder.add(classInfo);
            }
        }
        return builder;
    }


    /**
     * Represents a class path resource that can be either a class file or any
     * other resource file loadable from the class path.
     *
     * @since 14.0
     */
    public static class ResourceInfo {
        private final String resourceName;
        final ClassLoader loader;

        static ResourceInfo of(String resourceName, ClassLoader loader) {
            if (resourceName.endsWith(CLASS_FILE_NAME_EXTENSION)
                    && !resourceName.contains("$")) {
                return new ClassInfo(resourceName, loader);
            } else {
                return new ResourceInfo(resourceName, loader);
            }
        }

        ResourceInfo(String resourceName, ClassLoader loader) {
            this.resourceName = checkNotNull(resourceName);
            this.loader = checkNotNull(loader);
        }

        /**
         * Returns the url identifying the resource.
         */
        public final URL url() {
            return checkNotNull(loader.getResource(resourceName),
                    "Failed to load resource: %s", resourceName);
        }

        /**
         * Returns the fully qualified name of the resource. Such as
         * "com/mycomp/foo/bar.txt".
         */
        public final String getResourceName() {
            return resourceName;
        }

        @Override
        public int hashCode() {
            return resourceName.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ResourceInfo) {
                ResourceInfo that = (ResourceInfo) obj;
                return resourceName.equals(that.resourceName)
                        && loader == that.loader;
            }
            return false;
        }

        // Do not change this arbitrarily. We rely on it for sorting
        // ResourceInfo.
        @Override
        public String toString() {
            return resourceName;
        }
    }

    /**
     * Represents a class that can be loaded through {@link #load}.
     *
     * @since 14.0
     */
    public static final class ClassInfo extends ResourceInfo {
        private final String className;

        ClassInfo(String resourceName, ClassLoader loader) {
            super(resourceName, loader);
            this.className = getClassName(resourceName);
        }

        /**
         * Returns the package name of the class, without attempting to load the
         * class.
         */
        public String getPackageName() {
            return Reflect.getPackageName(className);
        }

        /**
         * Returns the simple name of the underlying class as given in the
         * source code.
         */
        public String getSimpleName() {
            String packageName = getPackageName();
            if (packageName.isEmpty()) {
                return className;
            }
            // Since this is a top level class, its simple name is always the
            // part after package name.
            return className.substring(packageName.length() + 1);
        }

        /**
         * Returns the fully qualified name of the class.
         */
        public String getName() {
            return className;
        }

        /**
         * Loads (but doesn't link or initialize) the class.
         *
         * @throws LinkageError when there were errors in loading classes that this class
         *                      depends on. For example, {@link NoClassDefFoundError}.
         */
        public Class<?> load() {
            try {
                return loader.loadClass(className);
            } catch (ClassNotFoundException e) {
                // Shouldn't happen, since the class name is read from the class
                // path.
                throw new IllegalStateException(e);
            }
        }

        @Override
        public String toString() {
            return className;
        }
    }

    static Map<URI, ClassLoader> getClassPathEntries(ClassLoader classloader) {
        LinkedHashMap<URI, ClassLoader> entries = new LinkedHashMap<URI, ClassLoader>();
        // Search parent first, since it's the order ClassLoader#loadClass()
        // uses.
        ClassLoader parent = classloader.getParent();
        if (parent != null) {
            entries.putAll(getClassPathEntries(parent));
        }
        if (classloader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) classloader;
            for (URL entry : urlClassLoader.getURLs()) {
                URI uri;
                try {
                    uri = entry.toURI();
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException(e);
                }
                if (!entries.containsKey(uri)) {
                    entries.put(uri, classloader);
                }
            }
        }
        return new HashMap<URI, ClassLoader>(entries);
    }

    static final class Scanner {

        private final Set<ResourceInfo> resources = new HashSet<ResourceInfo>();
        private final Set<URI> scannedUris = new HashSet<URI>();

        Set<ResourceInfo> getResources() {
            return resources;
        }

        void scan(URI uri, ClassLoader classloader) throws IOException {
            if (uri.getScheme().equals("file") && scannedUris.add(uri)) {
                scanFrom(new File(uri), classloader);
            }
        }

        void scanFrom(File file, ClassLoader classloader) throws IOException {
            if (!file.exists()) {
                return;
            }
            if (file.isDirectory()) {
                scanDirectory(file, classloader);
            }
        }

        private void scanDirectory(File directory, ClassLoader classloader) {
            scanDirectory(directory, classloader, "");
        }

        private void scanDirectory(File directory, ClassLoader classloader,
                                   String packagePrefix) {
            File[] files = directory.listFiles();
            if (files == null) {
                //   LOG.warn("Cannot read directory {}", directory);
                // IO error, just skip the directory
                return;
            }
            for (File f : files) {
                String name = f.getName();
                if (f.isDirectory()) {
                    scanDirectory(f, classloader, packagePrefix + name + "/");
                } else {
                    String resourceName = packagePrefix + name;
                    if (!resourceName.equals(JarFile.MANIFEST_NAME)) {
                        resources.add(ResourceInfo
                                .of(resourceName, classloader));
                    }
                }
            }
        }

        /**
         * Returns the absolute uri of the Class-Path entry value as specified
         * in <a href=
         * "http://docs.oracle.com/javase/6/docs/technotes/guides/jar/jar.html#Main%20Attributes"
         * > JAR File Specification</a>. Even though the specification only
         * talks about relative urls, absolute urls are actually supported too
         * (for example, in Maven surefire plugin).
         */
        static URI getClassPathEntry(File jarFile, String path)
                throws URISyntaxException {
            URI uri = new URI(path);
            if (uri.isAbsolute()) {
                return uri;
            } else {
                return new File(jarFile.getParentFile(), path.replace('/',
                        File.separatorChar)).toURI();
            }
        }
    }

    static String getClassName(String filename) {
        int classNameEnd = filename.length()
                - CLASS_FILE_NAME_EXTENSION.length();
        return filename.substring(0, classNameEnd).replace('/', '.');
    }
}