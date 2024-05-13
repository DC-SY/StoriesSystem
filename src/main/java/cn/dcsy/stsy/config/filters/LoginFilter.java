package cn.dcsy.stsy.config.filters;

import cn.dcsy.stsy.models.voData.BasicLoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author DC_DC
 * Date: 2024/5/13/23:27
 */
public class LoginFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        // 从Session中获取用户信息
        BasicLoginVO user = (BasicLoginVO) request.getSession().getAttribute("user");
        // 如果用户信息为空，说明用户未登录，返回错误响应
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("用户未登录");
            return false;
        }
        // 用户已登录，继续处理请求
        return true;
    }
}
