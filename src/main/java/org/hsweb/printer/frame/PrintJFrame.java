/*
 *  Copyright (c) 2015.  meicanyun.com Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  meicanyun Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with meicanyun.com.
 */

package org.hsweb.printer.frame;

import org.hsweb.printer.dtos.PrintHistoryDTO;
import org.hsweb.printer.dtos.PrintInputDTO;
import org.hsweb.printer.dtos.PrintResultDTO;
import org.hsweb.printer.server.PrinterHttpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by xiongchuang on 2016/8/25 .
 */
public class PrintJFrame extends JFrame {

    private PrintJFrame frame=this;
    private PrinterHttpServer printerHttpServer;

    public PrintJFrame(String applicationName, PrinterHttpServer printerHttpServer) {
        super(applicationName);
        this.printerHttpServer=printerHttpServer;

        frame.setSize(800, 600);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        frame.setBackground(Color.white);

        //为主窗体注册窗体事件
        frame.addWindowListener(new PrintJFrameWindowAdapter());
    }



    private class PrintJFrameWindowAdapter extends WindowAdapter{
        /**
         * 窗口活动
         */
        public void windowActivated(WindowEvent e) {
            JLabel jLabel=new JLabel("<html><div style='padding:5px 10px'>打印服务状态："+(printerHttpServer==null||!printerHttpServer.getState()?"<span color='red'>未开启</span>":"<span color='green'>开启中</span>")+"</div></html>");
            jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel.setVerticalAlignment(SwingConstants.TOP);
            frame.add(jLabel);


            List<PrintHistoryDTO> historyDTOList = StartMain.historyDTOList;
            if(!historyDTOList.isEmpty()){
                JPanel jp_center_left = new JPanel();
                jp_center_left.setLayout(new GridLayout(5,1));
                for (PrintHistoryDTO printHistoryDTO : historyDTOList) {
                    PrintInputDTO printInputDTO = printHistoryDTO.getPrintInputDTO();
                    PrintResultDTO printResultDTO = printHistoryDTO.getPrintResultDTO();

                    JLabel jLabel2=new JLabel(printInputDTO.getPrintDocName()+"  ->  "+(printResultDTO.getSuccess()?"打印成功":printResultDTO.getMessage()));
                    jp_center_left.add(jLabel2);
                }

                JPanel jp_center = new JPanel();
                jp_center.setLayout(new GridLayout(1,2));
                jp_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
                jp_center.add(jp_center_left);
                //jp_center.add(jp_center_right);
                frame.add(jp_center);
            }
        }

        /**
         *窗口最小化
         * @param e
         */
        @Override
        public void windowIconified(WindowEvent e) {
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
        }

        /**
         * 窗口关闭中
         * @param e
         */
        public void windowClosing(WindowEvent e) {
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
        }

        /**
         * 窗口关闭
         * @param e
         */
        public void windowClosed(WindowEvent e) {
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
        }
    }
}
