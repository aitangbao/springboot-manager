package com.company.project.utils;

import com.company.project.exception.code.BaseResponseCode;
import com.company.project.exception.code.ResponseCodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataResult <T>{

    /**
     * 请求响应code，0为成功 其他为失败
     */
    @ApiModelProperty(value = "请求响应code，0为成功 其他为失败", name = "code")
    private int code;

    /**
     * 响应异常码详细信息
     */
    @ApiModelProperty(value = "响应异常码详细信息", name = "msg")
    private String msg;

    /**
     * 响应内容 ， code 0 时为 返回 数据
     */
    @ApiModelProperty(value = "需要返回的数据", name = "data")
    private T data;

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.msg=null;
    }

    public DataResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }


    public DataResult() {
        this.code=BaseResponseCode.SUCCESS.getCode();
        this.msg=BaseResponseCode.SUCCESS.getMsg();
        this.data=null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code=BaseResponseCode.SUCCESS.getCode();
        this.msg=BaseResponseCode.SUCCESS.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }
    /**
     * 操作成功 data为null
     * @throws
     */
    public static <T>DataResult success(){
        return new <T>DataResult();
    }
    /**
     * 操作成功 data 不为null
     * @throws
     */
    public static <T>DataResult success(T data){
        return new <T>DataResult(data);
    }

    /**
     * 操作失败 data 不为null
     * @throws
     */
    public static <T>DataResult fail(String msg){
        return new <T>DataResult(BaseResponseCode.OPERATION_ERRO.getCode(),msg);
    }

    /**
     * 自定义 返回操作 data 可控
     * @throws
     */
    public static <T>DataResult getResult(int code,String msg,T data){
        return new <T>DataResult(code,msg,data);
    }
    /**
     *  自定义返回  data为null
     * @throws
     */
    public static <T>DataResult getResult(int code,String msg){
        return new <T>DataResult(code,msg);
    }
    /**
     * 自定义返回 入参一般是异常code枚举 data为空
     * @throws
     */
    public static <T>DataResult getResult(BaseResponseCode responseCode){
        return new <T>DataResult(responseCode);
    }
    /**
     * 自定义返回 入参一般是异常code枚举 data 可控
     * @throws
     */
    public static <T>DataResult getResult(BaseResponseCode responseCode,T data){

        return new <T>DataResult(responseCode,data);
    }

}
