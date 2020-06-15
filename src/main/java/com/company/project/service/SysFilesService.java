package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysFilesEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传
 *
 * @author manager
 * @email *****@mail.com
 * @date 2020-06-09 18:02:50
 */
public interface SysFilesService extends IService<SysFilesEntity> {

    void saveFile(MultipartFile file);

    void removeByIdsAndFiles(List<String> ids);
}

