package com.cucund.project.mq.reader.utils;

import java.util.List;
import java.util.Map;

public class EmptyUtils {


    public static boolean isNotEmpty(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof String) {
            if (o.equals(""))
                return false;
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty())
                return false;
        } else if (o instanceof List){
            if (((List) o).isEmpty())
                return false;
        }
        return true;
    }
}
