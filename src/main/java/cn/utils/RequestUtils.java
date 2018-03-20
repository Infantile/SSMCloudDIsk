package cn.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

/**
 * @author hzq
 */
public class RequestUtils {	

	/**
	 * 返回字符串,删除了首尾空格,如果不存在则返回null
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getRequestKeyValue(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return StringUtils.trimWhitespace(value);
		}
		return value;
	}

	/**
	 * 返回字符串,删除了首尾空格,如果不存在则返回null
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getSessionKeyValue(HttpServletRequest request,	String key) {
		String value = null;
		HttpSession session = request.getSession();
		if (session != null) {
			Object obj = session.getAttribute(key);
			if (!StringUtils.isEmpty(obj)) {
				value = String.valueOf(obj);
			}
		}
		return value;
	}
	
	/**
	 * bootstrapTable参数乱码问题解决
	 * @param key
	 * @return
	 */
	public static String formatBootstrapTableEncoding(String key) {
		String value = key;
		if (key != null) {
			try {
				value = new String(key.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				value = key;
			} 
		}
		return value;
	}
}