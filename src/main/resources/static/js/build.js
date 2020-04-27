layui.use(['element', 'laydate', 'form'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var laydate = layui.laydate;

    var build = $(".build-panel");

    //初始化laydate实例
    laydate.render({
        elem: '#laydate'
    });

    // 禁止选择网页文本
    $(document).on('selectstart', '.layui-card', function(){
        return false;
    });

    // 移动操作
    var downPosi, target = null, dragDom = null, timer = null;
    var areaPosi;
    $(document).on("mousedown", ".layui-form-item", function (e) {
        $this = $(this);
        timer = setTimeout(function () {
            downPosi = {'left': e.clientX - $this.offset().left,
                'top': e.clientY - $this.offset().top + 10};
            areaPosi = {'ltX': build.offset().left, 'ltY': build.offset().top,
                'rbX': build.offset().left + build.width(),
                'rbY': build.offset().top + build.height()};
            target = $this;
        }, 200);
    });
    $(document).on("mousemove", function (e) {
        if(dragDom == null && target != null){
            dragDom = $("<div class='layui-form timo-compile element-panel'></div>");
            dragDom.append(target.clone());
            dragDom.addClass("drag-box");
            dragDom.css("width", target.width());
            $("body").append(dragDom);
            var buildItem = target.parent('.build-item');
            if(buildItem.length > 0){
                buildItem.remove();
            }
        }
        if(dragDom != null){
            dragDom.css("left", e.clientX - downPosi.left);
            dragDom.css("top", e.clientY - downPosi.top);
        }
        if(dragDom != null){
            if(e.clientX > areaPosi.ltX && e.clientY > areaPosi.ltY
                && e.clientX < areaPosi.rbX && e.clientY < areaPosi.rbY){
                build.addClass("active");
                buildItemPosi(e.clientY);
            }else{
                build.removeClass("active");
                shellItem.remove();
            }
        }
    });
    $(document).on("mouseup", function (e) {
        if(dragDom != null){
            if(build.hasClass("active")){
                buildAdd(dragDom);
            }
            dragDom.remove();
            dragDom = null;
            target = null;
            build.removeClass("active");
            shellItem.remove();
        }
        if(timer != null){
            clearTimeout(timer);
            timer = null;
        }
    });

    // 构建项位置
    var shellItem = $("<div id='shell-item'></div>");
    var buildItemPosi = function(clientY){
        build.children('.build-item').each(function(key, val){
            var $this = $(val);
            var ty = $this.offset().top;
            var by = $this.offset().top + $this.height();
            if(clientY > ty &&  clientY < by ){
                if(clientY < (ty + by) / 2){
                    $this.before(shellItem);
                }else{
                    $this.after(shellItem);
                }
            }
        });
        shellItem.css('height', dragDom.height());
        if($('#shell-item').length == 0){
            build.append(shellItem);
        }
    };

    // 加入构建面板
    var buildAdd = function(dragDom){
        var elem = dragDom.children(".layui-form-item").clone();
        elem.children('div').children('div').remove();
        elem.removeAttr('style');
        elem.removeClass('drag-box');
        var item = $("<div class='build-item'>" +
            "<div class='control'><a class='edit'>编辑HTML</a> | " +
            "<a class='remove'>删除</a></div>" +
            "</div>");
        shellItem.after(item.append(elem));

        form.render();
    };

    // 编辑构建项
    var buildItem, index;
    build.on('click', '.edit', function(){
        buildItem = $(this).parents('.build-item');
        var elem = buildItem.children('.layui-form-item').clone();
        elem.children('div').children('div').remove();
        elem.find('.layui-upload-file').remove();

        var box = $("<div class='build-edit-box'></div>");
        var edit = $("<textarea class='build-edit'></textarea>").text(elem.prop('outerHTML'));
        box.append(edit).append("<button class='build-edit-btn'>更新</button></div>");

        index = layer.open({
            title: '编辑HTML',
            type: 1,
            skin: 'build-item-edit', //样式类名
            shadeClose: true, //开启遮罩关闭
            area: ['500px', '360px'],
            content: box.prop('outerHTML')
        });
    });

    // 更新HTML
    $(document).on('click', '.build-edit-btn', function(){
        var val = $(this).parent().children('textarea').val();
        buildItem.children('.layui-form-item').remove();
        buildItem.append($(val));
        form.render();
        layer.close(index);
    });

    // 删除构建项
    build.on('click', '.remove', function(){
        $(this).parents('.build-item').remove();
    });

    // 生成代码
    $(document).on('click', '.build-generate', function(){
        var genHtml = '';
        build.find(".layui-form-item").each(function (key, val) {
            var item = $(val).clone();
            item.children('div').children('div').remove();
            item.find('.layui-upload-file').remove();
            genHtml += item.prop('outerHTML') + "\n";
        });
        var box = $("<div class='build-edit-box'></div>");
        var edit = $("<textarea class='build-edit'></textarea>").text(genHtml);
        box.append(edit);

        index = layer.open({
            title: '复制HTML代码',
            type: 1,
            skin: 'build-item-edit', //样式类名
            shadeClose: true, //开启遮罩关闭
            area: ['500px', '360px'],
            content: box.prop('outerHTML')
        });
        $('.build-edit').focus().select();
    });

});