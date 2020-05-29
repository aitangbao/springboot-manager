/*CoreUtil*/
/*工具类，类似java静态工具类*/
var CoreUtil = (function () {
    var coreUtil = {};

    /*POST*/
    coreUtil.sendPost = function(url, params, ft){
        this.sendAJAX(url, params, ft, "POST")
    }
    /*PUT*/
    coreUtil.sendPut = function(url, params, ft){
        this.sendAJAX(url, params, ft, "PUT")
    }
    /*DELETE*/
    coreUtil.sendDelete = function(url, params, ft){
        this.sendAJAX(url, params, ft, "DELETE")
    }


    /*ajax*/
    coreUtil.sendAJAX = function(url, params, ft, method){
        var loadIndex = top.layer.load(0, {shade: false});
        $.ajax({
            url: url,
            cache: false,
            async: true,
            data: JSON.stringify(params),
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
                        top.window.location.href="/index/login"
                    })
                }else if(res.code==401008){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                } else {
                    layer.msg(res.msg);
                }
            }
        })
    }

    /*get*/
    coreUtil.sendGet = function(url, params, ft, method){
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
                        top.window.location.href="/index/login"
                    })
                }else if(res.code==401008){ //凭证过期重新登录
                    layer.msg("抱歉！您暂无权限", {time:2000})
                } else {
                    layer.msg(res.msg);
                }
            }
        })
    }

    /*表单数据封装成 json String*/
    coreUtil.formJson = function (frm) {  //frm：form表单的id
        var o = {};
        var a = $("#"+frm).serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [ o[this.name] ];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON.stringify(o);
    };
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


    coreUtil.formattime=function (val) {
        var date=new Date(val);
        var year=date.getFullYear();
        var month=date.getMonth()+1;
        month=month>9?month:('0'+month);
        var day=date.getDate();
        day=day>9?day:('0'+day);
        var hh=date.getHours();
        hh=hh>9?hh:('0'+hh);
        var mm=date.getMinutes();
        mm=mm>9?mm:('0'+mm);
        var ss=date.getSeconds();
        ss=ss>9?ss:('0'+ss);
        var time=year+'-'+month+'-'+day+' '+hh+':'+mm+':'+ss;
        return time;
    };

    //判断字符是否为空的方法
    coreUtil.isEmpty = function(obj){
        if(typeof obj == "undefined" || obj == null || obj == ""){
            return true;
        }else{
            return false;
        }
    }
    return coreUtil;
})(CoreUtil, window);
