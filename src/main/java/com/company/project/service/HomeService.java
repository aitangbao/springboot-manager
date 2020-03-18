package com.company.project.service;

import com.company.project.vo.resp.HomeRespVO;

public interface HomeService {

    HomeRespVO getHomeInfo(String userId);
}
