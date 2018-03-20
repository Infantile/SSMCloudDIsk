package cn.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 拦截器 
 */  
public class CommonInterceptor implements HandlerInterceptor{  
    
    /** 
     * Handler执行之前调用这个方法 
     */ 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler) throws Exception { 
        return true;
    }
  
    /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
    public void postHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler, ModelAndView modelAndView) throws Exception {  
    }  
    
    /** 
     * Handler执行完成之后调用这个方法 
     */  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception exc)  
            throws Exception { 
          
    }
}  
