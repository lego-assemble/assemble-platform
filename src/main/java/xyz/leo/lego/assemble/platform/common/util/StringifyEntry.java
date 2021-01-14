package xyz.leo.lego.assemble.platform.common.util;

public class StringifyEntry {
    private Class clazz;
    private Object value;

    public StringifyEntry() {

    }

    public StringifyEntry(Class clazz, Object value) {
        this.clazz = clazz;
        this.value = value;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
