package cn.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSonUtils {
	
	private static Logger logger = LoggerFactory.getLogger(JSonUtils.class); 

	/**
	 * 实体类型转换成JSON对象
	 * @param obj
	 * @return
	 */
	public static JSONObject objectToJson(Object obj) {
		JSONObject jsonResult = null;
		try {
			jsonResult = JSONObject.fromObject(obj);
		} catch (Exception e) {
			logger.error("转换的对象不是JSON的格式");
		}
		return jsonResult;
	}
	
	/**
	 * 通过制定的keyList获取json对应的value值
	 * @param keyList
	 * @param jsonObject
	 * @return
	 */
	public static String getJsonKeyValue(List<String> keyList, JSONObject jsonObject) {
		String keyValue = null;	
		
		if(keyList.size() > 0 && jsonObject != null){
			JSONObject jsonTemp = jsonObject;
			for(int i = 0; i < keyList.size(); i++) {
				if(i != 0){
					jsonTemp = JSonUtils.objectToJson(keyValue);
				}
				//通过制定的key获取json对应的value值
				keyValue = getJsonKeyValue(keyList.get(i), jsonTemp);
				
				//指定的key获取不到对应的value值，返回null
				if(jsonTemp == null) {					
					keyValue = null;
					break;
				}
			}
		}
		
		return keyValue;
	}
	
	/**
	 * 通过制定的key获取json对应的value值
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static String getJsonKeyValue(String key, JSONObject jsonObject) {
		String keyValue = null;	
		
		if(jsonObject != null && jsonObject.containsKey(key)){
			keyValue = jsonObject.getString(key);
		} else {
			//指定的key获取不到对应的value值，返回null
			logger.error("指定的key[" + key + "]获取不到对应的value值");
		}
		
		return keyValue;
	}
	
	/**
	 * 获取json对应的key值，从第一层开始到需要指定获取值的那一层的key值
	 * @param jsonKey 可以是多个参数
	 * @return
	 */
	public static List<String> getJsonKey(String... jsonKey) {
		List<String> keyList = new ArrayList<String>();
		if(jsonKey != null) {
			for(String key : jsonKey){
				keyList.add(key);
			}
		}
		
		return keyList;
	}

}
