package cn.springmvc.model;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class QueryShareObjectInfo implements Serializable{
	
	private static final long serialVersionUID = -969551364572263511L;
	private String shareObjectID;
	private String shareObjectName;
	private String shareObjectLoginID;
	private String shareObjectFlag;
	private String shareSearchString;
	public String getShareObjectID() {
		return shareObjectID;
	}
	public void setShareObjectID(String shareObjectID) {
		this.shareObjectID = shareObjectID;
	}
	public String getShareObjectName() {
		return shareObjectName;
	}
	public void setShareObjectName(String shareObjectName) {
		this.shareObjectName = shareObjectName;
	}
	public String getShareObjectLoginID() {
		return shareObjectLoginID;
	}
	public void setShareObjectLoginID(String shareObjectLoginID) {
		this.shareObjectLoginID = shareObjectLoginID;
	}
	public String getShareObjectFlag() {
		return shareObjectFlag;
	}
	public void setShareObjectFlag(String shareObjectFlag) {
		this.shareObjectFlag = shareObjectFlag;
	}
	public String getShareSearchString() {
		return shareSearchString;
	}
	public void setShareSearchString(String shareSearchString) {
		this.shareSearchString = shareSearchString;
	}	
}
