package com.syc.oa.controller;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TUser;
import com.syc.oa.service.UserService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/loginForm",method = RequestMethod.POST)
    public String loginForm(String loginName, String password, String isCheck, HttpServletRequest request, HttpServletResponse response){
        String options="remember";
        if (options.equals(isCheck)){
            Cookie stateCookie = new Cookie(options, "checked='checked'");
            Cookie nameCookie = new Cookie("loginName", loginName);
            stateCookie.setMaxAge(Integer.MAX_VALUE);
            nameCookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(stateCookie);
            response.addCookie(nameCookie);
        }else{
            Cookie[] cookies = request.getCookies();
            if (cookies!=null){
                for (Cookie ck:cookies){
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                }
            }
        }
        TUser user = userService.findUserByNameAndPassword(loginName, password);
        if (user!=null){
            request.getSession().setAttribute("user_session",user);
            request.getSession().setMaxInactiveInterval(24*60*60);
            return "index";
        }
        return "login";
    }

    @RequestMapping("/loginOut")
    public String loginOut(){
        //http://localhost:8080/user/login
        //return "redirect:login";
        return "redirect:/login";
    }

    @RequestMapping("/selectUser")
    public String showUser(){
        return "user/user";
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public PageBean<TUser> getUsers(@RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "1")String status,
                           @RequestParam(defaultValue = "1")Integer pageNumber,
                           @RequestParam(defaultValue = "10")Integer pageSize){
        PageInfo<TUser> info = userService.findAll(username, status, pageNumber, pageSize);
        PageBean<TUser> pageBean = new PageBean<>();
        pageBean.setRows(info.getList());
        pageBean.setTotal(info.getTotal());
        return pageBean;
    }

    @RequestMapping("/addUser")
    public String addUser(Integer flag, TUser user, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            return "user/showAddUser"; //跳转到添加用户
        }else {
            //真正实现添加
            if (userService.addUser(user)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/updateUser")
    public String updateUser(Integer flag,Integer id, TUser user,Model model,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            model.addAttribute("user", userService.findUserById(id));
            return "user/showUpdateUser";
        }else {
            if (userService.updateUser(user)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/removeUser")
    @ResponseBody
    public ModelAndView removeUser(Integer flag, Integer id, @RequestParam(name = "ids[]",required = false) Integer[] ids,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            //单个删除
            userService.removeOne(id);
            return new ModelAndView("user/user");
        }else {
            if (userService.removeMore(ids)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }
}
