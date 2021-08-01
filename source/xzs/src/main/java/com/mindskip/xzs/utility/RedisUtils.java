package com.mindskip.xzs.utility;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 **/
@Component
public class RedisUtils {
    private static RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplateTmp;

    /**
     * 查询key,支持模糊查询
     *
     * @param key 传过来时key的前后端已经加入了*，或者根据具体处理
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 重命名key
     */
    public void renameKey(String key, String newKey) {
        redisTemplate.rename(key, newKey);
    }

    /**
     * 字符串添加信息
     *
     * @param key
     * @param obj 可以是单个的值，也可以是任意类型的对象
     */
    public void set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }

    /**
     * 字符串添加信息
     *
     * @param key
     * @param obj    可以是单个的值，也可以是任意类型的对象
     * @param expire 设置失效时间
     */
    public void set(String key, Object obj, long expire) {
        redisTemplate.opsForValue().set(key, obj, expire, TimeUnit.SECONDS);
    }

    /**
     * 字符串获取值
     *
     * @param key
     */
    public Object get(String key) {
        if (key == null) {
            return key;
        }
        return redisTemplate.opsForValue().get(key);
    }

//    /**
//     * @param key
//     * @param value
//     * @param exptime
//     * @return absent true，exists false，
//     * ps：设置成功（值不存在）返回true，设置失败（值已存在）返回false
//     */
//    public Boolean setIfAbsent(String key, Object value, long exptime) {
//        Boolean b = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
//            RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
//            RedisSerializer keySerializer = redisTemplate.getKeySerializer();
//            Object obj = connection.execute("set", keySerializer.serialize(key),
//                    valueSerializer.serialize(value),
//                    SafeEncoder.encode("NX"),
//                    SafeEncoder.encode("EX"),
//                    Protocol.toByteArray(exptime));
//            return obj != null;
//        });
//        return b;
//    }


    /**
     * 删出key
     * 这里跟下边deleteKey（）最底层实现都是一样的，应该可以通用
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 添加单个
     *
     * @param key    key
     * @param filed  filed
     * @param domain 对象
     */
    public void hset(String key, String filed, Object domain) {
        redisTemplate.opsForHash().put(key, filed, domain);
    }

    /**
     * 添加HashMap
     *
     * @param key key
     * @param hm  要存入的hash表
     */
    public void hset(String key, HashMap<String, Object> hm) {
        redisTemplate.opsForHash().putAll(key, hm);
    }

    /**
     * 查询key和field所确定的值
     *
     * @param key   查询的key
     * @param field 查询的field
     * @return HV
     */
    public Object hget(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 查询该key下所有值
     *
     * @param key 查询的key
     * @return Map<HK, HV>
     */
    public Object hget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Object getAndSet(String key, Object newValue) {
        return redisTemplate.opsForValue().getAndSet(key, newValue);
    }

    /**
     * 删除key下所有值
     *
     * @param key 查询的key
     */
    public void deleteKey(String key) {
        redisTemplate.opsForHash().getOperations().delete(key);
    }

    /**
     * 判断key和field下是否有值
     *
     * @param key   判断的key
     * @param field 判断的field
     */
    public Boolean hasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 判断key下是否有值
     *
     * @param key 判断的key
     */
    public Boolean hasKey(String key) {
        return redisTemplate.opsForHash().getOperations().hasKey(key);
    }

    public Boolean expire(String key, long second) {
        return redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    @PostConstruct
    public void init() {
        RedisUtils.redisTemplate = redisTemplateTmp;
    }
}
