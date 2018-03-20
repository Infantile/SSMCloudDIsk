package cn.common;

import java.io.File;

import org.apache.log4j.DailyRollingFileAppender;

/**
 * 日志文件夹不存在时候自动生成文件夹
 * 有些log4j版本不自动生成文件夹
 * @author hzq
 *
 */
public class LogFileAppender extends DailyRollingFileAppender{
	@Override
    public void setFile(String file) {
        String filePath = file;
        File fileCheck = new File(filePath);
        if (!fileCheck.exists())
            fileCheck.getParentFile().mkdirs();
        super.setFile(filePath);
    }
}
