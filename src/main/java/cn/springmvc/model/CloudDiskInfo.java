package cn.springmvc.model;

import org.springframework.stereotype.Repository;

@Repository
public class CloudDiskInfo {

	private String userID;

    private String totalSize;

    private String usedSize;

    private String updateDate;

    public String getUserid() {
        return userID;
    }

    public void setUserid(String userID) {
        this.userID = userID == null ? null : userID.trim();
    }

    public String getTotalsize() {
        return totalSize;
    }

    public void setTotalsize(String totalSize) {
        this.totalSize = totalSize == null ? null : totalSize.trim();
    }

    public String getUsedsize() {
        return usedSize;
    }

    public void setUsedsize(String usedSize) {
        this.usedSize = usedSize == null ? null : usedSize.trim();
    }

    public String getUpdatedate() {
        return updateDate;
    }

    public void setUpdatedate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }
}