package cn.springmvc.model;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
@Repository
public class CloudDiskUser implements Serializable {
	
	private static final long serialVersionUID = 3869467412527431839L;

	private String userID;
	
	private String userLoginID;

    private String userName;

    private String password;

    private String operator;

    private String createDate;

    public String getUserid() {
        return userID;
    }

    public void setUserid(String userID) {
        this.userID = userID == null ? null : userID.trim();
    }

    public String getUserLoginID() {
		return userLoginID;
	}

	public void setUserLoginID(String userLoginID) {
		this.userLoginID = userLoginID == null ? null : userLoginID.trim();
	}

	public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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