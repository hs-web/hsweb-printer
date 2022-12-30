package org.hswebframework.printer.builder;


import lombok.extern.slf4j.Slf4j;
import org.hswebframework.printer.Layer;
import org.hswebframework.utils.StringUtils;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public abstract class AbstractLayerBuilder implements LayerBuilder {

    private String type;

    private Map<String, Object> configMap;

    public AbstractLayerBuilder(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Layer build(Map<String, Object> config) {
        this.configMap = config;

        return doBuild();
    }

    protected abstract Layer doBuild();

    protected String getString(String config, String def) {
        Object val = configMap.getOrDefault(config, def);
        if (val == null) {
            return def;
        }
        return String.valueOf(val);
    }

    protected int getInt(String config, int def) {
        String stringValue = getString(config, String.valueOf(def));
        if (!StringUtils.isNumber(stringValue)) {
            log.warn("参数{}={}不是数字格式!", config, stringValue);
            return def;
        }
        return Math.round(new BigDecimal(stringValue).floatValue());
    }

    protected boolean getBoolean(String config, boolean def) {
        String string = getString(config, String.valueOf(def));
        return "true".equalsIgnoreCase(string) || "1".equalsIgnoreCase(string);
    }

    protected Font getFont(Font def) {
        String fontName = getString("fontFamily", null);
        int fontSize = getInt("fontSize", def == null ? 16 : def.getSize());
        if (fontName == null) {
            return def;
        }
        return new Font(fontName, Font.PLAIN, fontSize);
    }

    protected Color getColor(String key,Color def) {
        String config = getString(key, null);
        if (null == config) {
            return def;
        }
        int r;
        int g;
        int b;
        if (config.startsWith("#")) {
            config = config.substring(1);
            if (config.length() != 6) {
                return def;
            }
            String[] rgbhex = config.replaceAll("(\\w{2})(?=.)", "$1,").split(",");
            r = Integer.parseInt(rgbhex[0], 16);
            g = Integer.parseInt(rgbhex[1], 16);
            b = Integer.parseInt(rgbhex[2], 16);
        }else {
            return def;
        }

        return new Color(r, g, b);
    }
    protected Color getColor(Color def) {
        return getColor("color",def);
    }

}
