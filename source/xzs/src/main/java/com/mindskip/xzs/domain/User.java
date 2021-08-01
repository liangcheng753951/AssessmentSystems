package com.mindskip.xzs.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -7797183521247423117L;

    private Integer id;

    private String userUuid;

    private String userName;

    private String password;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String modules;

    private Integer role;

    private Integer status;

    private String imagePath;

    private Date createTime;

    private Date modifyTime;

    private Date lastActiveTime;

    private Boolean deleted;
}
