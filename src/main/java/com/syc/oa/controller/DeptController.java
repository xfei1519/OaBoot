package com.syc.oa.controller;

import com.syc.oa.model.TDept;
import com.syc.oa.service.DeptService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/selectDept")
    public String showDept(){
        return "dept/dept";
    }

    @RequestMapping("/loadDept")
    @ResponseBody
    public PageBean<TDept> getUsers(@RequestParam(value = "dname",defaultValue = "") String name,
                                    @RequestParam(defaultValue = "1")Integer pageNumber,
                                    @RequestParam(defaultValue = "10")Integer pageSize){
        PageBean<TDept> pageBean = deptService.findAll(name, pageNumber, pageSize);
        return pageBean;
    }

    @RequestMapping("/addDept")
    public String addUser(Integer flag, TDept dept, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            return "dept/showAddDept";
        }else {
            //真正实现添加
            if (deptService.addDept(dept)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/updateDept")
    public String updateUser(Integer flag, Integer id, TDept dept, Model model, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            model.addAttribute("dept", deptService.findDeptById(id));
            return "dept/showUpdateDept";
        }else {
            if (deptService.updateDept(dept)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/removeDept")
    @ResponseBody
    public ModelAndView removeUser(Integer flag, Integer id, @RequestParam(name = "ids[]",required = false) Integer[] ids, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            deptService.removeOne(id);
            return new ModelAndView("dept/dept");
        }else {
            if (deptService.removeMore(ids)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }
}
