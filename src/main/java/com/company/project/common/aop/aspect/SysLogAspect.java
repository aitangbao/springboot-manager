package com.company.project.common.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.company.project.common.aop.annotation.LogAnnotation;
import com.company.project.entity.SysLog;
import com.company.project.mapper.SysLogMapper;
import com.company.project.service.HttpSessionService;
import com.company.project.common.utils.HttpContextUtils;
import com.company.project.common.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {
    @Lazy
    @Resource
    private SysLogMapper sysLogMapper;

    @Lazy
    @Resource
    private HttpSessionService httpSessionService;
    /**
     * 此处的切点是注解的方式
     * 只要出现 @LogAnnotation注解都会进入
     */
    @Pointcut("@annotation(com.company.project.common.aop.annotation.LogAnnotation)")
    public void logPointCut(){

    }

    /**
     * 环绕增强,相当于MethodInterceptor
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        try {
            saveSysLog(point, time);
        } catch (Exception e) {
            log.error("sysLog,exception:{}", e, e);
        }

        return result;
    }
    /**
     * 把日志保存
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
         LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if(logAnnotation != null){
            //注解上的描述
            sysLog.setOperation(logAnnotation.title()+"-"+logAnnotation.action());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        log.info("请求{}.{}耗时{}毫秒",className,methodName,time);
        try {
            //请求的参数
            Object[] args = joinPoint.getArgs();
            String params=null;
            if(args.length!=0){
                params=JSON.toJSONString(args);
            }

            sysLog.setParams(params);
        } catch (Exception e) {
            log.error("sysLog,exception:{}", e, e);
        }
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        log.info("Ip{}，接口地址{}，请求方式{}，入参：{}",sysLog.getIp(),request.getRequestURL(),request.getMethod(),sysLog.getParams());
        //用户名
        String userId= httpSessionService.getCurrentUserId();
        String username=httpSessionService.getCurrentUsername();
        sysLog.setUsername(username);
        sysLog.setUserId(userId);
        sysLog.setTime((int) time);
        log.info(sysLog.toString());
        sysLogMapper.insert(sysLog);

    }
}
