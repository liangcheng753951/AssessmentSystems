package com.mindskip.xzs.viewmodel.admin.user;

import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.service.SubjectService;
import com.mindskip.xzs.utility.DateTimeUtil;
import com.mindskip.xzs.viewmodel.BaseVM;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
public class UserResponseVM extends BaseVM {

    private Integer id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    private Integer role;

    private Integer sex;

    private String birthDay;

    private String lastActiveTime;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private String imagePath;

    private List<Integer> modules = new ArrayList<>();

    private String modulesName;

    public static UserResponseVM from(User user, SubjectService subjectService) {
        UserResponseVM vm = modelMapper.map(user, UserResponseVM.class);
        if (Strings.isNotBlank(user.getModules())) {
            Map<Integer, String> modulesName = subjectService.allSubjectNames();
            vm.setModules(Arrays.stream(user.getModules().split(",")).map(Integer::valueOf).collect(Collectors.toList()));
            vm.setModulesName(vm.getModules().stream().map(modulesName::get).collect(Collectors.joining(",")));
        }
        vm.setBirthDay(DateTimeUtil.dateFormat(user.getBirthDay()));
        vm.setLastActiveTime(DateTimeUtil.dateFormat(user.getLastActiveTime()));
        vm.setCreateTime(DateTimeUtil.dateFormat(user.getCreateTime()));
        vm.setModifyTime(DateTimeUtil.dateFormat(user.getModifyTime()));
        return vm;
    }

}
