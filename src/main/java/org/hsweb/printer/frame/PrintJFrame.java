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

import org.hsweb.printer.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Created by xiongchuang on 2016/8/25 .
 */
public class PrintJFrame extends JFrame {

    public PrintJFrame() throws HeadlessException {
        super("打印服务");
        initJFrame();
        initSystemTray();
    }

    private void initJFrame() {
        this.setSize(800, 600);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);

        //为主窗体注册窗体事件
        this.addWindowListener(new PrintJFrameWindowAdapter(this));

        this.setBackground(Color.white);
    }


    private void initSystemTray() {


        PopupMenu popupMenu=getPrintPopupMenu();

        URL resource = FontUtil.class.getClassLoader().getResource("print_icon.png");
        ImageIcon icon = new ImageIcon(resource); // 将要显示到托盘中的图标

        TrayIcon printTrayIcon = new TrayIcon(icon.getImage(), "打印服务", popupMenu);//实例化托盘图标
        printTrayIcon.setImageAutoSize(true);
        printTrayIcon.addMouseListener(new PrintTrayIconMouseAdapter(this,printTrayIcon));  //为托盘图标监听点击事件

        try {
            SystemTray tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
            tray.add(printTrayIcon); // 将托盘图标添加到系统的托盘实例中
        } catch (AWTException ex) {
            ex.printStackTrace();
        }


    }


    public PopupMenu getPrintPopupMenu() {
        MenuItem print = new MenuItem("print");
        print.setEnabled(false);
        MenuItem show = new MenuItem("open");
        show.addActionListener(new MenuItemShowActionListener(this,show));


        MenuItem exit = new MenuItem("exit");
        exit.addActionListener(e ->  System.exit(0));


        PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
       // pop.add(print);
        pop.add(show);
        pop.add(exit);

        return pop;
    }

    private class PrintJFrameWindowAdapter extends WindowAdapter{
        private JFrame printJFrame;
        public PrintJFrameWindowAdapter(JFrame printJFrame) {
            this.printJFrame=printJFrame;
        }

        /**
         * 窗口活动
         */
        public void windowActivated(WindowEvent e) {
            JLabel jLabel=new JLabel("修修修");
            printJFrame.add(jLabel);
        }

        /**
         *窗口最小化
         * @param e
         */
        @Override
        public void windowIconified(WindowEvent e) {
            printJFrame.setVisible(false);//使窗口不可视
            printJFrame.dispose();//释放当前窗体资源
        }

        /**
         * 窗口关闭中
         * @param e
         */
        public void windowClosing(WindowEvent e) {
            printJFrame.setVisible(false);//使窗口不可视
            printJFrame.dispose();//释放当前窗体资源
        }

        /**
         * 窗口关闭
         * @param e
         */
        public void windowClosed(WindowEvent e) {
            printJFrame.setVisible(false);//使窗口不可视
            printJFrame.dispose();//释放当前窗体资源
        }


/*
        *//**
         *窗口打开时
         * @param e
         *//*
        public void windowOpened(WindowEvent e) {
            System.out.println("窗口打开时");
        }

        *//**
         * Invoked when a window is de-iconified.
         *//*
        public void windowDeiconified(WindowEvent e) {
            System.out.println("窗口恢复");
        }

        *//**
         * Invoked when a window is de-activated.
         *//*
        public void windowDeactivated(WindowEvent e) {
            System.out.println("窗口失去焦点");
        }

        *//**
         * Invoked when a window state is changed.
         * @since 1.4
         *//*
        public void windowStateChanged(WindowEvent e) {
            System.out.println("窗口状态改变");
        }

        *//**
         * Invoked when the Window is set to be the focused Window, which means
         * that the Window, or one of its subcomponents, will receive keyboard
         * events.
         *
         * @since 1.4
         *//*
        public void windowGainedFocus(WindowEvent e) {
            System.out.println("获得焦点");
        }

        *//**
         * Invoked when the Window is no longer the focused Window, which means
         * that keyboard events will no longer be delivered to the Window or any of
         * its subcomponents.
         *
         * @since 1.4
         *//*
        public void windowLostFocus(WindowEvent e) {

            System.out.println("失去焦点");
        }*/

    }

    private class PrintTrayIconMouseAdapter extends MouseAdapter{
        private JFrame printJFrame;
        private TrayIcon printTrayIcon;
        public PrintTrayIconMouseAdapter(JFrame printJFrame,TrayIcon printTrayIcon) {
            this.printJFrame=printJFrame;
            this.printTrayIcon=printTrayIcon;
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)//鼠标双击图标
            {
                //printTrayIcon.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
                printJFrame.setExtendedState(JFrame.NORMAL);//设置状态为正常
                printJFrame.setVisible(true);//显示主窗体
            }
        }
    }

    private class MenuItemShowActionListener implements ActionListener {

        private JFrame printJFrame;
        private MenuItem show;

        public MenuItemShowActionListener(JFrame printJFrame, MenuItem show) {
            this.printJFrame=printJFrame;
            this.show=show;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            printJFrame.setExtendedState(JFrame.NORMAL);//设置状态为正常
            printJFrame.setVisible(true);
        }
    }
}
