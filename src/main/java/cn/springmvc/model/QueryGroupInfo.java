package cn.springmvc.model;

import java.io.Serializable;

public class QueryGroupInfo implements Serializable{

	private static final long serialVersionUID = -6072979765589352104L;
	
	private String groupID;

    private String groupName;

    private String userName;

    private String createDate;

    private String userLoginID;

    public String getGroupid() {
        return groupID;
    }

    public void setGroupid(String groupID) {
        this.groupID = groupID == null ? null : groupID.trim();
    }

    public String getGroupname() {
        return groupName;
    }

    public void setGroupname(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getCreatedate() {
        return createDate;
    }

    public void setCreatedate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }
    
    public String getUserLoginID() {
		return userLoginID;
	}

	public void setUserLoginID(String userLoginID) {
		this.userLoginID = userLoginID == null ? null : userLoginID.trim();
	}
}
