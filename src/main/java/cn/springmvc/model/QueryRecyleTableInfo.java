package cn.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class QueryRecyleTableInfo extends RecycleInfo {
	private FileInfo fileInfo;
	
	private FolderInfo folderInfo;

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public FolderInfo getFolderInfo() {
		return folderInfo;
	}

	public void setFolderInfo(FolderInfo folderInfo) {
		this.folderInfo = folderInfo;
	}
}
