package com.company.project.service;

import com.alibaba.fastjson.JSONObject;
import com.company.project.exception.BusinessException;
import com.company.project.exception.code.BaseResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author : aitangbao
 */

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public Long getExpire(String key, TimeUnit unit) {
        if(null==key||null==unit){
            throw new BusinessException(BaseResponseCode.DATA_ERROR.getCode(),"key or TomeUnit 不能为空");
        }
        return redisTemplate.getExpire(key, unit);
    }

    public void set(String key,Object value,long time,TimeUnit unit){

        if(null==key||null==value||null==unit){
            return;
        }
        if(time<0){
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
        }else {
            redisTemplate.opsForValue().set(key,JSONObject.toJSONString(value),time,unit);
        }

    }

    public void set(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public Boolean setifAbsen(String key,Object value,long time,TimeUnit unit){

        if(null==key||null==value||null==unit){
            throw new BusinessException(BaseResponseCode.DATA_ERROR.getCode(),"kkey、value、unit都不能为空");
        }
        return redisTemplate.opsForValue().setIfAbsent(key,JSONObject.toJSONString(value),time,unit);
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

    public void setAndExpire(String key, String value, int seconds) {
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, (long) seconds, TimeUnit.SECONDS);
    }


    public void setExpire(String key, Date endTime) {
        long seconds = endTime.getTime() - (new Date()).getTime();
        this.redisTemplate.expire(key, (long) ((int) (seconds / 1000L)), TimeUnit.SECONDS);
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



    public long incrby(String key,long increment){
        if(null==key){
            throw new BusinessException(BaseResponseCode.DATA_ERROR.getCode(),"key不能为空");
        }
        return redisTemplate.opsForValue().increment(key,increment);
    }
}
