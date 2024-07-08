package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.SysFilesEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件上传 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface SysFilesService extends IService<SysFilesEntity> {

    /**
     * 保存图片返回url
     *
     * @param file
     * @param request
     * @return
     */
    String saveFile(MultipartFile file, HttpServletRequest request);

    /**
     * 删除图片
     *
     * @param ids
     */
    void removeByIdsAndFiles(List<String> ids);
}

