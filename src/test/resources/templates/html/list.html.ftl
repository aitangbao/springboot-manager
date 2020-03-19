<!DOCTYPE html>
<html lang="en" xmlns:shiro="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <link rel="stylesheet" href="/css/custom.form.css">
</head>
<body>


<div class="panel panel-default operation" hidden>
    <div class="panel-heading title"></div>
    <div class="layui-card-body">
        <form class="layui-form " action="" lay-filter="info" style="width: 700px;margin-top: 10px">


            <#list table.fields as field>
                <#if field.keyFlag><#--生成主键排在第一位-->
                    <input name="${field.propertyName}" hidden/>
                </#if>
                <#if !field.keyFlag><#--生成主键排在第一位-->
                    <div class="layui-form-item">
                        <label class="layui-form-label">${field.comment}</label>
                        <div class="layui-input-block">
                            <input type="${field.propertyName}" name="${field.propertyName}" placeholder="请输入${field.comment}" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </#if>



            </#list>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="submit" class="layui-btn" lay-submit="" lay-filter="submit">保存</button>
                    <button  class="layui-btn layui-btn-primary" id="btn_cancel">返回</button>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="table_div">
    <table class="layui-table" id="showTable" lay-filter="showTable" ></table>
    <div id="laypage" class=" $(".layui-laypage-btn").click();"></div>

</div>
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"  shiro:hasPermission="${entity?uncap_first}:add">添加</button>
        <button class="layui-btn layui-btn-sm" lay-event="batchDeleted" shiro:hasPermission="${entity?uncap_first}:deleted">删除</button>
    </div>
</script>
<script type="text/html" id="tool">
    <a class="layui-btn layui-btn-xs" lay-event="edit" shiro:hasPermission="${entity?uncap_first}:update">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" shiro:hasPermission="${entity?uncap_first}:deleted">删除</a>
</script>

</body>
</html>
<script src="/layui/layui.all.js"></script>
<script src="/js/core.util.js"></script>
<script>
    var layer = layui.layer;
    var table = layui.table;
    var laypage = layui.laypage;
    var $ = jQuery = layui.jquery;
    var form = layui.form;

    var searchParam= {
        pageNum:1,
        pageSize:10
    }
    CoreUtil.sendAjax("/${entity?uncap_first}/listByPage",JSON.stringify(searchParam),function (res) {
        laypageTable(res.data.total,searchParam.pageNum);
        if(res.data.records !=null){
            loadTable(res.data.records);
        }
    },"POST",false,function (res) {
        layer.msg("抱歉！您暂无获取用户列表的权限");
        var noAuthorityData=[];
        loadTable(noAuthorityData);
    });
    var laypageTable = function(count,currentPage) {
        laypage.render({
            elem: 'laypage'
            , count: count
            ,limit:searchParam.pageSize
            , layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            ,curr: location.hash.replace('#!currentPage=', '') //获取起始页
            ,hash: 'currentPage' //自定义hash值
            , jump: function (obj,first) {
                if (!first){
                    searchParam.pageNum=obj.curr;
                    searchParam.pageSize=obj.limit;
                    CoreUtil.sendAjax("/${entity?uncap_first}/listByPage",JSON.stringify(searchParam),function (res) {
                        if(res.data.records !=null){
                            loadTable(res.data.records);
                            laypageTable(res.data.total,searchParam.pageNum);
                        }

                    },"POST",false,function (res) {
                        layer.alert("抱歉！您暂无获取用户列表的权限");
                        var noAuthorityData=[];
                        loadTable(noAuthorityData);
                    });
                }
            }
        });
    };
    //渲染table
    var loadTable=function (data) {
        table.render({
            elem: '#showTable'
            ,cols: [
                [
                    {type: 'checkbox', fixed: 'left'},
                    <#list table.fields as field>
                    {field: '${field.propertyName}', title: '${field.comment}', width: 300, sort: true},
                    </#list>
                    {width:300,toolbar:"#tool",title:'操作'}
                ]
            ]
            ,data: data
            ,even: true
            ,limit: data.length
            ,limits: [10, 20, 30, 40, 50]
            ,toolbar:'#toolbar'

        });
    };

    //表头工具
    table.on('toolbar(showTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'batchDeleted':
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if(data.length==0){
                    layer.msg("请选择要批量删除的列");
                }else {
                    var ids = [];
                    $(data).each(function (index,item) {
                        ids.push(item.id);
                    });
                    tipDialog(ids);
                }
                break;
            case 'add':

                selectNode=null;
                $(".table_div").hide();
                $(".operation").show();
                $(".title").html("新增");
                <#list table.fields as field>
                    $(".operation input[name=${field.propertyName}]").val("");
                </#list>
                form.render(); //更新全部
                break;
        };
    });
    //列操作
    table.on('tool(showTable)',function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'del':
                var ids=[];
                ids.push(data.id);
                tipDialog(ids);
                break;
            case 'edit':
                $(".table_div").hide();
                $(".operation").show();
                $(".title").html("编辑");
                <#list table.fields as field>
                    $(".operation input[name=${field.propertyName}]").val(data.${field.propertyName});
                </#list>
                form.render(); //更新全部
                break;
        }
    });

    //删除
    var tipDialog=function (ids) {
        layer.open({
            content: "确定要删除么?",
            yes: function(index, layero){
                layer.close(index); //如果设定了yes回调，需进行手工关闭
                CoreUtil.sendAjax("/${entity?uncap_first}/delete",JSON.stringify(ids),function (res) {
                    layer.msg(res.msg, {time:1000},function () {
                        $(".layui-laypage-btn").click();
                    });
                },"DELETE",false,function (res) {
                    layer.msg("抱歉！您暂无删除用户的权限");
                });
            }
        });
    };

    //返回
    $("#btn_cancel").click(function() {
        $(".table_div").show();
        $(".operation").hide();
        return false;
    });

    //监听保存
    form.on('submit(submit)', function(data){
        if(data.field.id===undefined || data.field.id===null || data.field.id===""){
            CoreUtil.sendAjax("/${entity?uncap_first}/add",JSON.stringify(data.field),function (res) {
                $(".table_div").show();
                $(".operation").hide();
                $(".layui-laypage-btn").click();

            },"POST",false,function (res) {
                layer.msg("抱歉！您暂无权限");
            });
        }else {
            CoreUtil.sendAjax("/${entity?uncap_first}/update",JSON.stringify(data.field),function (res) {
                $(".table_div").show();
                $(".operation").hide();
                $(".layui-laypage-btn").click();

            },"PUT",false,function (res) {
                layer.msg("抱歉！您暂无权限");
            });
        }

        return false;
    });
</script>