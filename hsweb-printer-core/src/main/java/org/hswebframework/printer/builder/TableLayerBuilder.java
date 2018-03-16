package org.hswebframework.printer.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouhao
 * @since 2.0
 */
public class TableLayerBuilder {

    private Row nowRow;

    private List<Row> rows = new ArrayList<>();

    public TableLayerBuilder nexRow() {

        return this;
    }

    public static class Row {
        private int        x;
        private int        y;
        private int        height;
        private List<Cell> cells;

        public List<Cell> getCells() {
            return cells;
        }

        public void setCells(List<Cell> cells) {
            this.cells = cells;
        }
    }

    public static class Cell {
        private int x;

        private int y;

        private int width;

        private int height;

        private String text;

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
