package cn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class); 
	
	/**
	 * 获取文件类型
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getFileType(MultipartFile multipartFile) {
		// 获取文件后缀名
		String ext = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
		return ext + "文件";
	}
	
	/**
	 * 获取准备压缩文件的名称

	 * @return
	 */
	public static String getZipFileName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return "CloudDisk-" + df.format(new Date()) + ".zip";
	}
	
	/**
	 * 获取文件或者文件夹名称（重复的时候设置加上括号和数字）
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getUploadFileName(String fileName, String rows) {
		try {
			//文件后缀名
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			//当前目录下所有文件和文件夹对象
			JSONArray myJsonArray = JSONArray.fromObject(rows);
			if (myJsonArray != null && myJsonArray.size() > 0) {
				int nameIndex = 0;
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					if(jsonObject.containsKey("filename")){
						String currentfileName = jsonObject.getString("filename");
						int beginBracketIndex = currentfileName.lastIndexOf("(");
						int endBracketIndex = currentfileName.lastIndexOf(")");
						int pointIndex = currentfileName.lastIndexOf(".");
						if(currentfileName != null && currentfileName.equals(fileName)){
							//获取最后一个)的位置bracketIndex与pointIndex比较是为了避免之前有自己命名的括号	
							nameIndex++;
						}else{
							if(beginBracketIndex > 0 && endBracketIndex > 0 
									&& pointIndex == endBracketIndex + 1){
								//获取到括号之前的值例如test(2)(1).txt值为test(2)
								String result = currentfileName.substring(0, beginBracketIndex);
								if(result != null && fileName.equals(result + "." + ext)){
									nameIndex++;
								}							
							}
						}
					}					
				}
				//如果有重复的名称重新命名
				if(nameIndex > 0){
					fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "("+ nameIndex +")." + ext;
				}
			}
		} catch (Exception e) {
			logger.error("获取文件或者文件夹名称出现异常：" + e.getMessage());
		}
		
		return fileName;
	}	
	
	/**
	 * 获取重命名
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getReNameFileName(String oldFileName, String newName) {
		try {
			//文件后缀名
			String oldExt = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
			if(newName != null && !newName.endsWith(oldExt)){
				newName = newName + "." + oldExt;
			}			
		} catch (Exception e) {
			logger.error("获取文件或者文件夹重命名出现异常：" + e.getMessage());
		}
		
		return newName;
	}
	
	/**
	 * 数据库文件大小显示到页面的转换
	 * @param fileSize
	 * @return
	 */
	public static String formatFileSize(String fileSize) {
		String value = "";
		try{
			long lf = Long.valueOf(fileSize);
			if (lf > 1024 * 1024 * 1024) {
				lf = Math.round(lf * 100 / (1024 * 1024 * 1024)) / 100;
				value = String.valueOf(lf) + "G";
			}else if (lf > 1024 * 1024) {
				lf = Math.round(lf * 100 / (1024 * 1024)) / 100;
				value = String.valueOf(lf) + "MB";
			} else {
				value = String.valueOf((Math.round(lf * 100 / 1024) / 100)) + "KB";
			}
		}catch(Exception e){
			logger.error("文件大小转换出现异常：" + e.getMessage());	
		}
		
		return value;
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
