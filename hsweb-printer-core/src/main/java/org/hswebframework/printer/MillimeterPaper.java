package org.hswebframework.printer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 以毫米定义纸张大小
 *
 * @author zhouhao
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MillimeterPaper implements Paper {
    private int width;

    private int height;
}
