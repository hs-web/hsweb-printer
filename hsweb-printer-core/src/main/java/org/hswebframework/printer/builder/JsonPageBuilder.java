package org.hswebframework.printer.builder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hswebframework.printer.Layer;
import org.hswebframework.printer.Pager;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhouhao
 * @since 1.0
 */
public class JsonPageBuilder extends AbstractPageBuilder {
    @Override
    public List<Pager> build(String config) {
        config = config.trim();
        List<Object> list = config.startsWith("[")
                ? JSON.parseArray(config)
                : Collections.singletonList(JSON.parseObject(config));
        return list.stream()
                .map(JSONObject.class::cast)
                .map(jsonObject -> {
                    Pager pager = new Pager();
                    Integer orientation = jsonObject.getInteger("orientation");
                    pager.setOrientation(orientation == null ? 0 : orientation);
                    List<Layer> layers = jsonObject.getJSONArray("layers")
                            .stream()
                            .map(JSONObject.class::cast)
                            .map(this::buildLayer)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    pager.setLayers(layers);
                    return pager;
                }).collect(Collectors.toList());
    }
}
