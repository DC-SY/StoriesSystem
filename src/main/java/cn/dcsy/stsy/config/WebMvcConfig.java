package cn.dcsy.stsy.config;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:31
 */

import cn.dcsy.stsy.config.filters.LoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginFilter())
                // 拦截所有请求
                .addPathPatterns("/**")
                // 排除登录和注册接口
                .excludePathPatterns("/basic/login", "/basic/register", "/basic/index", "/mail/send");
    }
}
