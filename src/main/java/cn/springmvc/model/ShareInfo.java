package cn.springmvc.model;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class ShareInfo implements Serializable {

	private static final long serialVersionUID = 3213895673280007732L;

	private String fileID;

	private String fileName;

	private String shareObjectID;

	private String shareObjectName;

	private String operator;

	private String createDate;

	public String getFileid() {
		return fileID;
	}

	public void setFileid(String fileID) {
		this.fileID = fileID == null ? null : fileID.trim();
	}

	public String getShareobjectid() {
		return shareObjectID;
	}

	public void setShareobjectid(String shareObjectID) {
		this.shareObjectID = shareObjectID == null ? null : shareObjectID
				.trim();
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getShareObjectName() {
		return shareObjectName;
	}

	public void setShareObjectName(String shareObjectName) {
		this.shareObjectName = shareObjectName;
	}
}