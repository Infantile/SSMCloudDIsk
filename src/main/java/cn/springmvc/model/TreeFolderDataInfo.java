package cn.springmvc.model;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TreeFolderDataInfo {
	/**嵌套节点 */
	private List<TreeFolderDataInfo> nodes;
	/**列表树节点ID */
	private String id;
	/**列表树节点上的文本，通常是节点右边的小图标 */
    private String text;
    /**通过结合全局showTags选项来在列表树节点的右边添加额外的信息*/
    private String tags;
    
	public List<TreeFolderDataInfo> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeFolderDataInfo> nodes) {
		this.nodes = nodes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}