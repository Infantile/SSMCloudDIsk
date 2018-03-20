package cn.springmvc.service;

import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import cn.common.fastdfs.FastDFSFile;
import cn.common.fastdfs.FileManager;

/**
 * @author hzq
 *文件服务的基础类
 */
public abstract class BaseFileService {
	private static Logger logger = LoggerFactory.getLogger(BaseFileService.class);

	/**
	 * 文件上传
	 * @param multipartFile
	 * @return
	 */
	public String[] uploadFile(MultipartFile multipartFile, String loginUser){
		// 获取文件后缀名
		String ext = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
		FastDFSFile file = null;
		String[] uploadResult = null;
		try {
			file = new FastDFSFile(multipartFile.getBytes(), ext);
			NameValuePair[] meta_list = new NameValuePair[4];
			meta_list[0] = new NameValuePair("fileName", multipartFile.getOriginalFilename());
			meta_list[1] = new NameValuePair("fileLength", String.valueOf(multipartFile.getSize()));
			meta_list[2] = new NameValuePair("fileExt", ext);
			meta_list[3] = new NameValuePair("fileAuthor", loginUser);
			uploadResult = FileManager.upload(file, meta_list);
		} catch (Exception e) {
			logger.error("文件上传出现异常：" + e.getMessage());
		}
		
		return 	uploadResult;
    }

	/**
	 * 文件下载
	 * @param groupName 组名，例如group1
	 * @param remoteFileName 文件路径和名称，例如：M00/00/00/CrEC8VkWuKGANcMsAAApO9CVdGA62.xlsx
	 * @param specFileName 下载后对应文件名
	 * @return
	 */
    public ResponseEntity<byte[]> downloadFile(String groupName,
			String remoteFileName, String specFileName){
    	ResponseEntity<byte[]> content = null;
    	try {
    		content = FileManager.download(groupName, remoteFileName, specFileName);
    	} catch (Exception e) {
			logger.error("文件下载出现异常：" + e.getMessage());
		}
    	return content;
    }
    
	/**
	 * 文件删除
	 * @param groupName 组名，例如group1
	 * @param remoteFileName 文件路径和名称，例如：M00/00/00/CrEC8VkWuKGANcMsAAApO9CVdGA62.xlsx
	 * @return 0：删除成功  -1：发生异常  其他：删除失败
	 */
    public Integer delete(String groupName, String remoteFileName){
		return FileManager.delete(groupName, remoteFileName);   	
    }
    
    /**
     * 文件预览 
     * @param cloudDiskUser
     * @return
     */   
    public String previewFile(String fileID){
    	return null;
    }
    
}
