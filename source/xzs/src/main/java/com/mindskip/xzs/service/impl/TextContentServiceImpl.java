package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.TextContent;
import com.mindskip.xzs.repository.TextContentMapper;
import com.mindskip.xzs.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TextContentServiceImpl extends BaseServiceImpl<TextContent> implements TextContentService {

    private final static String CACHE_NAME = "ecs:textcontent";

    @Autowired
    public TextContentServiceImpl(TextContentMapper textContentMapper) {
        super(textContentMapper);
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result == null")
    public TextContent selectById(Integer id) {
        return super.selectById(id);
    }

    @Override
    public int insertByFilter(TextContent record) {
        return super.insertByFilter(record);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.id")
    public int updateByIdFilter(TextContent record) {
        return super.updateByIdFilter(record);
    }

}
