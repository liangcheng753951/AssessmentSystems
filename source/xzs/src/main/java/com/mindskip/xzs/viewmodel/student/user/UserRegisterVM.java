package com.mindskip.xzs.viewmodel.student.user;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterVM {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordConfirm;


}
