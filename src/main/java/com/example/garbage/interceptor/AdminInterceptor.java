package com.example.garbage.interceptor;
import com.example.garbage.common.Constants;
import com.example.garbage.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.*;
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Object obj = req.getSession().getAttribute(Constants.SESSION_USER);
        if (obj == null || "USER".equals(((User) obj).getRole())) { resp.sendRedirect("/admin/login"); return false; }
        return true;
    }
}
