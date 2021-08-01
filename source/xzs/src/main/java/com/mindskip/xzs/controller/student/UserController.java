package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.base.SystemCode;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.enums.RoleEnum;
import com.mindskip.xzs.domain.enums.UserStatusEnum;
import com.mindskip.xzs.service.AuthenticationService;
import com.mindskip.xzs.service.UserService;
import com.mindskip.xzs.viewmodel.student.user.UserRegisterVM;
import com.mindskip.xzs.viewmodel.student.user.UserResponseVM;
import com.mindskip.xzs.viewmodel.student.user.UserUpdatePasswordVM;
import com.mindskip.xzs.viewmodel.student.user.UserUpdateVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RestController("StudentUserController")
@RequestMapping(value = "/api/student/user")
public class UserController extends BaseApiController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestResponse register(@RequestBody @Valid UserRegisterVM model) {
        if (!model.getPassword().equals(model.getPasswordConfirm())) {
            return new RestResponse<>(SystemCode.ParameterValidError.getCode(), "Inconsistent password input.");
        }
        User existUser = userService.getUserByUserName(model.getUserName());
        if (null != existUser) {
            return new RestResponse<>(2, "User has already exist!");
        }
        User user = modelMapper.map(model, User.class);
        String encodePwd = authenticationService.pwdEncode(model.getPassword());
        user.setUserUuid(UUID.randomUUID().toString());
        user.setPassword(encodePwd);
        user.setRole(RoleEnum.STUDENT.getCode());
        user.setStatus(UserStatusEnum.Enable.getCode());
        user.setLastActiveTime(new Date());
        user.setCreateTime(new Date());
        user.setDeleted(false);
        userService.insertByFilter(user);
        return RestResponse.ok();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
        if (StringUtils.isBlank(model.getBirthDay())) {
            model.setBirthDay(null);
        }
        User user = userService.selectById(getCurrentUser().getId());
        modelMapper.map(model, user);
        if (user.getBirthDay() != null) {
            Calendar now = Calendar.getInstance();
            Calendar born = Calendar.getInstance();
            now.setTime(new Date());
            born.setTime(user.getBirthDay());
            user.setAge(now.get(Calendar.YEAR) - born.get(Calendar.YEAR));
        }
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public RestResponse updatePassword(@RequestBody @Valid UserUpdatePasswordVM model) {
        if (!model.getNewPassword().equals(model.getNewPasswordConfirm())) {
            return new RestResponse<>(SystemCode.ParameterValidError.getCode(), "Inconsistent password input.");
        }
        if (model.getNewPassword().length() < 6) {
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
            return RestResponse.fail(2, "Password error!");
        }
    }
}
