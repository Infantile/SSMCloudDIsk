package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.FileInfoMapper;
import cn.springmvc.dao.FolderInfoMapper;
import cn.springmvc.dao.ShareInfoMapper;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.MyFileManageService;
import cn.springmvc.service.MySharedManageService;
@Service
public class MySharedManageServiceImpl extends BaseFileService implements
		MySharedManageService {
	private static Logger logger = LoggerFactory
			.getLogger(MySharedManageServiceImpl.class);
	@Autowired
	private ShareInfoMapper shareInfoMapper;
	@Autowired
	private FileInfoMapper fileInfoMapper;
	@Autowired
	private FolderInfoMapper folderInfoMapper;
	@Autowired
	private MyFileManageService myFileManageService;
	@Override
	public int cancelSharedFile(ShareInfo shareInfo) {
		// TODO Auto-generated method stub
		return shareInfoMapper.deleteMySharedInfo(shareInfo);
	}

	@Override
	public String filePreview(FileInfo fileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fileDownloader(FileInfo fileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (实现接口方法)
	 * @see cn.springmvc.service.MySharedManageService#queryMySharedFileListInfo(cn.springmvc.model.ShareInfo)
	 */
	@Override
	public List<QueryMySharedFileInfo> queryMySharedFileListInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return shareInfoMapper.queryMySharedFileTableInfo(map);
	}

	/* (实现接口方法)
	 * @see cn.springmvc.service.MySharedManageService#queryMySharedFileListInfoByFolderID(cn.springmvc.model.ShareInfo)
	 */
	@Override
	public List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<QueryMySharedFileInfo> resultList=new ArrayList<QueryMySharedFileInfo>();
		List<QueryMySharedFileInfo> queryMySharedFileInfos1 = queryMySharedFileListInfo(paramMap);
		List<FolderInfo>  folderInfos=folderInfoMapper.queryAllParentFolderNodeInfo(paramMap.get("folderID").toString());
		List<QueryMySharedFileInfo> queryMySharedFileInfos2=shareInfoMapper.queryNextPageSharedFileInfoByFolderID(paramMap);
		for (int j = 0; j < queryMySharedFileInfos1.size(); j++) {
			for (int k = 0; k < folderInfos.size(); k++) {
				if (queryMySharedFileInfos1.get(j).getFileid().equals(folderInfos.get(k).getFolderid())) {
					if (queryMySharedFileInfos2.size()>0) {
						for(QueryMySharedFileInfo model : queryMySharedFileInfos2){
							model.setOperator(queryMySharedFileInfos1.get(j).getOperator());
							model.setShareObjectName(queryMySharedFileInfos1.get(j).getShareObjectName());
							model.setCreatedate(queryMySharedFileInfos1.get(j).getCreatedate());
							resultList.add(model);
						}
						return resultList;
					}	
				}
			}
		}			
		return resultList;
	}
	
	public List<QueryMySharedFileInfo> queryMySharedSearchInfo(Map<String, Object> map,Map<String, Object> map2) {
		// TODO Auto-generated method stub
		List<QueryMySharedFileInfo> queryMySharedFileInfos = new ArrayList<QueryMySharedFileInfo>();
		List<QueryMySharedFileInfo> queryMySharedFileResultInfos = new ArrayList<QueryMySharedFileInfo>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			queryMySharedFileInfos=queryMySharedFileListInfo(map);
			//从我的文件中模糊查询出搜索的关键字文件
			fileInfoList = fileInfoMapper.querySharedFileSearchInfo(map2);
			//筛选出该文件或者直系父目录是否在我的共享列表中
			for (int i = 0; i < fileInfoList.size(); i++) {
				FileInfo fileInfo=fileInfoList.get(i);
				List<FolderInfo>  folderInfos=folderInfoMapper.queryAllParentFolderNodeInfo(fileInfo.getFileid());
				for (int j = 0; j < queryMySharedFileInfos.size(); j++) {
					for (int k = 0; k < folderInfos.size(); k++) {
						if (queryMySharedFileInfos.get(j).getFileid().equals(folderInfos.get(k).getFolderid())) {
							//该搜索文件在我的共享文件中，生成一个共享对象
							QueryMySharedFileInfo queryMySharedFileInfo=new QueryMySharedFileInfo();
							queryMySharedFileInfo.setFileid(fileInfo.getFileid());
							queryMySharedFileInfo.setFileName(fileInfo.getFilename());
						    queryMySharedFileInfo.setShareobjectid(queryMySharedFileInfos.get(j).getShareobjectid());
						    queryMySharedFileInfo.setShareObjectName(queryMySharedFileInfos.get(j).getShareObjectName());
						    queryMySharedFileInfo.setOperator(queryMySharedFileInfos.get(j).getOperator());
						    queryMySharedFileInfo.setCreatedate(queryMySharedFileInfos.get(j).getCreatedate());
						    //判断文件是否是目录
						  //判断文件的类型，是文件夹还是文件
							if ("file".equals(myFileManageService.fileType(fileInfo.getFileid()))){
								queryMySharedFileInfo.setFileSize(fileInfo.getFilesize());
								queryMySharedFileInfo.setFileType(fileInfo.getFiletype());
							}else {
								queryMySharedFileInfo.setFileSize(null);
								queryMySharedFileInfo.setFileType("文件夹");
							}
							queryMySharedFileResultInfos.add(queryMySharedFileInfo);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("搜索框输入查询功能发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return queryMySharedFileResultInfos;
	}

}
