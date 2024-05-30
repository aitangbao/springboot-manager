package com.company.project.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Api(tags = "视图")
@Controller
@RequestMapping("")
public class IndexController {

    @GetMapping({"/index/login","/login","/"})
    public String loginIndex() {
        if (StpUtil.isLogin()) {
            return "home";
        }
        return "login";
    }

    @GetMapping("/index/home")
    public String home() {
        if (StpUtil.isLogin()) {
            return "home";
        }
        return "login";
    }

    @GetMapping("/index/users/password")
    public String updatePassword() {
        return "users/update_password";
    }

    @GetMapping("/index/users/info")
    public String userDetail(Model model) {
        model.addAttribute("flagType", "edit");
        return "users/user_edit";
    }

    @GetMapping("/index/menus")
    public String menusList() {

        return "menus/menu_list";
    }

    @GetMapping("/index/roles")
    public String roleList() {
        return "roles/role_list";
    }

    @GetMapping("/index/users")
    public String userList() {
        return "users/user_list";
    }

    @GetMapping("/index/logs")
    public String logList() {
        return "logs/log_list";
    }

    @GetMapping("/index/depts")
    public String deptList() {
        return "depts/dept_list";
    }

    @GetMapping("/index/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/index/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/index/500")
    public String error405() {
        return "error/500";
    }

    @GetMapping("/index/main")
    public String indexHome() {
        return "main";
    }

    @GetMapping("/index/about")
    public String about() {
        return "about";
    }

    @GetMapping("/index/build")
    public String build() {
        return "build";
    }

    @GetMapping("/index/sysContent")
    public String sysContent() {
        return "syscontent/list";
    }

    @GetMapping("/index/sysDict")
    public String sysDict() {
        return "sysdict/list";
    }

    @GetMapping("/index/sysGenerator")
    public String sysGenerator() {
        return "generator/list";
    }

    @GetMapping("/index/sysJob")
    public String sysJob() {
        return "sysjob/list";
    }

    @GetMapping("/index/sysJobLog")
    public String sysJobLog() {
        return "sysjoblog/list";
    }

    @GetMapping("/index/sysFiles")
    public String sysFiles() {
        return "sysfiles/list";
    }
}
