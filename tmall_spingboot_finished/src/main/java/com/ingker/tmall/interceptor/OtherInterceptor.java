package com.ingker.tmall.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ingker.tmall.entity.service.impl.CategoryServiceImpl;
import com.ingker.tmall.entity.service.impl.OrderItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ingker.tmall.entity.pojo.Category;
import com.ingker.tmall.entity.pojo.OrderItem;
import com.ingker.tmall.entity.pojo.User;


public class OtherInterceptor implements HandlerInterceptor {
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    OrderItemServiceImpl orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        User user =(User) session.getAttribute("user");
        int  cartTotalItemNumber = 0;
        if(null!=user) {
            List<OrderItem> ois = orderItemService.listByUser(user);
            for (OrderItem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }

        }

        List<Category> cs =categoryService.list();
        String contextPath=httpServletRequest.getServletContext().getContextPath();

        httpServletRequest.getServletContext().setAttribute("categories_below_search", cs);
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        httpServletRequest.getServletContext().setAttribute("contextPath", contextPath);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
