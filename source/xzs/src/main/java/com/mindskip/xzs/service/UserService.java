package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.other.KeyValue;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.viewmodel.admin.user.UserPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;
public interface UserService extends BaseService<User> {

    User getUserById(Integer id);

    User getUserByUserName(String username);

    PageInfo<User> userPage(UserPageRequestVM requestVM);

    List<KeyValue> selectByUserName(String userName);

    void changePicture(User user, String imagePath);
}
