package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.Subject;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.viewmodel.admin.education.SubjectPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SubjectService extends BaseService<Subject> {

    List<Subject> getSubjectByUser(User user);

    List<Subject> allSubject();

    PageInfo<Subject> page(SubjectPageRequestVM requestVM);

    Map<Integer, String> allSubjectNames();
}
