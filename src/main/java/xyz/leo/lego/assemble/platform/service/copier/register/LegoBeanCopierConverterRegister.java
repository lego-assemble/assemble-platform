package xyz.leo.lego.assemble.platform.service.copier.register;

import org.apache.commons.collections4.MapUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/12/5 8:47 PM
 */
@Component
public class LegoBeanCopierConverterRegister implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> beanMap = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BeanConverter.class);
        if (MapUtils.isEmpty(beanMap)) {
            return;
        }

        for (Object bean : beanMap.values()) {
            if (bean instanceof LegoBeanCopierConverter) {
                LegoBeanCopierConverter legoBeanCopierConverter = (LegoBeanCopierConverter)bean;
                legoBeanCopierConverter.doRegister();
            }
        }
    }
}
