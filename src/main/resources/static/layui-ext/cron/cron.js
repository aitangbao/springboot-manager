/**
 @ Name：layui.cron Cron表达式解析器
 @ Author：贝哥哥
 @ License：MIT
 */

layui.define(["form"], function (exports) {
  //假如该组件依赖 layui.form
  var $ = layui.$,
    form = layui.form,
    //字符常量
    MOD_NAME = "cron",
    ELEM = ".layui-cron",
    //外部接口
    cron = {
      v: "1.0.0",
      index: layui.cron ? layui.cron.index + 10000 : 0,

      //设置全局项
      set: function (options) {
        var that = this;
        that.config = $.extend({}, that.config, options);
        return that;
      },

      //事件监听
      on: function (events, callback) {
        return layui.onevent.call(this, MOD_NAME, events, callback);
      },
      //主体CSS等待事件
      ready: function (fn) {
        var cssPath = layui.cache.base + "cron/cron.css?v=" + cron.v;
        layui.link(cssPath, fn, "cron"); //此处的“cron”要对应 cron.css 中的样式： html #layuicss-cron{}
        return this;
      },
    },
    // 返回当前实例
    thisIns = function () {
      var that = this,
        options = that.config,
        id = options.id || options.index;
      return {
        reload: function (options) {
          that.reload.call(that, options);
        },
        config: options,
      };
    },
    //构造器
    Class = function (options) {
      var that = this;
      that.index = ++cron.index;
      that.config = $.extend({}, that.config, cron.config, options);
      cron.ready(function () {
        that.init();
      });
    };

  //默认配置
  Class.prototype.config = {
    value: "* * * * * ?", // 当前表达式值，每秒执行一次
    lang: "cn", //语言，只支持cn/en，即中文和英文
    trigger: "focus", //呼出控件的事件
    done: null, //控件选择完毕后的回调，点击运行/确定也均会触发
    run: null, // 最近运行时间接口
  };

  // 多语言
  Class.prototype.lang = function () {
    var that = this,
      options = that.config,
      text = {
        cn: {
          tabs: ["秒", "分", "时", "日", "月", "周", "年"],
          tools: {
            confirm: "确定",
            parse: "解析",
            run: "运行",
          },
        },
        en: {
          tabs: [
            "Seconds",
            "Minutes",
            "Hours",
            "Days",
            "Months",
            "Weeks",
            "Years",
          ],
          weeks: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
          month: [
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec",
          ],
          tools: {
            confirm: "Confirm",
            parse: "Parse",
            run: "Run",
          },
        },
      };
    return text[options.lang] || text["cn"];
  };

  // 初始准备
  Class.prototype.init = function () {
    var that = this,
      options = that.config;

    that.$elem = $(options.elem);
    options.elem = that.$elem[0];

    if (!options.elem) return;

    options.value || (options.value = "* * * * * ?");

    // 初始化表达式
    var arr = options.value.split(" ");
    that.cron = [];
    for (var i = 1; i <= 7; i++) {
      if (i < 6) {
        that.cron.push(arr[i - 1] || "*");
      } else if (i < 7) {
        that.cron.push(arr[i - 1] || "?");
      } else {
        that.cron.push(arr[i - 1] || "");
      }
    }

    //如果不是input|textarea元素，则默认采用click事件
    if (!that.isInput(options.elem)) {
      if (options.trigger === "focus") {
        options.trigger = "click";
      }
    }

    console.log("绑定事件.");
    that.events();
  };

  // 绑定的元素事件处理
  Class.prototype.events = function () {
    var that = this,
      options = that.config;
    if (!options.elem || options.elem.eventHandler) return;

    // 绑定触发事件
    that.$elem.on(options.trigger, function () {
      that.render();
    });

    // 绑定关闭控件事件
    $(document)
      .on("click", function (e) {
        if (
          (that.elemCron && that.elemCron.contains(e.target)) ||
          e.target === options.elem
        ) {
          return; // 点击的是当前绑定元素或cron容器内的元素则不关闭
        }
        that.remove();
      })
      .on("keydown", function (e) {
        if (e.keyCode === 13) {
          e.preventDefault();
          layer.msg("取消事件默认动作，点击确定按钮");
          // $(that.footer).find(ELEM_CONFIRM)[0].click();
        }
      });

    //自适应定位
    $(window).on("resize", function () {
      if (!options.elem || !$(ELEM)[0]) {
        return false;
      }
      that.position();
    });

    options.elem.eventHandler = true;
  };

  // 渲染视图
  Class.prototype.render = function () {
    var that = this,
      options = that.config,
      lang = that.lang();
    // that.remove();

    var $elemCron = (that.$elemCron = $('<div class="layui-cron"></div>')),
      // 主区域
      elemMain = [], // tabs容器
      elemSeconds = that.getSecondsElem(), // 秒
      elemMinutes = that.getMinutesElem(), // 分
      elemHours = that.getHoursElem(), // 时
      elemDays = that.getDaysElem(), // 日
      elemMonths = that.getMonthsElem(), // 月
      elemWeeks = that.getWeeksElem(), // 周
      elemYears = that.getYearsElem(); // 年

    // 组装容器
    elemMain.push(
      '<div class="layui-tab layui-tab-card">',
      '<ul class="layui-tab-title">'
    );
    layui.each(lang.tabs, function (i, tab) {
      if (i == 0) {
        elemMain.push('<li class="layui-this">', tab, "</li>");
      } else {
        elemMain.push("<li>", tab, "</li>");
      }
    });
    elemMain.push(
      "</ul>",
      '<div class="layui-tab-content" style="width:651px;min-height:300px;">',
      elemSeconds,
      elemMinutes,
      elemHours,
      elemDays,
      elemMonths,
      elemWeeks,
      elemYears,
      "</div>",
      "</div>"
    );
    $elemCron.append(elemMain.join(""));

    // 底部区域
    var elemFooter = [
      '<div class="cron-title">最近运行时间</div>',
      '<div class="cron-box" id="run-list"></div>',
      '<div class="cron-box"><div class="cron-footer-btns"><span lay-type="run" class="cron-btns-run">运行</span><span lay-type="confirm" class="cron-btns-confirm">确定</span></div></div>',
    ];
    $elemCron.append(elemFooter.join(""));

    // 渲染
    $("body").append($elemCron);
    that.elemCron = that.$elemCron[0];
    form.render();

    // 定位
    that.position();

    // 监听
    //点击底部按钮
    $elemCron.find(".cron-footer-btns span").on("click", function () {
      var type = $(this).attr("lay-type");
      that.tool(this, type);
    });
  };

  // 渲染秒
  Class.prototype.getSecondsElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-show layui-form" lay-filter="cronSecForm">',
      ];

    var radio = ["", "", "", ""];
    val = ["", "", "", "", []];

    if (that.cron[0] == "*") {
      radio[0] = "checked";
    } else if (that.cron[0].split("-").length == 2) {
      radio[1] = "checked";
      val[0] = that.cron[0].split("-")[0];
      val[1] = that.cron[0].split("-")[1];
    } else if (that.cron[0].split("/").length == 2) {
      radio[2] = "checked";
      val[2] = that.cron[0].split("/")[0];
      val[3] = that.cron[0].split("/")[1];
    } else {
      radio[3] = "checked";
      val[4] = that.cron[0].split(",");
    }

    elem.push(
      '<div><input type="radio" name="type[0]" value="all" title="每秒" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[0]" value="range" title="周期" ',
      radio[1],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">秒</div><span class="cron-tips">(0-59)</span></div>',
      '<div><input type="radio" name="type[0]" value="per" title="按照" ',
      radio[2],
      ">",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="perFrom" value="',
      val[2],
      '"/>',
      '<div class="cron-input-mid">秒开始，每</div><input class="cron-input" type="number" name="perVal" value="',
      val[3],
      '"/>',
      '<div class="cron-input-mid">秒执行一次</div><span class="cron-tips">(0/60)</span></div>',
      '<div><input type="radio" name="type[0]" value="assign" title="指定" ',
      radio[3],
      "></div>"
    );
    elem.push("<div>");
    for (var i = 0; i < 60; i++) {
      elem.push(
        '<input type="checkbox" lay-skin="primary" name="seconds[',
        i,
        ']" value="',
        i,
        '" title="',
        i,
        '" ',
        val[4].indexOf("" + i) > -1 ? "checked" : "",
        ">"
      );
    }
    elem.push("</div>");

    elem.push("</div>");

    return elem.join("");
  };

  // 渲染分
  Class.prototype.getMinutesElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-form" lay-filter="cronMinForm">',
      ];

    var radio = ["", "", "", ""];
    val = ["", "", "", "", []];

    if (that.cron[1] == "*") {
      radio[0] = "checked";
    } else if (that.cron[1].split("-").length == 2) {
      radio[1] = "checked";
      val[0] = that.cron[1].split("-")[0];
      val[1] = that.cron[1].split("-")[1];
    } else if (that.cron[1].split("/").length == 2) {
      radio[2] = "checked";
      val[2] = that.cron[1].split("/")[0];
      val[3] = that.cron[1].split("/")[1];
    } else {
      radio[3] = "checked";
      val[4] = that.cron[1].split(",");
    }

    elem.push(
      '<div><input type="radio" name="type[1]" value="all" title="每分" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[1]" value="range" title="周期" ',
      radio[1],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">分</div><span class="cron-tips">(0-59)</span></div>',
      '<div><input type="radio" name="type[1]" value="per" title="按照" ',
      radio[2],
      ">",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="perFrom" value="',
      val[2],
      '"/>',
      '<div class="cron-input-mid">分开始，每</div><input class="cron-input" type="number" name="perVal" value="',
      val[3],
      '"/>',
      '<div class="cron-input-mid">分执行一次</div><span class="cron-tips">(0/60)</span></div>',
      '<div><input type="radio" name="type[1]" value="assign" title="指定" ',
      radio[3],
      "></div>"
    );
    elem.push("<div>");
    for (var i = 0; i < 60; i++) {
      elem.push(
        '<input type="checkbox" lay-skin="primary" name="minutes[',
        i,
        ']" value="',
        i,
        '" title="',
        i,
        '"',
        val[4].indexOf("" + i) > -1 ? "checked" : "",
        ">"
      );
    }
    elem.push("</div>");

    elem.push("</div>");

    return elem.join("");
  };

  // 渲染时
  Class.prototype.getHoursElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-form" lay-filter="cronHourForm">',
      ];

    var radio = ["", "", "", ""];
    val = ["", "", "", "", []];

    if (that.cron[2] == "*") {
      radio[0] = "checked";
    } else if (that.cron[2].split("-").length == 2) {
      radio[1] = "checked";
      val[0] = that.cron[2].split("-")[0];
      val[1] = that.cron[2].split("-")[1];
    } else if (that.cron[2].split("/").length == 2) {
      radio[2] = "checked";
      val[2] = that.cron[2].split("/")[0];
      val[3] = that.cron[2].split("/")[1];
    } else {
      radio[3] = "checked";
      val[4] = that.cron[2].split(",");
    }

    elem.push(
      '<div><input type="radio" name="type[2]" value="all" title="每小时" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[2]" value="range" title="周期" ',
      radio[1],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">时</div><span class="cron-tips">(0-23)</span></div>',
      '<div><input type="radio" name="type[2]" value="per" title="按照" ',
      radio[2],
      ">",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="perFrom" value="',
      val[2],
      '"/>',
      '<div class="cron-input-mid">时开始，每</div><input class="cron-input" type="number" name="perVal" value="',
      val[3],
      '"/>',
      '<div class="cron-input-mid">时执行一次</div><span class="cron-tips">(0/24)</span></div>',
      '<div><input type="radio" name="type[2]" value="assign" title="指定" ',
      radio[3],
      "></div>"
    );
    elem.push("<div>");
    for (var i = 0; i < 24; i++) {
      elem.push(
        '<input type="checkbox" lay-skin="primary" name="hours[',
        i,
        ']" value="',
        i,
        '" title="',
        i,
        '" ',
        val[4].indexOf("" + i) > -1 ? "checked" : "",
        ">"
      );
    }
    elem.push("</div>");

    elem.push("</div>");

    return elem.join("");
  };

  // 渲染天
  Class.prototype.getDaysElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-form" lay-filter="cronDayForm">',
      ];

    var radio = ["", "", "", "", "", "", ""];
    val = ["", "", "", "", "", []];

    if (that.cron[3] == "*") {
      radio[0] = "checked";
    } else if (that.cron[3] == "?") {
      radio[1] = "checked";
    } else if (that.cron[3].split("-").length == 2) {
      radio[2] = "checked";
      val[0] = that.cron[3].split("-")[0];
      val[1] = that.cron[3].split("-")[1];
    } else if (that.cron[3].split("/").length == 2) {
      radio[3] = "checked";
      val[2] = that.cron[3].split("/")[0];
      val[3] = that.cron[3].split("/")[1];
    } else if (that.cron[3].indexOf("W") > -1) {
      radio[4] = "checked";
      val[4] = that.cron[3].match(/(\d+)W$/)[1];
    } else if (that.cron[3] == "L") {
      radio[5] = "checked";
    } else {
      radio[6] = "checked";
      val[5] = that.cron[3].split(",");
    }

    elem.push(
      '<div><input type="radio" name="type[3]" value="all" title="每天" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[3]" value="none" title="不指定" ',
      radio[1],
      "></div>",
      '<div><input type="radio" name="type[3]" value="range" title="周期" ',
      radio[2],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">日</div><span class="cron-tips">(1-31)</span></div>',
      '<div><input type="radio" name="type[3]" value="per" title="按照" ',
      radio[3],
      ">",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="perFrom" value="',
      val[2],
      '"/>',
      '<div class="cron-input-mid">日开始，每</div><input class="cron-input" type="number" name="perVal" value="',
      val[3],
      '"/>',
      '<div class="cron-input-mid">日执行一次</div><span class="cron-tips">(1/31)</span></div>',
      '<div><input type="radio" name="type[3]" value="work" title="工作日" ',
      radio[4],
      ">",
      '<div class="cron-input-mid">每月</div><input class="cron-input" type="number" name="workDay" value="',
      val[4],
      '"/>',
      '<div class="cron-input-mid">号最近的那个工作日</div><span class="cron-tips">(1-31)</span></div>',
      '<div><input type="radio" name="type[3]" value="last" title="本月最后一日" ',
      radio[5],
      "></div>",
      '<div><input type="radio" name="type[3]" value="assign" title="指定" ',
      radio[6],
      "></div>"
    );
    elem.push("<div>");
    for (var i = 1; i <= 31; i++) {
      elem.push(
        '<input type="checkbox" lay-skin="primary" name="days[',
        i,
        ']" value="',
        i,
        '" title="',
        i,
        '" ',
        val[5].indexOf("" + i) > -1 ? "checked" : "",
        ">"
      );
    }
    elem.push("</div>");

    elem.push("</div>");

    return elem.join("");
  };

  // 渲染月
  Class.prototype.getMonthsElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-form" lay-filter="cronMonthForm">',
      ];

    var radio = ["", "", "", "", ""];
    val = ["", "", "", "", []];

    if (that.cron[4] == "*") {
      radio[0] = "checked";
    } else if (that.cron[4] == "?") {
      radio[1] = "checked";
    } else if (that.cron[4].split("-").length == 2) {
      radio[2] = "checked";
      val[0] = that.cron[4].split("-")[0];
      val[1] = that.cron[4].split("-")[1];
    } else if (that.cron[4].split("/").length == 2) {
      radio[3] = "checked";
      val[2] = that.cron[4].split("/")[0];
      val[3] = that.cron[4].split("/")[1];
    } else {
      radio[4] = "checked";
      val[4] = that.cron[4].split(",");
    }

    elem.push(
      '<div><input type="radio" name="type[4]" value="all" title="每个月" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[4]" value="none" title="不指定" ',
      radio[1],
      "></div>",
      '<div><input type="radio" name="type[4]" value="range" title="周期" ',
      radio[2],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">月</div><span class="cron-tips">(1-12)</span></div>',
      '<div><input type="radio" name="type[4]" value="per" title="按照" ',
      radio[3],
      ">",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="perFrom" value="',
      val[2],
      '"/>',
      '<div class="cron-input-mid">月开始，每</div><input class="cron-input" type="number" name="perVal" value="',
      val[3],
      '"/>',
      '<div class="cron-input-mid">月执行一次</div><span class="cron-tips">(1/12)</span></div>',
      '<div><input type="radio" name="type[4]" value="assign" title="指定" ',
      radio[4],
      "></div>"
    );
    elem.push("<div>");
    for (var i = 0; i < 24; i++) {
      elem.push(
        '<input type="checkbox" lay-skin="primary" name="months[',
        i,
        ']" value="',
        i,
        '" title="',
        i,
        '" ',
        val[4].indexOf("" + i) > -1 ? "checked" : "",
        ">"
      );
    }
    elem.push("</div>");

    elem.push("</div>");

    return elem.join("");
  };

  // 渲染周
  Class.prototype.getWeeksElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-form" lay-filter="cronWeekForm">',
      ];

    var radio = ["", "", "", "", "", ""];
    val = ["", "", "", "", "", []];

    if (that.cron[5] == "*") {
      radio[0] = "checked";
    } else if (that.cron[5] == "?") {
      radio[1] = "checked";
    } else if (that.cron[5].split("-").length == 2) {
      radio[2] = "checked";
      val[0] = that.cron[5].split("-")[0];
      val[1] = that.cron[5].split("-")[1];
    } else if (that.cron[5].split("#").length == 2) {
      radio[3] = "checked";
      val[2] = that.cron[5].split("#")[0];
      val[3] = that.cron[5].split("#")[1];
    } else if (/\d+L/.test(that.cron[5])) {
      radio[4] = "checked";
      val[4] = that.cron[5].match(/(\d+)L/)[1];
    } else {
      radio[5] = "checked";
      val[5] = that.cron[5].split(",");
    }

    elem.push(
      '<div><input type="radio" name="type[5]" value="all" title="每个周" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[5]" value="none" title="不指定" ',
      radio[1],
      "></div>",
      '<div><input type="radio" name="type[5]" value="range" title="周期" ',
      radio[2],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">周</div><span class="cron-tips">(1-7)</span></div>',
      '<div><input type="radio" name="type[5]" value="per" title="按照" ',
      radio[3],
      ">",
      '<div class="cron-input-mid">第</div><input class="cron-input" type="number" name="perFrom" value="',
      val[2],
      '"/>',
      '<div class="cron-input-mid">周的星期</div><input class="cron-input" type="number" name="perVal" value="',
      val[3],
      '"/>',
      '<span class="cron-tips">(1-4/1-7)</span></div>',
      '<div><input type="radio" name="type[5]" value="last" title="本月最后一个星期" ',
      radio[4],
      '><input class="cron-input" type="number" name="lastVal" value="',
      val[4],
      '" />',
      '<span class="cron-tips">(1-7)</span></div>',
      '<div><input type="radio" name="type[5]" value="assign" title="指定" ',
      radio[5],
      "></div>"
    );
    elem.push("<div>");
    for (var i = 1; i <= 7; i++) {
      elem.push(
        '<input type="checkbox" lay-skin="primary" name="weeks[',
        i,
        ']" value="',
        i,
        '" title="',
        i,
        '" ',
        val[5].indexOf("" + i) > -1 ? "checked" : "",
        ">"
      );
    }
    elem.push("</div>");

    elem.push("</div>");

    return elem.join("");
  };

  // 渲染年
  Class.prototype.getYearsElem = function () {
    var that = this,
      options = that.config,
      elem = [
        '<div class="layui-tab-item layui-form" lay-filter="cronYearForm">',
      ];

    var radio = ["", "", ""];
    val = ["", ""];

    if (that.cron[6] == "*") {
      radio[0] = "checked";
    } else if (that.cron[6] == "" || that.cron[6] == " ") {
      radio[1] = "checked";
    } else if (that.cron[6].split("-").length == 2) {
      radio[2] = "checked";
      val[0] = that.cron[6].split("-")[0];
      val[1] = that.cron[6].split("-")[1];
    }

    elem.push(
      '<div><input type="radio" name="type[6]" value="all" title="每年" ',
      radio[0],
      "></div>",
      '<div><input type="radio" name="type[6]" value="none" title="不指定" ',
      radio[1],
      "></div>",
      '<div><input type="radio" name="type[6]" value="range" title="周期" ',
      radio[2],
      "/>",
      '<div class="cron-input-mid">从</div><input class="cron-input" type="number" name="rangeStart" value="',
      val[0],
      '"/>',
      '<div class="cron-input-mid">-</div><input class="cron-input" type="number" name="rangeEnd" value="',
      val[1],
      '"/>',
      '<div class="cron-input-mid">年</div></div>'
    );

    elem.push("</div>");

    return elem.join("");
  };

  function isNotBlank(str) {
    return str != undefined && str !== "";
  }

  // 底部按钮事件
  Class.prototype.tool = function (btn, type) {
    var that = this,
      options = that.config,
      active = {
        // 计算秒
        calSeconds: function () {
          var data = form.val("cronSecForm"),
            dataType = data["type[0]"];
          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[0] = data.rangeStart + "-" + data.rangeEnd;
          } else if (
            "per" == dataType &&
            isNotBlank(data.perFrom) &&
            isNotBlank(data.perVal)
          ) {
            that.cron[0] = data.perFrom + "/" + data.perVal;
          } else if ("assign" == dataType) {
            var checkbox = [];
            layui.each(data, function (key, value) {
              if (/^seconds/.test(key)) {
                checkbox.push(value);
              }
            });
            if (checkbox.length) {
              that.cron[0] = checkbox.join(",");
            } else {
              that.cron[0] = "*";
            }
          } else if ("all" == dataType) {
            that.cron[0] = "*";
          }
        },
        calMinutes: function () {
          var data = form.val("cronMinForm"),
            dataType = data["type[1]"];
          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[1] = data.rangeStart + "-" + data.rangeEnd;
          } else if (
            "per" == dataType &&
            isNotBlank(data.perFrom) &&
            isNotBlank(data.perVal)
          ) {
            that.cron[1] = data.perFrom + "/" + data.perVal;
          } else if ("assign" == dataType) {
            var checkbox = [];
            layui.each(data, function (key, value) {
              if (/^minutes/.test(key)) {
                checkbox.push(value);
              }
            });
            if (checkbox.length) {
              that.cron[1] = checkbox.join(",");
            } else {
              that.cron[1] = "*";
            }
          } else if ("all" == dataType) {
            that.cron[1] = "*";
          }
        },
        calHours: function () {
          var data = form.val("cronHourForm"),
            dataType = data["type[2]"];
          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[2] = data.rangeStart + "-" + data.rangeEnd;
          } else if (
            "per" == dataType &&
            isNotBlank(data.perFrom) &&
            isNotBlank(data.perVal)
          ) {
            that.cron[2] = data.perFrom + "/" + data.perVal;
          } else if ("assign" == dataType) {
            var checkbox = [];
            layui.each(data, function (key, value) {
              if (/^hours/.test(key)) {
                checkbox.push(value);
              }
            });
            if (checkbox.length) {
              that.cron[2] = checkbox.join(",");
            } else {
              that.cron[2] = "*";
            }
          } else if ("all" == dataType) {
            that.cron[2] = "*";
          }
        },
        calDays: function () {
          var data = form.val("cronDayForm"),
            dataType = data["type[3]"];

          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[3] = data.rangeStart + "-" + data.rangeEnd;
          } else if (
            "per" == dataType &&
            isNotBlank(data.perFrom) &&
            isNotBlank(data.perVal)
          ) {
            that.cron[3] = data.perFrom + "/" + data.perVal;
          } else if ("work" == dataType && isNotBlank(data.workDay)) {
            that.cron[3] = data.workDay + "W";
          } else if ("last" == dataType) {
            that.cron[3] = "L";
          } else if ("assign" == dataType) {
            var checkbox = [];
            layui.each(data, function (key, value) {
              if (/^days/.test(key)) {
                checkbox.push(value);
              }
            });
            if (checkbox.length) {
              that.cron[3] = checkbox.join(",");
            } else {
              that.cron[3] = "*";
            }
          } else if ("all" == dataType) {
            that.cron[3] = "*";
          } else if ("none" == dataType) {
            that.cron[3] = "?";
          }
        },
        calMonths: function () {
          var data = form.val("cronMonthFrom"),
            dataType = data["type[4]"];
          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[4] = data.rangeStart + "-" + data.rangeEnd;
          } else if (
            "per" == dataType &&
            isNotBlank(data.perFrom) &&
            isNotBlank(data.perVal)
          ) {
            that.cron[4] = data.perFrom + "/" + data.perVal;
          } else if ("assign" == dataType) {
            var checkbox = [];
            layui.each(data, function (key, value) {
              if (/^months/.test(key)) {
                checkbox.push(value);
              }
            });
            if (checkbox.length) {
              that.cron[4] = checkbox.join(",");
            } else {
              that.cron[4] = "*";
            }
          } else if ("all" == dataType) {
            that.cron[4] = "*";
          } else if ("none" == dataType) {
            that.cron[4] = "?";
          }
        },
        calWeeks: function () {
          var data = form.val("cronWeekForm"),
            dataType = data["type[5]"];
            console.log(data);
          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[5] = data.rangeStart + "-" + data.rangeEnd;
          } else if (
            "per" == dataType &&
            isNotBlank(data.perFrom) &&
            isNotBlank(data.perVal)
          ) {
            that.cron[5] = data.perFrom + "#" + data.perVal;
          } else if ("last" == dataType && isNotBlank(data.lastVal)) {
            that.cron[5] = data.lastVal + "L";
          } else if ("assign" == dataType) {
            var checkbox = [];
            layui.each(data, function (key, value) {
              if (/^weeks/.test(key)) {
                checkbox.push(value);
              }
            });
            if (checkbox.length) {
              that.cron[5] = checkbox.join(",");
            } else {
              that.cron[5] = "*";
            }
          } else if ("all" == dataType) {
            that.cron[5] = "*";
          } else if ("none" == dataType) {
            that.cron[5] = "?";
          }
        },
        calYears: function () {
          var data = form.val("cronYearForm"),
            dataType = data["type[6]"];
          if (
            "range" == dataType &&
            isNotBlank(data.rangeStart) &&
            isNotBlank(data.rangeEnd)
          ) {
            that.cron[6] = data.rangeStart + "-" + data.rangeEnd;
          } else if ("all" == dataType) {
            that.cron[6] = "*";
          } else if ("none" == dataType) {
            that.cron[6] = "";
          }
        },

        // 计算表达式
        calculate: function () {
          active.calSeconds();
          active.calMinutes();
          active.calHours();
          active.calDays();
          active.calMonths();
          active.calWeeks();
          active.calYears();
          if (that.cron[5] != "?" && that.cron[3] != "?") {
            layer.msg("不支持周参数和日参数同时存在");
            return false;
          }
          return true;
        },
        // 运行
        run: function () {
          if (!active.calculate()) {
            return;
          }
          var cronStr = that.cron.join(" ").trim();
          // TODO 请求接口获取最近运行时间，或js生成最近运行时间
          if (options.url) {
            $.post(
              options.url,
              { cron: cronStr },
              function (res) {
                if (res.code == 0) {
                  $("#run-list").empty().append(res.data.join("<br>"));
                } else {
                  layer.alert(res.msg, { icon: 2, title: "错误" });
                }
              },
              "json"
            );
          }
          options.done(cronStr);
        },

        //确定
        confirm: function () {
          if (!active.calculate()) {
            return;
          }
          var cronStr = that.cron.join(" ").trim();
          options.done && options.done(cronStr);
          that.remove();
        },
      };
    active[type] && active[type]();
  };

  // 定位算法
  Class.prototype.position = function () {
    var that = this,
      options = that.config,
      elem = options.elem,
      rect = elem.getBoundingClientRect(), //绑定元素的坐标
      cronWidth = that.elemCron.offsetWidth, //控件的宽度
      cronHeight = that.elemCron.offsetHeight, //控件的高度
      //滚动条高度
      scrollArea = function (type) {
        type = type ? "scrollLeft" : "scrollTop";
        return document.body[type] | document.documentElement[type];
      },
      winArea = function (type) {
        return document.documentElement[type ? "clientWidth" : "clientHeight"];
      },
      margin = 5,
      left = rect.left,
      top = rect.bottom;

    //如果右侧超出边界
    if (left + cronWidth + margin > winArea("width")) {
      left = winArea("width") - cronWidth - margin;
    }

    //如果底部超出边界
    if (top + cronHeight + margin > winArea()) {
      top =
        rect.top > cronHeight //顶部是否有足够区域显示完全
          ? rect.top - cronHeight
          : winArea() - cronHeight;
      top = top - margin * 2;
    }

    that.elemCron.style.left =
      left + (options.position === "fixed" ? 0 : scrollArea(1)) + "px";
    that.elemCron.style.top =
      top + (options.position === "fixed" ? 0 : scrollArea()) + "px";
  };

  // 控件移除
  Class.prototype.remove = function () {
    var that = this,
      options = that.config;
    $(ELEM).remove();
  };

  // 是否输入框
  Class.prototype.isInput = function (elem) {
    return /input|textarea/.test(elem.tagName.toLocaleLowerCase());
  };

  /**
   * 核心入口
   * @param options
   * @returns {{reload: reload, config: *}}
   */
  cron.render = function (options) {
    var ins = new Class(options);
    return thisIns.call(ins);
  };

  exports("cron", cron);
});
