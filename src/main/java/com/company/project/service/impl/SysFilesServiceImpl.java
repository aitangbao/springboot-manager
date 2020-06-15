package com.company.project.service.impl;

import cn.hutool.core.io.FileUtil;
import com.company.project.common.config.FileUploadProperties;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SysFilesMapper;
import com.company.project.entity.SysFilesEntity;
import com.company.project.service.SysFilesService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@EnableConfigurationProperties(FileUploadProperties.class)
@Service("sysFilesService")
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFilesEntity> implements SysFilesService {

    private final FileUploadProperties fileUploadProperties;

    @Override
    public void saveFile(MultipartFile file) {
        //存储文件夹
        String createTime = DateUtils.format(new Date(), DateUtils.DATEPATTERN);
        String newPath = fileUploadProperties.getPath() + createTime + File.separator;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        try {
            String fileName = file.getOriginalFilename();
            //id与filename保持一直，删除文件
            String fileNameNew = UUID.randomUUID().toString().replace("-", "") + getFileType(fileName);
            String newFilePathName = newPath + fileNameNew;
            String url = fileUploadProperties.getUrl() + "/" + createTime + "/" + fileNameNew;
            //创建输出文件对象
            File outFile = new File(newFilePathName);
            //拷贝文件到输出文件对象
            FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
            //保存文件记录
            SysFilesEntity sysFilesEntity = new SysFilesEntity();
            sysFilesEntity.setCreateDate(new Date());
            sysFilesEntity.setFileName(fileName);
            sysFilesEntity.setFilePath(newFilePathName);
            sysFilesEntity.setUrl(url);
            this.save(sysFilesEntity);
        } catch (Exception e) {
            throw new BusinessException("上传文件失败");
        }
    }

    @Override
    public void removeByIdsAndFiles(List<String> ids) {
        List<SysFilesEntity> list = this.listByIds(ids);
        list.stream().forEach(entity -> {
            //如果之前的文件存在，删除
            if (FileUtil.exist(entity.getFilePath())) {
                FileUtil.del(entity.getFilePath());
            }
        });
        this.removeByIds(ids);

    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if (fileName != null && fileName.indexOf(".") >= 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}