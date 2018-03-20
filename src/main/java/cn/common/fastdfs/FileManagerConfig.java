package cn.common.fastdfs;

import java.io.Serializable;

/**
 * 文件上传的相关属性封装
 * @author hzq
 */
public interface FileManagerConfig extends Serializable {
	public static final String FILE_DEFAULT_AUTHOR = "root";
	public static final String PROTOCOL = "http://";
	public static final String SEPARATOR = "/";
	public static final String TRACKER_NGNIX_ADDR = "10.177.2.241";
	public static final String TRACKER_NGNIX_PORT = "9999";	
	public static final String CLIENT_CONFIG_FILE = "fdfs_client.conf";
	public static final String CLIENT_CONFIG_FILE_PATH = "conf";
	public static final String SEPARATOR_COLON = ":";
}
