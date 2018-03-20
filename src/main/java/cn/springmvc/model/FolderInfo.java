package cn.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class FolderInfo {

	private String folderID;

    private String folderName;
    
    private String folderState;

    private String operator;

    private String createDate;

    public String getFolderid() {
        return folderID;
    }

    public void setFolderid(String folderID) {
        this.folderID = folderID == null ? null : folderID.trim();
    }

    public String getFoldername() {
        return folderName;
    }

    public void setFoldername(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    } 

    public String getFolderState() {
		return folderState;
	}

	public void setFolderState(String folderState) {
		this.folderState = folderState == null ? null : folderState.trim();
	}

	public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getCreatedate() {
        return createDate;
    }

    public void setCreatedate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }
}