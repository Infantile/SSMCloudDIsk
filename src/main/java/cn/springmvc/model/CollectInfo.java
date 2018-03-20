package cn.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class CollectInfo{
	
	private String userID;

    private String collectObjectID;

    private String operator;

    private String createDate;

    public String getUserid() {
        return userID;
    }

    public void setUserid(String userID) {
        this.userID = userID == null ? null : userID.trim();
    }

    public String getCollectobjectid() {
        return collectObjectID;
    }

    public void setCollectobjectid(String collectObjectID) {
        this.collectObjectID = collectObjectID == null ? null : collectObjectID.trim();
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