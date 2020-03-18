package com.company.project.controller;

import com.company.project.service.HomeService;
import com.company.project.service.HttpSessionService;
import com.company.project.utils.DataResult;
import com.company.project.vo.resp.HomeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys")
@Api(tags = "首页数据")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @Autowired
    private HttpSessionService httpSessionService;

    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public DataResult<HomeRespVO> getHomeInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("authorization");
        /**
         * 通过access_token拿userId
         */
        String userId = httpSessionService.getCurrentUserId();
        DataResult<HomeRespVO> result = DataResult.success();
        result.setData(homeService.getHomeInfo(userId));
        return result;
    }
}
