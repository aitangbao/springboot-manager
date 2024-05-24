package com.company.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.DateUtils;
import com.company.project.entity.SysFilesEntity;
import com.company.project.mapper.SysFilesMapper;
import com.company.project.service.SysFilesService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 文件上传 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service("sysFilesService")
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFilesEntity> implements SysFilesService {
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${file.path}")
    private String filePath;

    @Override
    public DataResult saveFile(MultipartFile file, HttpServletRequest request) {
        //存储文件夹
        String createTime = DateUtils.format(new Date(), DateUtils.DATEPATTERN);
        String newPath = filePath + File.separator + createTime + File.separator;
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
            String url = this.getRootDir(request) + ("/" + contextPath + "/files/" + createTime + "/" + fileNameNew).replaceAll("/+", "/");
            //创建输出文件对象
            File outFile = new File(newFilePathName);
            //拷贝文件到输出文件对象
            FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
            //保存文件记录
            SysFilesEntity sysFilesEntity = new SysFilesEntity();
            sysFilesEntity.setFileName(fileName);
            sysFilesEntity.setFilePath(newFilePathName);
            sysFilesEntity.setUrl(url);
            this.save(sysFilesEntity);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("src", url);
            return DataResult.success(resultMap);
        } catch (Exception e) {
            throw new BusinessException("上传文件失败");
        }
    }

    @Override
    public void removeByIdsAndFiles(List<String> ids) {
        List<SysFilesEntity> list = this.listByIds(ids);
        list.forEach(entity -> {
            //如果之前的文件存在，删除
            File file = new File(entity.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        });
        this.removeByIds(ids);

    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    private String getFileType(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    /**
     * 获取跟路径
     * @param request
     * @return
     */
    private String getRootDir(HttpServletRequest request) {
        // 获取协议 (http 或 https)
        String scheme = request.getScheme();

        // 获取域名
        String serverName = request.getServerName();

        // 获取端口号
        int serverPort = request.getServerPort();

        // 构建根路径
        StringBuilder rootURL = new StringBuilder();
        rootURL.append(scheme).append("://").append(serverName);

        // 仅当端口不是默认端口时，才包括端口号
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            rootURL.append(":").append(serverPort);
        }

        return rootURL.toString();
    }
}