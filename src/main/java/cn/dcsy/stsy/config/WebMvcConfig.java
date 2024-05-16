package cn.dcsy.stsy.config;


import cn.dcsy.stsy.config.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 32841
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CorsFilter())
                // 拦截所有请求
                .addPathPatterns("/**")
                // 排除登录和注册接口
                .excludePathPatterns("/basic/login", "/basic/register", "/basic/index", "/mail/send");
    }
}
