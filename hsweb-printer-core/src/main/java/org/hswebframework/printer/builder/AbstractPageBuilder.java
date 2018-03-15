package org.hswebframework.printer.builder;


import org.hswebframework.printer.Layer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since
 */
public abstract class AbstractPageBuilder implements PageBuilder {
    static final Map<String, LayerBuilder> layerBuilders = new HashMap<>();

    static {
        addBuilder(new LineLayerBuilder());
        addBuilder(new RectLayerBuilder());
        addBuilder(new TextLayerBuilder());
        addBuilder(new ImageLayerBuilder());
    }

    static void addBuilder(LayerBuilder builder) {
        layerBuilders.put(builder.getType(), builder);
    }

    protected Layer buildLayer(Map<String, Object> config) {
        String type = (String) config.get("type");
        if (type == null) {
            return null;
        }
        LayerBuilder builder = layerBuilders.get(type);
        return builder == null ? null : builder.build(config);
    }
}
