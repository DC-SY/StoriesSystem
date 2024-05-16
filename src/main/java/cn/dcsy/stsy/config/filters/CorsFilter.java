package cn.dcsy.stsy.config.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:27
 */
@Slf4j
public class CorsFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "*");
        return true;
    }
}
