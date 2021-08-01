package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.User;

public interface AuthenticationService {

    boolean authUser(User user, String username, String password);

    String pwdEncode(String password);

    String pwdDecode(String endodePwd);
}
