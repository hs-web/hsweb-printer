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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xiongchuang on 2016/8/27 .
 */
public class MenuItemActionListener implements ActionListener {
    private JFrame main;
    public MenuItemActionListener(JFrame main) {
        this.main=main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof MenuItem){
            MenuItem menuItem=(MenuItem)e.getSource();
            if(MenuItemName.open.getName().equals( menuItem.getName())){
                main.setExtendedState(JFrame.NORMAL);//设置状态为正常
                main.setVisible(true);//显示主窗体
            }else if(MenuItemName.exit.getName().equals( menuItem.getName())){
                System.exit(0);
            }
        }
    }
}
