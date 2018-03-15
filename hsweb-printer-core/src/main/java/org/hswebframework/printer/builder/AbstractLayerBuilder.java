package org.hswebframework.printer.builder;


import org.hswebframework.printer.Layer;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
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
        return Math.round(new BigDecimal(getString(config, String.valueOf(def))).floatValue());
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

    protected Color getColor(Color def) {
        int r = def.getRed();
        int g = def.getGreen();
        int b = def.getBlue();
        String config = getString("color", null);
        if (null == config) {
            return def;
        }
        if (config.startsWith("#")) {
            config = config.substring(1);
            if (config.length() != 6) {
                return def;
            }
            String[] rgbhex = config.replaceAll("(\\w{2})(?=.)", "$1,").split(",");
            r = Integer.parseInt(rgbhex[0], 16);
            g = Integer.parseInt(rgbhex[1], 16);
            b = Integer.parseInt(rgbhex[2], 16);
        }

        return new Color(r, g, b);
    }

}
