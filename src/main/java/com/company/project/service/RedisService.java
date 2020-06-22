package com.company.project.service;

import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public Long getExpire(String key) {
        if (null == key) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR.getCode(), "key or TomeUnit 不能为空");
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    public void set(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }


    public String get(String key) {
        String value = this.redisTemplate.opsForValue().get(key);
        return value;
    }

    public void del(String key) {
        if (this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    public void setAndExpire(String key, String value, long seconds) {
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, (long) seconds, TimeUnit.SECONDS);
    }


    public Set keys(String pattern) {
        return redisTemplate.keys("*" + pattern);
    }

    public void delKeys(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (!CollectionUtils.isEmpty(keys)) {
            this.redisTemplate.delete(keys);
        }
    }


    public long incrby(String key, long increment) {
        if (null == key) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR.getCode(), "key不能为空");
        }
        return redisTemplate.opsForValue().increment(key, increment);
    }


}
