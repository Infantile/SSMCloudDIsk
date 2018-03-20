package cn.springmvc.model;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class GroupInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7334007525791648222L;

	private String groupID;

    private String groupName;

    private String operator;

    private String createDate;

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