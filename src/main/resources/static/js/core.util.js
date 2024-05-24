/*工具类*/
var CoreUtil = (function () {
    var coreUtil = {};

    /*GET*/
    coreUtil.sendGet = function(url, params, ft){
        this.sendAJAX(url, params, ft, "GET")
    }

    /*POST*/
    coreUtil.sendPost = function(url, params, ft){
        this.sendAJAX(url, JSON.stringify(params), ft, "POST")
    }
    /*PUT*/
    coreUtil.sendPut = function(url, params, ft){
        this.sendAJAX(url, JSON.stringify(params), ft, "PUT")
    }
    /*DELETE*/
    coreUtil.sendDelete = function(url, params, ft){
        this.sendAJAX(url, JSON.stringify(params), ft, "DELETE")
    }


    /*ajax*/
    coreUtil.sendAJAX = function(url, params, ft, method){
        var loadIndex = top.layer.load(0, {shade: false});
        $.ajax({
            url: url,
            cache: false,
            async: true,
            data: params,
            type: method,
            contentType: 'application/json; charset=UTF-8',
            dataType: "json",
            beforeSend: function(request) {
                request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
            },
            success: function (res) {
                top.layer.close(loadIndex);
                if (res.code==0){
                    if(ft!=null&&ft!=undefined){
                        ft(res);
                    }
                }else if(res.code==401001){ //凭证过期重新登录
                    layer.msg("凭证过期请重新登录", {time:2000}, function () {
                        top.window.location.href=ctx + "index/login"
                    })
                }else if(res.code==401008){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                } else {
                    layer.msg(res.msg);
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                top.layer.close(loadIndex);
                if(XMLHttpRequest.status==404){
                    top.window.location.href= ctx + "index/404";
                }else{
                    layer.msg("服务器好像除了点问题！请稍后试试");
                }
            }
        })
    }


    /*存入本地缓存*/
    coreUtil.setData = function(key, value){
        layui.data('LocalData',{
            key :key,
            value: value
        })
    };
    /*从本地缓存拿数据*/
    coreUtil.getData = function(key){
        var localData = layui.data('LocalData');
        return localData[key];
    };

    //判断字符是否为空的方法
    coreUtil.isEmpty = function(obj){
        if(typeof obj == "undefined" || obj == null || obj == ""){
            return true;
        }else{
            return false;
        }
    }

    //字典数据回显
    coreUtil.selectDictLabel = function (datas, value) {
        datas = JSON.parse(datas);
        var label = "";
        $.each(datas, function(index, dict) {
            if (dict.value == ('' + value)) {
                label = dict.label;
                return false;
            }
        });
        //匹配不到，返回未知
        if (CoreUtil.isEmpty(label)) {
            return "未知";
        }
        return label;
    }

    //生成随机8位数
    coreUtil.showImg = function (src) {
        if (!CoreUtil.isEmpty(src)) {
            var json = {
                "title": "", //相册标题
                "id": "1", //相册id
                "start": 0, //初始显示的图片序号，默认0
                "data": [   //相册包含的图片，数组格式
                    {
                        "alt": "",
                        "pid": "1", //图片id
                        "src": src, //原图地址
                        "thumb": "" //缩略图地址
                    }
                ]
            };
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        }
    }

    //获取复选框的值
    coreUtil.getCheckboxValues = function (type, data) {
        if (this.isEmpty(data)) {
            return "";
        }
        var listRtn = [];
        var keys = getObjectKeys(data);
        for (var i = 0; i < keys.length; i++) {
            if (keys[i].indexOf("[") > -1 && keys[i].indexOf("]") > -1 && keys[i].indexOf(type) > -1) {
                listRtn.push(keys[i].substring(keys[i].indexOf("[") + 1, keys[i].indexOf("]")))
            }
        }
        return listRtn.toString();
    }

    //赋值复选框的值 返回json， 可以直接form.val进行赋值
    coreUtil.setCheckboxValues = function (type, values) {
        var json = {};
        if (this.isEmpty(values)) {
            return json;
        }
        values = values.split(",");
        for (var i = 0; i < values.length; i++) {
            var key = type + "["+values[i] + "]";
            json[key] = true;
        }
        return json;
    }

    return coreUtil;
})(CoreUtil, window);

//写成标准的方法(数组是object的一种)：
function getObjectKeys(object) {
    var keys = [];
    for (var property in object)
        keys.push(property);
    return keys;
}

function getObjectValues(object) {
    var values = [];
    for (var property in object)
        values.push(object[property]);
    return values;
}