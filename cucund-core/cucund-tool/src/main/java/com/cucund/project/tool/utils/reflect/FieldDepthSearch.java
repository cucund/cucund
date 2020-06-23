package com.cucund.project.tool.utils.reflect;

import java.lang.reflect.Field;

public class FieldDepthSearch {

    public static<T> Field getField(Class<T> c,String propertyName){
        Field field = null;
        if(c.isMemberClass()){
            field = getField(c.getSuperclass(), propertyName);
        }
        if(field == null){
            try {
                field = c.getField(propertyName);
            } catch (NoSuchFieldException e) {
                try {
                    field = c.getDeclaredField(propertyName);
                } catch (NoSuchFieldException e2) {
                }
            }
        }
        return field;
    }



}
