package xyz.leo.lego.assemble.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan(basePackages = "xyz.leo.lego.assemble.platform.dao.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class AssemblePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssemblePlatformApplication.class, args);
	}

}
