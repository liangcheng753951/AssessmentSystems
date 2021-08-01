package com.mindskip.xzs.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.base.SystemCode;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.enums.UserStatusEnum;
import com.mindskip.xzs.domain.other.KeyValue;
import com.mindskip.xzs.service.AuthenticationService;
import com.mindskip.xzs.service.SubjectService;
import com.mindskip.xzs.service.UserService;
import com.mindskip.xzs.utility.DateTimeUtil;
import com.mindskip.xzs.utility.PageInfoHelper;
import com.mindskip.xzs.viewmodel.admin.user.*;
import com.mindskip.xzs.viewmodel.student.user.UserUpdatePasswordVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController("AdminUserController")
@RequestMapping(value = "/api/admin/user")
public class UserController extends BaseApiController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    @Autowired
    private SubjectService subjectService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserResponseVM>> pageList(@RequestBody UserPageRequestVM model) {
        PageInfo<User> pageInfo = userService.userPage(model);
        PageInfo<UserResponseVM> page = PageInfoHelper.copyMap(pageInfo, user -> UserResponseVM.from(user, subjectService));
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> select(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserResponseVM userVm = UserResponseVM.from(user, subjectService);
        return RestResponse.ok(userVm);
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user, subjectService);
        return RestResponse.ok(userVm);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody @Valid UserCreateVM model) {
        if (model.getId() == null) {  //create
            User existUser = userService.getUserByUserName(model.getUserName());
            if (null != existUser) {
                return new RestResponse<>(2, "user is exist!");
            }
            model.setPassword("123456");
        }
        if (StringUtils.isBlank(model.getBirthDay())) {
            return new RestResponse<>(2, "please input the birthday");
        }
        User user = modelMapper.map(model, User.class);
        Calendar now = Calendar.getInstance();
        Calendar born = Calendar.getInstance();

        now.setTime(new Date());
        born.setTime(user.getBirthDay());
        user.setAge(now.get(Calendar.YEAR) - born.get(Calendar.YEAR));
        user.setModules(model.getModules().stream().map(String::valueOf).collect(Collectors.joining(",")));
        if (model.getId() == null) {
            String encodePwd = authenticationService.pwdEncode(model.getPassword());
            user.setPassword(encodePwd);
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setLastActiveTime(new Date());
            user.setDeleted(false);
            userService.insertByFilter(user);
        } else {
            if (!StringUtils.isBlank(model.getPassword())) {
                String encodePwd = authenticationService.pwdEncode(model.getPassword());
                user.setPassword(encodePwd);
            }
            user.setModifyTime(new Date());
            userService.updateByIdFilter(user);
        }
        return RestResponse.ok(user);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
        User user = userService.selectById(getCurrentUser().getId());
        modelMapper.map(model, user);
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public RestResponse updatePassword(@RequestBody @Valid UserUpdatePasswordVM model) {
        if (!model.getNewPassword().equals(model.getNewPasswordConfirm())) {
            return new RestResponse<>(SystemCode.ParameterValidError.getCode(), "Inconsistent password input.");
        }
        if (model.getNewPassword().length() < 6){
            return new RestResponse<>(SystemCode.ParameterValidError.getCode(), "Password can not less than 6 characters.");
        }
        User user = userService.selectById(getCurrentUser().getId());
        boolean validResult = authenticationService.authUser(user, user.getUserName(), model.getCurrentPassword());
        if (validResult) {
            String encodePwd = authenticationService.pwdEncode(model.getNewPassword());
            user.setPassword(encodePwd);
            user.setModifyTime(new Date());
            userService.updateByIdFilter(user);
            return RestResponse.ok();
        } else {
            return RestResponse.fail(2,  "Password error!");
        }
    }


    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        Integer newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
        user.setStatus(newStatus);
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);
        return RestResponse.ok(newStatus);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setDeleted(true);
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }


    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {
        List<KeyValue> keyValues = userService.selectByUserName(userName);
        return RestResponse.ok(keyValues);
    }

}
