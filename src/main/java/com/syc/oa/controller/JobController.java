package com.syc.oa.controller;

import com.syc.oa.model.TJob;
import com.syc.oa.service.JobService;
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
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping("/selectJob")
    public String showDept(){
        return "job/job";
    }

    @RequestMapping("/loadJob")
    @ResponseBody
    public PageBean<TJob> getUsers(@RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "1")Integer pageNumber,
                                   @RequestParam(defaultValue = "10")Integer pageSize){
        PageBean<TJob> pageBean = jobService.findAll(name, pageNumber, pageSize);
        return pageBean;
    }

    @RequestMapping("/addJob")
    public String addUser(Integer flag, TJob job, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            return "job/showAddJob";
        }else {
            //真正实现添加
            if (jobService.addJob(job)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/updateJob")
    public String updateUser(Integer flag, Integer id, TJob job, Model model, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            model.addAttribute("job", jobService.findJobById(id));
            return "job/showUpdateJob";
        }else {
            if (jobService.updateJob(job)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/removeJob")
    @ResponseBody
    public ModelAndView removeUser(Integer flag, Integer id, @RequestParam(name = "ids[]",required = false) Integer[] ids, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            jobService.removeOne(id);
            return new ModelAndView("job/job");
        }else {
            if (jobService.removeMore(ids)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }
}
