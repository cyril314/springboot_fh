package com.fit.common.interceptor;

import com.fit.common.utils.StringUtil;
import com.fit.config.shiro.Jurisdiction;
import com.fit.entity.system.User;
import com.fit.util.Const;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @className: ActionInterceptor
 * @description: 访问拦截器
 * @author: Aim
 * @date: 2023/4/13
 **/
@Component
public class ActionInterceptor implements HandlerInterceptor {

    //不对匹配该值的访问路径拦截（正则）
    private static final String NO_INTERCEPTOR_PATH = ".*/((login*)|(logout)|(code)|(app)|(weixin)|(assets)|(main)|(websocket)).*";
    private static final String SUFFIX = ".do";

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String uri = req.getRequestURI().replace("//", "/").trim();
        if (StringUtils.endsWithIgnoreCase(uri, SUFFIX)) {
            ServletContext context = req.getServletContext();
            context.getRequestDispatcher(StringUtil.removeSuffix(uri, SUFFIX)).forward(req, resp);
            return false;
        }
        StringBuffer realPath = new StringBuffer(req.getScheme());
        realPath.append("://").append(req.getServerName()).append(":").append(req.getServerPort());
        realPath.append(req.getContextPath()).append("/");
        req.setAttribute("ctx", realPath.toString());
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mav) throws Exception {
        if (mav == null) {
            mav = new ModelAndView();
        }
        String path = req.getServletPath();
        switch (resp.getStatus()) {
            case 200:
                if (!path.matches(NO_INTERCEPTOR_PATH)) {
                    User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
                    if (user != null) {
                        path = path.substring(1, path.length());
                        boolean b = Jurisdiction.hasJurisdiction(path); //访问权限校验
                        if (!b) {
                            resp.sendRedirect(req.getContextPath() + Const.LOGIN);
                        }
                    } else {//登陆过滤
                        resp.sendRedirect(req.getContextPath() + Const.LOGIN);
                    }
                }
                break;
            case 403:
            case 500:
                mav.setViewName("error");
                break;
            case 404:
                mav.setViewName("404");
                break;
        }
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception {
    }
}
