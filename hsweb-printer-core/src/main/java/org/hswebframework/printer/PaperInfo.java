package org.hswebframework.printer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhouhao
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperInfo implements Paper {
    private int width;

    private int height;
}
