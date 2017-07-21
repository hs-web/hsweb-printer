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

package org.hsweb.printer.fx.component;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.builders.ComponentBuilder;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiong on 2017-07-19.
 */
public class ComponentFactory {
    private static Map<String,ComponentBuilder> builders=new HashMap<>();
    static {
        Reflections reflections = new Reflections("org.hsweb.printer.fx.component.builders");
        Set<Class<? extends ComponentBuilder>> routesSet = reflections.getSubTypesOf(ComponentBuilder.class);
        for (Class<? extends ComponentBuilder> componentBuilderClass : routesSet) {
            if(componentBuilderClass.isInterface()|| Modifier.isAbstract(componentBuilderClass.getModifiers())){
                continue;
            }
            try {
                addElementComponentBuilder(componentBuilderClass.newInstance());
            } catch (InstantiationException e) {
                System.out.println(componentBuilderClass.getName());
            } catch (IllegalAccessException e) {
                System.out.println(componentBuilderClass.getName());
            }
        }
        /* addElementComponentBuilder(new TextElementComponentBuilder());
        addElementComponentBuilder(new VariableElementComponentBuilder());
        addElementComponentBuilder(new ImageElementComponentBuilder());
        addElementComponentBuilder(new QrcodeElementComponentBuilder());


        addElementComponentBuilder(new PosPanelComponentBudiler());
        addElementComponentBuilder(new ForPanelComponentBudiler());*/


    }

    private static void addElementComponentBuilder(ComponentBuilder elementComponentBuilder){
        builders.put(elementComponentBuilder.getType(),elementComponentBuilder);
    }

    public static<T extends Component> T builder(String type, TemplateComponentDTO docComponentDTO, PropertyController propertyController){


        return builder(type,docComponentDTO,propertyController,null);

    }
    public static<T extends Component> T builder(String type, TemplateComponentDTO docComponentDTO, PropertyController propertyController, PanelComponent parentComponent ){

        ComponentBuilder<T> elementComponentBuilder = builders.get(type);
        if(elementComponentBuilder!=null){
            return elementComponentBuilder.builder(docComponentDTO,propertyController,parentComponent);
        }
        return null;

    }


}
