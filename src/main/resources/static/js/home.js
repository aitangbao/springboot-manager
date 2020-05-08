//全屏
$('body').on('click', '[data-check-screen]', function () {
    var check = $(this).attr('data-check-screen');
    if (check == 'full') {
        fullScreen();
        $(this).attr('data-check-screen', 'exit');
        $(this).html('<i class="layui-icon layui-icon-screen-restore"></i>');
    } else {
        exitFullScreen();
        $(this).attr('data-check-screen', 'full');
        $(this).html('<i class="layui-icon layui-icon-screen-full"></i>');
    }
});

//刷新
$('body').on('click', '[data-refresh]', function () {
    $(".layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload();
});

/**
 * 初始化背景色
 */
var initBgColor = function () {
    var bgcolorId = sessionStorage.getItem('layuiminiBgcolorId');
    if (bgcolorId == null || bgcolorId == undefined || bgcolorId == '') {
        bgcolorId = config('BgColorDefault');
    }
    var bgcolorData = bgColorConfigFun(bgcolorId);
    console.log(bgcolorData)
    var styleHtml = '.layui-layout-admin .layui-header{background-color:' + bgcolorData.headerRight + '!important;}\n' +
        '.layui-header>ul>.layui-nav-item.layui-this,.layuimini-tool i:hover{background-color:' + bgcolorData.headerRightThis + '!important;}\n' +
        '.layui-layout-admin .layui-logo {background-color:' + bgcolorData.headerLogo + '!important;}\n' +
        '.layui-side.layui-bg-black,.layui-side.layui-bg-black>.layui-left-menu>ul {background-color:' + bgcolorData.menuLeft + '!important;}\n' +
        '.layui-left-menu .layui-nav .layui-nav-child a:hover:not(.layui-this) {background-color:' + bgcolorData.menuLeftHover + ';}\n' +
        '.layui-layout-admin .layui-nav-tree .layui-this, .layui-layout-admin .layui-nav-tree .layui-this>a, .layui-layout-admin .layui-nav-tree .layui-nav-child dd.layui-this, .layui-layout-admin .layui-nav-tree .layui-nav-child dd.layui-this a {\n' +
        '    background-color: ' + bgcolorData.menuLeftThis + ' !important;\n' +
        '}';
    $('#layuimini-bg-color').html(styleHtml);
};

/**
 * 弹出配色方案
 */
$('body').on('click', '[data-bgcolor]', function () {
    var loading = layer.load(0, {shade: false, time: 2 * 1000});
    var clientHeight = (document.documentElement.clientHeight) - 95;
    var bgColorHtml = buildBgColorHtml();
    var html = '<div class="layuimini-color">\n' +
        '<div class="color-title">\n' +
        '<span>配色方案</span>\n' +
        '</div>\n' +
        '<div class="color-content">\n' +
        '<ul>\n' + bgColorHtml + '</ul>\n' +
        '</div>\n' +
        '</div>';
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        shade: 0.2,
        anim: 2,
        shadeClose: true,
        id: 'layuiminiBgColor',
        area: ['340px', clientHeight + 'px'],
        offset: 'rb',
        content: html,
        end: function () {
            $('.layuimini-select-bgcolor').removeClass('layui-this');
        }
    });
    layer.close(loading);
});
/**
 * 选择配色方案
 */
$('body').on('click', '[data-select-bgcolor]', function () {
    var bgcolorId = $(this).attr('data-select-bgcolor');
    $('.layuimini-color .color-content ul .layui-this').attr('class', '');
    $(this).attr('class', 'layui-this');
    sessionStorage.setItem('layuiminiBgcolorId', bgcolorId);
    initBgColor();
});

/**
 * 进入全屏
 */
var fullScreen = function () {
    var el = document.documentElement;
    var rfs = el.requestFullScreen || el.webkitRequestFullScreen;
    if (typeof rfs != "undefined" && rfs) {
        rfs.call(el);
    } else if (typeof window.ActiveXObject != "undefined") {
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript != null) {
            wscript.SendKeys("{F11}");
        }
    } else if (el.msRequestFullscreen) {
        el.msRequestFullscreen();
    } else if (el.oRequestFullscreen) {
        el.oRequestFullscreen();
    } else {
        top.layer.msg('浏览器不支持全屏调用！');
    }
};

/**
 * 退出全屏
 */
var exitFullScreen = function () {
    var el = document;
    var cfs = el.cancelFullScreen || el.webkitCancelFullScreen || el.exitFullScreen;
    if (typeof cfs != "undefined" && cfs) {
        cfs.call(el);
    } else if (typeof window.ActiveXObject != "undefined") {
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript != null) {
            wscript.SendKeys("{F11}");
        }
    } else if (el.msExitFullscreen) {
        el.msExitFullscreen();
    } else if (el.oRequestFullscreen) {
        el.oCancelFullScreen();
    } else {
        top.layer.msg('浏览器不支持全屏调用！');
    }
};

/**
 * 构建背景颜色选择
 * @returns {string}
 */
var buildBgColorHtml = function () {
    var html = '';
    var bgcolorId = sessionStorage.getItem('layuiminiBgcolorId');
    if (bgcolorId == null || bgcolorId == undefined || bgcolorId == '') {
        bgcolorId = 0;
    }
    var bgColorConfig = bgColorConfigFun();
    $.each(bgColorConfig, function (key, val) {
        if (key == bgcolorId) {
            html += '<li class="layui-this" data-select-bgcolor="' + key + '">\n';
        } else {
            html += '<li  data-select-bgcolor="' + key + '">\n';
        }
        html += '<a href="javascript:;" data-skin="skin-blue" style="" class="clearfix full-opacity-hover">\n' +
            '<div><span style="display:block; width: 20%; float: left; height: 12px; background: ' + val.headerLogo + ';"></span><span style="display:block; width: 80%; float: left; height: 12px; background: ' + val.headerRight + ';"></span></div>\n' +
            '<div><span style="display:block; width: 20%; float: left; height: 40px; background: ' + val.menuLeft + ';"></span><span style="display:block; width: 80%; float: left; height: 40px; background: #f4f5f7;"></span></div>\n' +
            '</a>\n' +
            '</li>';
    });
    return html;
};
/**
 * 配色方案配置项(默认选中第一个方案)
 * @param bgcolorId
 */
 function bgColorConfigFun(bgcolorId) {
    var bgColorConfig = [
        {
            headerRight: '#1aa094',
            headerRightThis: '#197971',
            headerLogo: '#243346',
            menuLeft: '#2f4056',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#23262e',
            headerRightThis: '#0c0c0c',
            headerLogo: '#0c0c0c',
            menuLeft: '#23262e',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#ffa4d1',
            headerRightThis: '#bf7b9d',
            headerLogo: '#e694bd',
            menuLeft: '#1f1f1f',
            menuLeftThis: '#ffa4d1',
            menuLeftHover: '#1f1f1f',
        },
        {
            headerRight: '#1aa094',
            headerRightThis: '#197971',
            headerLogo: '#0c0c0c',
            menuLeft: '#23262e',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#1e9fff',
            headerRightThis: '#0069b7',
            headerLogo: '#0c0c0c',
            menuLeft: '#1f1f1f',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },

        {
            headerRight: '#ffb800',
            headerRightThis: '#d09600',
            headerLogo: '#243346',
            menuLeft: '#2f4056',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#e82121',
            headerRightThis: '#ae1919',
            headerLogo: '#0c0c0c',
            menuLeft: '#1f1f1f',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#963885',
            headerRightThis: '#772c6a',
            headerLogo: '#243346',
            menuLeft: '#2f4056',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#1e9fff',
            headerRightThis: '#0069b7',
            headerLogo: '#0069b7',
            menuLeft: '#1f1f1f',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#ffb800',
            headerRightThis: '#d09600',
            headerLogo: '#d09600',
            menuLeft: '#2f4056',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#e82121',
            headerRightThis: '#ae1919',
            headerLogo: '#d91f1f',
            menuLeft: '#1f1f1f',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        },
        {
            headerRight: '#963885',
            headerRightThis: '#772c6a',
            headerLogo: '#772c6a',
            menuLeft: '#2f4056',
            menuLeftThis: '#1aa094',
            menuLeftHover: '#3b3f4b',
        }
    ];

    if (bgcolorId == undefined) {
        return bgColorConfig;
    } else {
        return bgColorConfig[bgcolorId];
    }
};


/**
 *  系统配置
 * @param name
 * @returns {{BgColorDefault: number, urlSuffixDefault: boolean}|*}
 */
var config = function (name) {

    var config = {
        urlHashLocation: true,   // URL地址hash定位
        urlSuffixDefault: false, // URL后缀
        BgColorDefault: 0,       // 默认皮肤（0开始）
        checkUrlDefault: false,   // 是否判断URL有效
    };

    if (name == undefined) {
        return config;
    } else {
        return config[name];
    }
};


//显示隐藏侧边栏
var isShow=1;
function iconHide(){
    if(isShow===1)
        hide();
    else
        show();
    isShow*=-1;
}
function hide(){
    $('.layuimini-tool-left').animate({left:'50px'});

    $('.layui-side cite').hide();
    $('.layui-side').animate({width:'55px'});
    $('.layui-logo').html("SM")
    $('.layui-logo').animate({width:'55px'});
    $('.layui-body').animate({left:'60px'});
    document.getElementById('hide').className="layui-color layui-icon layui-icon-spread-left";
    $(".layui-side li").removeClass("layui-nav-itemed");
}
function show(){
    $('.layuimini-tool-left').animate({left:'200px'});

    $('.layui-side cite').show();
    $('.layui-side').animate({width:'200px'});
    $('.layui-body').animate({left:'200px'});
    $('.layui-logo').html("Springboot-Manager")
    $('.layui-logo').animate({width:'200px'});
    document.getElementById('hide').className="layui-color layui-icon layui-icon-shrink-right";
    var id = $(".layui-tab-title li.layui-this").attr("lay-id");
    if (!CoreUtil.isEmpty(id)) {
        $("a[data-id='"+id+"']").parents("li").addClass("layui-nav-itemed")
    }
}
function ulHide(){
    if(isShow===-1)
        show();
    isShow=1;
}

/**
 * 选项卡操作
 */
$('body').on('click', '[data-page-close]', function () {
    var loading = layer.load(0, {shade: false, time: 2 * 1000});
    var closeType = $(this).attr('data-page-close');

    if (closeType == 'all') {
            if($(".layui-tab-title li").length > 1){
                $(".layui-tab-title li").each(function(){
                    if($(this).attr("lay-id") != ''){
                        element.tabDelete("tab",$(this).attr("lay-id")).init();
                    }
                })
            }else{
                layer.msg("没有可以关闭的窗口了@_@");
            }
    } else {
        if($(".layui-tab-title li").length > 1){

            console.log($(".layui-tab-title li").length)
            console.log($(".layui-tab-title li.layui-this span").text())
            if ($(".layui-tab-title li").length == 2 && $(".layui-tab-title li.layui-this span").text()!="主页"){
                layer.msg("没有可以关闭的窗口了@_@");
            } else {
                $(".layui-tab-title li").each(function(){
                    if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
                        element.tabDelete("tab",$(this).attr("lay-id")).init();
                    }
                })
            }

        }else{
            layer.msg("没有可以关闭的窗口了@_@");
        }
    }
    layer.close(loading);
});
