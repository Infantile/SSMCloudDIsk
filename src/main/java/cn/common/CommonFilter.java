package cn.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 过滤器
 * 
 * @author hzq
 * 
 */
public class CommonFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(
				(HttpServletResponse) response);
		// 判断是否为ajax请求
		String XRequested = hrequest.getHeader("X-Requested-With");
		// 登录登陆页面
		String logonStrings = "validateLogin.do";
		// 过滤资源后缀参数
		String includeStrings = "/file/;/init/;/group/;/share/;/recycle/;/collect/";
		// 没有登陆转向页面
		String redirectPath = hrequest.getScheme() + "://"
				+ hrequest.getServerName() + ":" + hrequest.getServerPort()
				+ hrequest.getContextPath() + "/";
		// 请求地址
		String uri = hrequest.getRequestURI();
		String[] logonList = logonStrings.split(";");
		String[] includeList = includeStrings.split(";");
		// 只对指定过滤参数后缀进行过滤
		if (!isContains(uri, includeList)) {
			chain.doFilter(request, response);
			return;
		}
		// 对登录页面不进行过滤
		if (isContains(uri, logonList)) {
			chain.doFilter(request, response);
			return;
		}
		// 判断用户是否登录
		String user = (String) hrequest.getSession().getAttribute("userId");
		if (user == null) {
			if ("XMLHttpRequest".equals(XRequested)) {
				//修改jquery-1.11.1.js中function done()来实现页面跳转
				response.getWriter().write("IsAjax");
			} else {
				wrapper.sendRedirect(redirectPath);
			}
			return;
		} else {
			chain.doFilter(request, response);
			return;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private static boolean isContains(String container, String[] regx) {
		boolean result = false;
		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i]) != -1) {
				return true;
			}
		}
		return result;
	}

}
