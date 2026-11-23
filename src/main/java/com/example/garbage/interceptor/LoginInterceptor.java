package com.example.garbage.interceptor;
import com.example.garbage.common.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.*;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (req.getSession().getAttribute(Constants.SESSION_USER) == null) {
            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().write("{\"code\":401,\"msg\":\"请先登录\"}");
            } else { resp.sendRedirect("/login"); }
            return false;
        }
        return true;
    }
}
