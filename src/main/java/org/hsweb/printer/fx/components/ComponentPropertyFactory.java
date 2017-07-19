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

package org.hsweb.printer.fx.components;

import org.hsweb.printer.fx.components.components.Component;
import org.hsweb.printer.fx.components.components.PanelComponent;
import org.hsweb.printer.fx.components.propertys.ComponentProperty;
import org.hsweb.printer.fx.components.propertys.dtos.PropertysDTO;
import org.hsweb.printer.fx.utils.scan.ClassScanner;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiong on 2017-07-19.
 */
public class ComponentPropertyFactory {
    private static Map<String,ComponentProperty> propertyMap=new HashMap<>();
    static {
        Set<Class<ComponentProperty>> routesSet = ClassScanner.scan(ComponentProperty.class,"org.hsweb.printer.fx.components.propertys");
        for (Class<ComponentProperty> componentBuilderClass : routesSet) {
            if(componentBuilderClass.isInterface()|| Modifier.isAbstract(componentBuilderClass.getModifiers())){
                continue;
            }
            try {
                addComponentProperty(componentBuilderClass.newInstance());
            } catch (InstantiationException e) {
                System.out.println(componentBuilderClass.getName());
            } catch (IllegalAccessException e) {
                System.out.println(componentBuilderClass.getName());
            }
        }
    }
    private static void addComponentProperty(ComponentProperty elementComponentBuilder){
        propertyMap.put(elementComponentBuilder.getType(),elementComponentBuilder);
    }

    public static <T extends TemplateComponentDTO> void  property(PropertysDTO propertys,Component basicComponent, T baseComponentDTO){
        propertys.getPropertyFont().setVisible(false);
        propertys.getElementMenu().setVisible(false);
        propertys.getPubProperty().clear();
        propertys.getFontProperty().clear();

        ComponentProperty<Component,T> componentProperty = propertyMap.get(baseComponentDTO.getType());
        if(componentProperty!=null){
            componentProperty.property(propertys,basicComponent,baseComponentDTO);
        }

    }

    public static  <T extends TemplateComponentDTO> void property(PropertysDTO  propertys,PanelComponent basicComponent, T baseComponentDTO) {
        propertys.getPropertyFont().setVisible(false);
        propertys.getElementMenu().setVisible(false);
        propertys.getPubProperty().clear();
        propertys.getFontProperty().clear();

        ComponentProperty<PanelComponent,T> componentProperty = propertyMap.get(baseComponentDTO.getType());
        if(componentProperty!=null){
            componentProperty.property(propertys,basicComponent,baseComponentDTO);
        }
    }





}
