package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.common.CommonConstant;
import cn.springmvc.dao.CloudDiskInfoMapper;
import cn.springmvc.dao.CollectInfoMapper;
import cn.springmvc.dao.FileInfoMapper;
import cn.springmvc.dao.FolderInfoMapper;
import cn.springmvc.dao.FolderRelationMapper;
import cn.springmvc.dao.RecycleInfoMapper;
import cn.springmvc.dao.ShareInfoMapper;
import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.model.CollectInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.FolderRelation;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.MyFileManageService;
import cn.springmvc.service.RecycleFileManageService;
import cn.utils.DateUtils;

@Service
public class RecycleFileManageServiceImpl extends BaseFileService implements
		RecycleFileManageService {
	private static Logger logger = LoggerFactory
			.getLogger(RecycleFileManageServiceImpl.class);
	@Autowired
	private RecycleInfoMapper recycleInfoMapper;
	@Autowired
	private FileInfoMapper fileInfoMapper;
	@Autowired
	private FolderInfoMapper folderInfoMapper;
    @Autowired
	private MyFileManageService myFileManageService;
    @Autowired
    private FolderRelationMapper folderRelationMapper;
    @Autowired
    private CloudDiskInfoMapper cloudDiskInfoMapper;
    @Autowired
    private CollectInfoMapper collectInfoMapper;
    @Autowired
    private ShareInfoMapper shareInfoMapper;
	@Override
	public List<FileInfo> queryRecycleTableInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return recycleInfoMapper.queryRecycleTableInfo(map);
	}

	@Transactional
	@Override
	public String restoreFile(String loginUserId,
			String fileID) {
		String result="error";
		try {			
			        //父目录不存在，已被彻底删除了
					if (folderInfoMapper.queryParentFolderInfo(fileID).size()==0) {
						//需要把文件还原到我的文件根目录下
						//判断文件的类型，是文件夹还是文件
						if ("file".equals(myFileManageService.fileType(fileID))) {
							//1.更新文件信息表
							FileInfo fileInfo = new FileInfo();
							fileInfo.setFileid(fileID);
							fileInfo.setFilestate("normal");
							fileInfoMapper.updateFileInfo(fileInfo);
						}else if("folder".equals(myFileManageService.fileType(fileID))){
							//1.更新目录信息表
							FolderInfo folderInfo = new FolderInfo();
							folderInfo.setFolderid(fileID);
							folderInfo.setFolderState("normal");
							folderInfoMapper.updateFolderInfo(folderInfo);
						}
						//2.更新目录关系表
						FolderRelation folderRelation=new FolderRelation();
						folderRelation.setChildrenfolderid(fileID);
						folderRelation.setUserid(loginUserId);
						folderRelation.setIsRootFolder(CommonConstant.IS_ROOT_FOLDER);
						folderRelation.setParentfolderid("");
						folderRelationMapper.setRootFolder(folderRelation);
						//3.删除回收信息
						RecycleInfo recycleInfo = new RecycleInfo();
						recycleInfo.setUserid(loginUserId);
						recycleInfo.setRecycleobjectid(fileID);
						recycleInfoMapper.deleteRecycleInfoInfo(recycleInfo);
						result="root";
					}//如果父目录存在，且在回收列表中，必须先还原其父目录才能还原该文件，否则只能将其还原到我的文件根目录下
					else if (parentFolderExistRecycleList(loginUserId, fileID)!=null) {
						String folderID=parentFolderExistRecycleList(loginUserId, fileID);
						FolderInfo folderInfo=new FolderInfo();
						folderInfo.setFolderid(folderID);
						String folderName=folderInfoMapper.queryFolderInfo(folderInfo).get(0).getFoldername();
						result=folderName;
						return result;
					}//如果父目录存在，且不在回收列表中，可以直接还原文件到其父目录下。
					else {
						//判断文件的类型，是文件夹还是文件
						if ("file".equals(myFileManageService.fileType(fileID))) {
							//1.更新文件信息表
							FileInfo fileInfo = new FileInfo();
							fileInfo.setFileid(fileID);
							fileInfo.setFilestate("normal");
							fileInfoMapper.updateFileInfo(fileInfo);
						}else if("folder".equals(myFileManageService.fileType(fileID))){
							//1.更新目录信息表
							FolderInfo folderInfo = new FolderInfo();
							folderInfo.setFolderid(fileID);
							folderInfo.setFolderState("normal");
							folderInfoMapper.updateFolderInfo(folderInfo);
						}
						//3.删除回收信息
						RecycleInfo recycleInfo = new RecycleInfo();
						recycleInfo.setUserid(loginUserId);
						recycleInfo.setRecycleobjectid(fileID);
						recycleInfoMapper.deleteRecycleInfoInfo(recycleInfo);
						result="success";
					}				
		} catch (Exception e) {
			logger.error("恢复删除文件对象数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return result;
	}
	
    @Transactional
	@Override
	public boolean absolutelyDeleteFile(String fileID,String userID) {
		// TODO Auto-generated method stub
    	boolean result = false;
    	try {
    		FileInfo fileInfo=new FileInfo();
			fileInfo.setFileid(fileID);
    		List<FileInfo> fInfos=fileInfoMapper.queryFileInfo(fileInfo);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fileObjId", fileInfo.getFileid());
			//判断是否是文件，文件直接删除
	    	if (fInfos.size()>0) {
				//1.删除目录关系表的记录
				FolderRelation folderRelation=new FolderRelation();
				folderRelation.setChildrenfolderid(fInfos.get(0).getFileid());
				folderRelationMapper.deleteFolderRelation(folderRelation);		
				if (fileInfoMapper.queryFileNumber(fileInfo).size()>1) {
					//同一份文件有多份时，不删除实体文件，仅仅删除数据库纪录
					//2.删除文件表的记录
					fileInfoMapper.deleteFileInfo(fInfos.get(0));
				}else {
					//2.删除文件表的记录
					fileInfoMapper.deleteFileInfo(fInfos.get(0));
					//3.删除实体文件
					super.delete(fInfos.get(0).getFilegroupname(), fInfos.get(0).getFilepath());
				}	
				//4.更新用户已用的存储容量
				CloudDiskInfo cloudDiskInfo=new CloudDiskInfo();
				cloudDiskInfo.setUserid(userID);
				cloudDiskInfo.setUsedsize(fInfos.get(0).getFilesize());
				cloudDiskInfo.setUpdatedate(DateUtils.getSystemTime());
				cloudDiskInfoMapper.updateCloudDiskCapacityInfo(cloudDiskInfo);
				//5.删除回收纪录
				RecycleInfo recycleInfo = new RecycleInfo();
				recycleInfo.setUserid(userID);
				recycleInfo.setRecycleobjectid(fileID);
				recycleInfoMapper.deleteRecycleInfoInfo(recycleInfo);
				//6.删除收藏夹纪录
				CollectInfo collectInfo=new CollectInfo();
				collectInfo.setUserid(userID);
				collectInfo.setCollectobjectid(fileID);
				collectInfoMapper.deleteCollectInfoInfo(collectInfo);
				//7.删除共享纪录
				ShareInfo shareInfo =new ShareInfo();
				shareInfo.setFileid(fileID);
				shareInfoMapper.deleteSharedInfoInfo(shareInfo);
			}//该文件是目录，且为空目录
	    	else if (myFileManageService.queryFolderContainInfo(paramMap).size()==0) {
				//1.删除目录表的记录即可
				FolderInfo folderInfo=new FolderInfo();
				folderInfo.setFolderid(fileInfo.getFileid());
				folderInfoMapper.deleteFolderInfo(folderInfo);
				//2.删除目录关系表的记录
				FolderRelation folderRelation=new FolderRelation();
				folderRelation.setChildrenfolderid(fileInfo.getFileid());
				folderRelationMapper.deleteFolderRelation(folderRelation);
				//3.删除回收纪录
				RecycleInfo recycleInfo = new RecycleInfo();
				recycleInfo.setUserid(userID);
				recycleInfo.setRecycleobjectid(fileID);
				recycleInfoMapper.deleteRecycleInfoInfo(recycleInfo);
				//4.删除收藏夹纪录
				CollectInfo collectInfo=new CollectInfo();
				collectInfo.setUserid(userID);
				collectInfo.setCollectobjectid(fileID);
				collectInfoMapper.deleteCollectInfoInfo(collectInfo);
				//5.删除共享纪录
				ShareInfo shareInfo =new ShareInfo();
				shareInfo.setFileid(fileID);
				shareInfoMapper.deleteSharedInfoInfo(shareInfo);
			}//该文件是目录，且下面还有子文件，进行递归操作
			else {
				Map<String, Object> paramMap2 = new HashMap<String, Object>();
				List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
				paramMap2.put("fileObjId", fileID);
				fileInfoList = myFileManageService.queryFolderContainInfo(paramMap2);
				//循环遍历删除子文件，可能是目录也可能是文件
				for (FileInfo file : fileInfoList) {
					RecycleInfo recycleInfo=new RecycleInfo();
					recycleInfo.setRecycleobjectid(file.getFileid());
					if (recycleInfoMapper.queryRecycleInfoInfo(recycleInfo).size()>0) {
						continue;
					}else {
						absolutelyDeleteFile(file.getFileid(),userID);
					}				
				}
				//1.删除目录表的记录即可
				FolderInfo folderInfo=new FolderInfo();
				folderInfo.setFolderid(fileInfo.getFileid());
				folderInfoMapper.deleteFolderInfo(folderInfo);
				//2.删除目录关系表的记录
				FolderRelation folderRelation=new FolderRelation();
				folderRelation.setChildrenfolderid(fileInfo.getFileid());
				folderRelationMapper.deleteFolderRelation(folderRelation);
				//3.删除回收纪录
				RecycleInfo recycleInfo = new RecycleInfo();
				recycleInfo.setUserid(userID);
				recycleInfo.setRecycleobjectid(fileID);
				recycleInfoMapper.deleteRecycleInfoInfo(recycleInfo);
				//4.删除收藏夹纪录
				CollectInfo collectInfo=new CollectInfo();
				collectInfo.setUserid(userID);
				collectInfo.setCollectobjectid(fileID);
				collectInfoMapper.deleteCollectInfoInfo(collectInfo);
				//5.删除共享纪录
				ShareInfo shareInfo =new ShareInfo();
				shareInfo.setFileid(fileID);
				shareInfoMapper.deleteSharedInfoInfo(shareInfo);
			}
			//该次文件删除成功
			result=true;
		} catch (Exception e) {
			logger.error("删除文件操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public String filePreview(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查看回收文件的父目录(包括一级父目录，二级父目录，...)是否也在回收列表中
	 * @param userID
	 * @param fileID
	 * @return 返回离它最近的父目录ID
	 */
	public String parentFolderExistRecycleList(String userID,String fileID){
		RecycleInfo recycleInfo = new RecycleInfo();
		recycleInfo.setUserid(userID);
		List<RecycleInfo> recycleInfos=recycleInfoMapper.queryRecycleInfoInfo(recycleInfo);
		List<FolderInfo>  folderInfos=folderInfoMapper.queryAllParentFolderNodeInfo(fileID);
		if (folderInfos.size()>1 && recycleInfos.size()>1) {
			for (int i = 1; i < folderInfos.size(); i++) {
				for (int j = 0; j < recycleInfos.size(); j++) {
					if (folderInfos.get(i).getFolderid().equals(recycleInfos.get(j).getRecycleobjectid())) {
						String result=recycleInfos.get(j).getRecycleobjectid();
						return result;
					}else {
						continue;
					}
				}
			}
		}	
		return null;		
	}

	@Override
	public List<FileInfo> queryRecycleSearchInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try{
			fileInfoList = recycleInfoMapper.querySearchRecycleInfo(map);			
		}catch(Exception e){
			logger.error("搜索框输入查询功能发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return fileInfoList;
	}

	/* (实现接口方法)
	 * @see cn.springmvc.service.RecycleFileManageService#queryRecycleInfoInfo(cn.springmvc.model.RecycleInfo)
	 */
	@Override
	public List<RecycleInfo> queryRecycleInfoInfo(RecycleInfo recycleInfo) {
		// TODO Auto-generated method stub
		return recycleInfoMapper.queryRecycleInfoInfo(recycleInfo);
	}
}
