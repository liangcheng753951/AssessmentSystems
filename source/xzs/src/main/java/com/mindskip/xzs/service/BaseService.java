package com.mindskip.xzs.service;

/**
 * @param <T>
 */
public interface BaseService<T> {
    int deleteById(Integer id);

    int insert(T record);

    int insertByFilter(T record);

    T selectById(Integer id);

    int updateByIdFilter(T record);

    int updateById(T record);
}
