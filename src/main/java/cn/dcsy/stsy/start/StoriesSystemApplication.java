package cn.dcsy.stsy.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DC_DC
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.dcsy.stsy"})
@MapperScan("cn.dcsy.stsy.mappers")
public class StoriesSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoriesSystemApplication.class, args);
    }

}
