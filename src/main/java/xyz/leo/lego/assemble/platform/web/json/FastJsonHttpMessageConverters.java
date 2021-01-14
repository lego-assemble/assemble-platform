package xyz.leo.lego.assemble.platform.web.json;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @author xuyangze
 * @date 2019/1/18 12:12 PM
 */
@Configuration
public class FastJsonHttpMessageConverters {
    @Bean
    @ConditionalOnMissingBean(name = "fastJsonHttpMessageConverters")
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        //1. 需要定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();

        //2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        //3. 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fasHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }
}
