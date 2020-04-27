package com.company.project.controller;


import com.company.project.entity.oshi.SystemHardwareInfo;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "视图", description = "负责返回视图")
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/login")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/index/home";
        }
        return "login";
    }

    /**
     * 进入首页
     */
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        return "home";
    }

    /**
     * 更改密码页面
     */
    @GetMapping("/users/password")
    public String updatePassword() {
        return "users/update_password";
    }

    /**
     * 用户编辑个人信息 视图
     */
    @GetMapping("/users/info")
    public String userDetail(Model model) {
        model.addAttribute("flagType", "edit");
        return "users/user_edit";
    }

    /**
     * 菜单权限列表  视图
     */
    @GetMapping("/menus")
    public String menusList() {

        return "menus/menu_list";
    }

    /**
     * 角色列表 操作视图
     */
    @GetMapping("/roles")
    public String roleList() {
        return "roles/role_list";
    }

    /**
     * 用户列表操作 视图
     */
    @GetMapping("/users")
    public String userList() {
        return "users/user_list";
    }

    /**
     * 系统操作日志 视图
     */
    @GetMapping("/logs")
    public String logList() {
        return "logs/log_list";
    }

    /**
     * 组织机构列表 试图
     */
    @GetMapping("/depts")
    public String deptList() {
        return "depts/dept_list";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/500")
    public String error405() {
        return "error/500";
    }

    @GetMapping("/main")
    public String indexHome() {
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/build")
    public String build() {
        return "build";
    }

    @GetMapping("/systemInfo")
    public String systemInfo(Model model) {

        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();

        model.addAttribute("server", systemHardwareInfo);

        return "systemInfo";
    }
}
