package org.hswebframework.printer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 以像素定义纸张大小
 *
 * @author zhouhao
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PixelPaper {
    private int width;

    private int height;

    /**
     * 将毫米转为像素，算法为:dpi/25.4/mm
     *
     * @param dpi   DPI,每英寸的像素数量
     * @param paper 以毫米定义的纸张
     * @see Paper#A4
     */
    public PixelPaper(double dpi, MillimeterPaper paper) {
        //1英寸=25.4毫米
        BigDecimal decimal = BigDecimal.valueOf(dpi)
                .divide(BigDecimal.valueOf(25.4), 5, RoundingMode.CEILING);
        setHeight(decimal.multiply(BigDecimal.valueOf(paper.getHeight())).intValue());

        setWidth(decimal.multiply(BigDecimal.valueOf(paper.getWidth())).intValue());
    }
}
