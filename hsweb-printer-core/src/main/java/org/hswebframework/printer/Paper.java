package org.hswebframework.printer;

/**
 * @author zhouhao
 * @see MillimeterPaper
 * @see PixelPaper
 * @since 1.0
 */
public interface Paper {
    int getWidth();

    int getHeight();

    /*以毫米定义的纸张*/

    MillimeterPaper A0  = new MillimeterPaper(841, 1189);
    MillimeterPaper B0  = new MillimeterPaper(1000, 1414);
    MillimeterPaper C0  = new MillimeterPaper(917, 1297);
    MillimeterPaper A1  = new MillimeterPaper(594, 841);
    MillimeterPaper B1  = new MillimeterPaper(707, 1000);
    MillimeterPaper C1  = new MillimeterPaper(648, 917);
    MillimeterPaper A2  = new MillimeterPaper(420, 594);
    MillimeterPaper B2  = new MillimeterPaper(500, 707);
    MillimeterPaper C2  = new MillimeterPaper(458, 648);
    MillimeterPaper A3  = new MillimeterPaper(297, 420);
    MillimeterPaper B3  = new MillimeterPaper(353, 500);
    MillimeterPaper C3  = new MillimeterPaper(324, 458);
    MillimeterPaper A4  = new MillimeterPaper(210, 297);
    MillimeterPaper B4  = new MillimeterPaper(250, 353);
    MillimeterPaper C4  = new MillimeterPaper(229, 324);
    MillimeterPaper A5  = new MillimeterPaper(148, 210);
    MillimeterPaper B5  = new MillimeterPaper(176, 250);
    MillimeterPaper C5  = new MillimeterPaper(162, 229);
    MillimeterPaper A6  = new MillimeterPaper(105, 148);
    MillimeterPaper B6  = new MillimeterPaper(125, 176);
    MillimeterPaper C6  = new MillimeterPaper(114, 162);
    MillimeterPaper A7  = new MillimeterPaper(74, 105);
    MillimeterPaper B7  = new MillimeterPaper(88, 125);
    MillimeterPaper C7  = new MillimeterPaper(81, 114);
    MillimeterPaper A8  = new MillimeterPaper(52, 74);
    MillimeterPaper B8  = new MillimeterPaper(62, 88);
    MillimeterPaper C8  = new MillimeterPaper(57, 81);
    MillimeterPaper A9  = new MillimeterPaper(37, 52);
    MillimeterPaper B9  = new MillimeterPaper(44, 62);
    MillimeterPaper C9  = new MillimeterPaper(40, 57);
    MillimeterPaper A10 = new MillimeterPaper(26, 37);
    MillimeterPaper B10 = new MillimeterPaper(31, 44);
    MillimeterPaper C10 = new MillimeterPaper(28, 40);

}
