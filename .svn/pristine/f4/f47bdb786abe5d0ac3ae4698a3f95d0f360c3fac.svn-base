package cn.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtil {
	private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class); 
	/**
	 * 传入文本内容，返回 SHA-512 串 
	 * @param strText
	 * @return
	 */
	public static String SHA256(final String strText) {
		return SHA(strText, "SHA-512");
	}  
	  
	 /**
	  * 字符串 SHA 加密  
	  * @param strText 需要加密的数据
	  * @param strType 加密类型：SHA-256, SHA-512, MD5
	  * @return
	  */
	private static String SHA(final String strText, final String strType) {
		// 返回值
		String strResult = null;

		// 是否是有效字符串
		if (strText != null && strText.length() > 0) {
			try {
				// SHA 加密开始
				// 创建加密对象 并传入加密类型
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				// 传入要加密的字符串
				messageDigest.update(strText.getBytes());
				// 得到 byte 类型结果
				byte byteBuffer[] = messageDigest.digest();

				// 将 byte 转换为 string
				StringBuffer strHexString = new StringBuffer();
				// 遍历 byte buffer
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				// 得到返回结果
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
				logger.error("密码加密出现异常：" + e.getMessage());
			}
		}

		return strResult;
	}  
}
