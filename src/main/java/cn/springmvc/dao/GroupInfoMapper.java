package cn.springmvc.dao;

import org.springframework.stereotype.Component;

import cn.springmvc.model.GroupInfo;

@Component
public interface GroupInfoMapper {
    int deleteByPrimaryKey(String groupid);

    int insert(GroupInfo record);

    int insertSelective(GroupInfo record);

    GroupInfo selectByPrimaryKey(String groupid);

    boolean updateByPrimaryKeySelective(GroupInfo record);

    boolean updateByPrimaryKey(GroupInfo record);
}