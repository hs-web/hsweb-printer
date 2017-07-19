package org.hsweb.printer.fx.utils.scan; /**
 * Created by wangziqing on 16/2/29.
 */


import eu.infomas.annotation.AnnotationDetector;
import eu.infomas.annotation.AnnotationDetector.Reporter;
import eu.infomas.annotation.AnnotationDetector.TypeReporter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 注解类扫描工具.
 */
public class AnnotationClassScanner {
   // private static final Logger LOG = LoggerFactory.getLogger(AnnotationClassScanner.class);


    /**
     * 扫描类注解.
     * @param annotationClass
     * @param packages
     * @return
     */
    public static Set<Class<?>> scan(
            final Class<? extends Annotation> annotationClass,
            String... packages) {
        final Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        final Reporter reporter = new TypeReporter() {

            @SuppressWarnings("unchecked")
            @Override
            public Class<? extends Annotation>[] annotations() {
                return new Class[] { annotationClass };
            }

            @Override
            public void reportTypeAnnotation(
                    Class<? extends Annotation> annotation, String className) {
                loadClass(classes, className);
            }

        };
        return startScan(classes, reporter, packages);
    }



    private static Set<Class<?>> startScan(final Set<Class<?>> classes,
                                           final Reporter reporter, String... packageNames) {
        final AnnotationDetector cf = new AnnotationDetector(reporter);
        try {
            if (packageNames.length == 0) {
                // 解决在web容器下扫描不到类的问题.
                URL url = Thread.currentThread().getContextClassLoader()
                        .getResource("");
                File file = new File(url.getPath());
                File[] files = file.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory() && !pathname.isHidden();
                    }
                });
                List<String> fileNames = new ArrayList<String>();
                for (File one : files) {
                    fileNames.add(one.getName());
                }
                //LOG.debug("san path:{"+fileNames+"}");
                cf.detect(toStringArray(fileNames));
                // FIXME 这里扫描全部可能会有性能问题
                // XXX 在java项目中可以扫描到jar文件中的类，在web项目中不行.
                cf.detect();
            } else {
                cf.detect(packageNames);
            }
        } catch (IOException e) {
            //LOG.error("scan package error packages:{}"+ Arrays.toString(packageNames));
        }
        return classes;
    }



    /**
     * 加载类.
     * @param classes
     * @param className
     */
    private static void loadClass(final Set<Class<?>> classes, String className) {
        try {
            Class<?> clazz = Resources.classForName(className);
            classes.add(clazz);
        } catch (ClassNotFoundException e) {
            //LOG.debug("load class error . className:{"+className+"}");
        }
    }

    /**
     * 转换为String数组
     *
     * @param array
     * @return
     */
    public static String[] toStringArray(List<String> array) {
        return array.toArray(new String[] {});
    }
}