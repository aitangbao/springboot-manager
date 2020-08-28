package com.company.project.controller;

import com.company.project.common.utils.DataResult;
import com.company.project.entity.oshi.SystemHardwareInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息
 *
 * @author wangyihang
 * @version V1.0
 * @date 2020年8月26日
 */
@Api(tags = "系统信息")
@RestController
@RequestMapping("/sys")
public class SystemController {

    @GetMapping("/info")
    public DataResult systemInfo() {
        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();
        DataResult dataResult = DataResult.success(systemHardwareInfo);

        return dataResult ;
    }
}
