package com.mindskip.xzs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.Subject;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.repository.SubjectMapper;
import com.mindskip.xzs.service.SubjectService;
import com.mindskip.xzs.utility.RedisUtils;
import com.mindskip.xzs.viewmodel.admin.education.SubjectPageRequestVM;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl extends BaseServiceImpl<Subject> implements SubjectService {
    private final static String REDIS_ALL_MODULES_KEY = "REDIS_ALL_MODULES_KEY";

    private final static String CACHE_NAME = "ecs:module";
    private final SubjectMapper subjectMapper;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    public SubjectServiceImpl(SubjectMapper subjectMapper) {
        super(subjectMapper);
        this.subjectMapper = subjectMapper;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result == null")
    public Subject selectById(Integer id) {
        return super.selectById(id);
    }

    @Override
    public int insertByFilter(Subject record) {
        redisUtils.delete(REDIS_ALL_MODULES_KEY);
        return super.insertByFilter(record);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.id")
    public int updateByIdFilter(Subject record) {
        redisUtils.delete(REDIS_ALL_MODULES_KEY);
        return super.updateByIdFilter(record);
    }


    @Override
    public List<Subject> getSubjectByUser(User user) {
        String modules = user.getModules();
        if (Strings.isNotBlank(modules)) {
            return subjectMapper.selectByIds(Arrays.stream(modules.split(",")).map(Integer::valueOf).collect(Collectors.toList()));
        }
        return Collections.emptyList();
    }

    @Override
    public List<Subject> allSubject() {
        return subjectMapper.allSubject();
    }

    @Override
    public PageInfo<Subject> page(SubjectPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                subjectMapper.page(requestVM)
        );
    }

    @Override
    public Map<Integer, String> allSubjectNames() {
        Map<Integer, String> map = (Map<Integer, String>) redisUtils.get(REDIS_ALL_MODULES_KEY);
        if (map == null) {
            map = this.allSubject().stream().filter(e -> !e.getDeleted()).collect(Collectors.toMap(Subject::getId, Subject::getName));
            redisUtils.set(REDIS_ALL_MODULES_KEY, map, 60 * 60);
        }
        return map;
    }

}
