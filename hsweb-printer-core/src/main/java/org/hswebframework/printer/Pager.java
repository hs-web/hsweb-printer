package org.hswebframework.printer;

import lombok.Data;

import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
@Data
public class Pager {
    private List<Layer> layers;

    private int orientation = 1;

}
