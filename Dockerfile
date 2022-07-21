# 基础镜像
FROM openjdk:8-jre
# author
MAINTAINER wenbin
# 复制jar文件到路径
ADD manager.jar manager.jar
# 时间
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
# 启动服务
ENTRYPOINT ["java","-jar","/manager.jar"]
# 暴露端口
EXPOSE 8080

