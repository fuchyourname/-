package com.and.music.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.domain.Users;
import com.and.music.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    // 白名单路径
    private static final List<String> WHITE_LIST = Arrays.asList("/index/login", "/index/register");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 检查路径是否在白名单中
        if (WHITE_LIST.contains(requestURI)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        Users user = session != null ? (Users) session.getAttribute("user") : null;

        if (ObjectUtil.isEmpty(user)) {
            return false;
        }

        // 设置用户信息到 UserContext
        UserContext.setUser(user);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理完成后清除 ThreadLocal 中的数据
        UserContext.removeUser();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求完全处理完成后清除 ThreadLocal 中的数据
        UserContext.removeUser();
    }
}
