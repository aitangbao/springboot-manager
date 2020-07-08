package com.company.project.controller;

import com.company.project.entity.oshi.SystemHardwareInfo;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/login")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/index/home" ;
        }
        return "login" ;
    }

    @GetMapping("/home")
    public String home() {
        return "home" ;
    }

    @GetMapping("/users/password")
    public String updatePassword() {
        return "users/update_password" ;
    }

    @GetMapping("/users/info")
    public String userDetail(Model model) {
        model.addAttribute("flagType", "edit");
        return "users/user_edit" ;
    }

    @GetMapping("/menus")
    public String menusList() {

        return "menus/menu_list" ;
    }

    @GetMapping("/roles")
    public String roleList() {
        return "roles/role_list" ;
    }

    @GetMapping("/users")
    public String userList() {
        return "users/user_list" ;
    }

    @GetMapping("/logs")
    public String logList() {
        return "logs/log_list" ;
    }

    @GetMapping("/depts")
    public String deptList() {
        return "depts/dept_list" ;
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403" ;
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404" ;
    }

    @GetMapping("/500")
    public String error405() {
        return "error/500" ;
    }

    @GetMapping("/main")
    public String indexHome() {
        return "main" ;
    }

    @GetMapping("/about")
    public String about() {
        return "about" ;
    }

    @GetMapping("/build")
    public String build() {
        return "build" ;
    }

    @GetMapping("/systemInfo")
    public String systemInfo(Model model) {

        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();

        model.addAttribute("server", systemHardwareInfo);

        return "systemInfo" ;
    }

    @GetMapping("/sysContent")
    public String sysContent() {
        return "syscontent/list" ;
    }

    @GetMapping("/sysDict")
    public String sysDict() {
        return "sysdict/list" ;
    }

    @GetMapping("/sysGenerator")
    public String sysGenerator() {
        return "generator/list" ;
    }

    @GetMapping("/sysJob")
    public String sysJob() {
        return "sysjob/list";
    }

    @GetMapping("/sysJobLog")
    public String sysJobLog() {
        return "sysjoblog/list";
    }

    @GetMapping("/sysFiles")
    public String sysFiles() {
        return "sysfiles/list";
    }
}
