package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.other.KeyValue;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.viewmodel.admin.user.UserPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface UserMapper extends BaseMapper<User> {


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUserById(Integer id);

    User getUserByUserName(String username);

    List<User> userPage(UserPageRequestVM requestVM);

    List<KeyValue> selectByUserName(String userName);
}
