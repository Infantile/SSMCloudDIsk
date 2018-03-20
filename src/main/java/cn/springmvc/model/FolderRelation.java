package cn.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class FolderRelation{
	private String userID;

    private String parentFolderID;

    private String childrenFolderID;
    
	private String isRootFolder;

    public String getUserid() {
        return userID;
    }

    public void setUserid(String userID) {
        this.userID = userID == null ? null : userID.trim();
    }

    public String getParentfolderid() {
        return parentFolderID;
    }

    public void setParentfolderid(String parentFolderID) {
        this.parentFolderID = parentFolderID == null ? null : parentFolderID.trim();
    }

    public String getChildrenfolderid() {
        return childrenFolderID;
    }

    public void setChildrenfolderid(String childrenFolderID) {
        this.childrenFolderID = childrenFolderID == null ? null : childrenFolderID.trim();
    }

	public String getIsRootFolder() {
		return isRootFolder;
	}

	public void setIsRootFolder(String isRootFolder) {
		this.isRootFolder = isRootFolder == null ? null : isRootFolder.trim();
	}
	
}