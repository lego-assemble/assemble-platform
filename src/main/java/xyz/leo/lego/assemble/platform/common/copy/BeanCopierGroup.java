package xyz.leo.lego.assemble.platform.common.copy;

import net.sf.cglib.beans.BeanCopier;

/**
 * @author xuyangze
 * @date 2019/11/12 5:51 PM
 */
public class BeanCopierGroup {
    /**
     * 不需要转化的copier
     */
    private BeanCopier noConvertCopier;

    /**
     * 需要转化的copier
     */
    private BeanCopier convertCopier;

    public void put(boolean convert, BeanCopier beanCopier) {
        if (convert) {
            this.convertCopier = beanCopier;
        } else {
            this.noConvertCopier = beanCopier;
        }
    }

    public BeanCopier get(boolean convert) {
        if (convert) {
            return this.convertCopier;
        } else {
            return this.noConvertCopier;
        }
    }
}
