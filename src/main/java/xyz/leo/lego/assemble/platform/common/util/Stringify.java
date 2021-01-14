package xyz.leo.lego.assemble.platform.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Stringify {
    public static String toString(Object aObject) {
        Class clazz = aObject.getClass();
        if(aObject instanceof List) {
            List<Object> aObjectList = (List)aObject;
            List<StringifyEntry> stringsList = new ArrayList<StringifyEntry>();
            for(Object aObj : aObjectList) {
                String value = toString(aObj);
                StringifyEntry entry = new StringifyEntry(aObject.getClass(), value);
                stringsList.add(entry);
            }

            aObject = JSON.toJSONString(stringsList);
        }

        StringifyEntry stringifyEntry = new StringifyEntry(clazz, aObject);
        return JSON.toJSONString(stringifyEntry);
    }

    public static <T> T toObject(String aString) {
        if(null == aString) {
            return null;
        }

        StringifyEntry stringifyEntry = JSON.parseObject(aString, StringifyEntry.class);
        if(List.class.isAssignableFrom(stringifyEntry.getClazz())) {
            String value = (String)stringifyEntry.getValue();
            List<StringifyEntry> stringList = JSON.parseArray(value, StringifyEntry.class);
            if(null != stringList) {
                List<T> values = new ArrayList<T>();
                for(StringifyEntry entry : stringList) {
                    values.add(toObject(entry.getValue().toString()));
                }

                return (T)values;
            }
        } else if (stringifyEntry.getClazz().isAssignableFrom(Character.class)) {
            return (T)new Character(((String)stringifyEntry.getValue()).charAt(0));
        } else if (stringifyEntry.getClazz().isAssignableFrom(String.class)) {
            return (T)stringifyEntry.getValue();
        } else if (stringifyEntry.getValue() instanceof String) {
            return (T)JSON.parseObject(stringifyEntry.getValue().toString(), stringifyEntry.getClazz());
        } else if (stringifyEntry.getValue() instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject)stringifyEntry.getValue();
            return (T)JSON.toJavaObject(jsonObject, stringifyEntry.getClazz());
        } else {
            return (T)stringifyEntry.getValue();
        }

        return null;
    }
}
