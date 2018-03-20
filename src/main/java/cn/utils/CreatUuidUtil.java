package cn.utils;

import java.util.UUID;

public class CreatUuidUtil {
   public static String getUuid(){
	   return UUID.randomUUID().toString().replaceAll("-", "");
   }
}
