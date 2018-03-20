package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import cn.springmvc.model.FileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.model.TreeFolderDataInfo;

/**
 * @author 胡志强 2017年5月23日
 */
public interface MyFileManageService {
	/**
	 * 查看我的文件列表信息
	 * 
	 * @param map
	 * @return
	 */
	List<FileInfo> queryMyFileListInfo(Map<String, Object> map);

	/**
	 * 点击目录查询目录下所有文件以及文件目录
	 * 
	 * @param map
	 * @return
	 */
	List<FileInfo> queryFolderContainInfo(Map<String, Object> map);

	/**
	 * 根据文件ID获取其详细信息，以便后续下载文件
	 * 
	 * @param map
	 * @return List<FileInfo>
	 */
	List<FileInfo> queryMyFileInfo(Map<String, Object> map);

	/**
	 * 输入搜索框查询列表信息
	 * 
	 * @param map
	 * @return
	 */
	List<FileInfo> querySearchInfo(Map<String, Object> map);

	/**
	 * 创建文件夹
	 * 
	 * @param loginUser
	 * @param loginUserId
	 * @param folderName
	 * @param currentFolderId
	 * @return
	 */
	boolean createFolder(String loginUser, String loginUserId,
			String folderName, String currentFolderId);

	/**
	 * 收藏文件对象（文件或者文件夹）
	 * 
	 * @param loginUser
	 * @param loginUserId
	 * @param fileObjId
	 * @return
	 */
	boolean collectObject(String loginUser, String loginUserId, String fileObjId);

	/**
	 * 复制或者移动文件
	 * 
	 * @param loginUserId
	 *            登录用户ID
	 * @param parentFolderID
	 *            复制或者移动到的目录ID
	 * @param fileInfoList
	 *            复制移动到目录里面的所有文件和目录（用于判断重名）
	 * @param childrenFolderIdJSONArray
	 *            界面多选复制移动的文件目录对象
	 * @param controlType
	 *            复制或者移动区别
	 * @return
	 */
	List<String> copyOrmoveTo(String loginUserId, String parentFolderID,
			List<FileInfo> fileInfoList, JSONArray childrenFolderIdJSONArray,
			int controlType);

	/**
	 * 从我的文件中共享文件
	 * 
	 * @param shareInfo
	 * @return int
	 */
	int shareFileFromMyFile(ShareInfo shareInfo);

	/**
	 * 查询共享对象信息,包括个人，群组，以及all
	 * 
	 * @param cloudDiskUser
	 * @param groupInfo
	 * @return List<GroupInfo>
	 */
	List<QueryShareObjectInfo> queryShareObjectInfo(
			QueryShareObjectInfo queryShareObjectInfo);

	/**
	 * 重命名文件
	 * 
	 * @param fileInfo
	 * @return boolean
	 */
	boolean renameFile(String fileType, Map<String, Object> map);

	/**
	 * 文件上传
	 * 
	 * @param multipartFile
	 * @param rows
	 * @param loginUser
	 * @param loginUserId
	 * @param comment
	 * @param parentFolderID
	 * @return
	 */
	boolean fileUploader(MultipartFile multipartFile, String rows,
			String loginUser, String loginUserId, String comment,
			String parentFolderID);

	/**
	 * 文件下载
	 * 
	 * @param loginUser
	 * @param loginUserId
	 * @param fileInfo
	 * @return
	 */
	ResponseEntity<byte[]> fileDownloader(String loginUser, String loginUserId,
			FileInfo fileInfo);

	/**
	 * 文件预览
	 * 
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);

	/**
	 * 删除文件
	 * 
	 * @param fileID
	 * @return int
	 */
	int deleteFile(String fileID, RecycleInfo recycleInfo);

	/**
	 * 目标目录下拉框值
	 * 
	 * @param loginUserId
	 * @param parentFolderID
	 * @param selectFolderIds
	 * @return
	 */
	TreeFolderDataInfo queryFolderIds(String loginUserId,
			String parentFolderID, String selectFolderIds);

	/**
	 * 判断文件类型，比如是文件，还是目录，还是两样都不是
	 * 
	 * @param fileID
	 * @return 返回"file"表示文件,"folder"表示目录，"none"表示不存在
	 */
	String fileType(String fileID);

	/**
	 * 判断是否重复共享文件
	 * 
	 * @param shareInfo
	 * @return 返回未曾共享的文件信息及已共享文件的提示信息
	 */
	Map<String, Object> checkRepeatedSharedFile(ShareInfo shareInfo);
}
