package com.syc.oa.controller;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TAdvice;
import com.syc.oa.service.AdviceService;
import com.syc.oa.util.MailUtil;
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
import java.util.HashMap;

@Controller
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    private AdviceService adviceService;

    @RequestMapping("selectAdvice")
    public String showAdvice(){
        return "advice/advice";
    }

    @RequestMapping(value="/loadAdvice",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public PageBean<TAdvice> loadAdvice(@RequestParam(required = false,defaultValue = "") String title,
                                        @RequestParam(required = false,defaultValue = "")String content,
                                        Integer pageNum,
                                        Integer pageSize){
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        params.put("title",title);
        PageInfo<TAdvice> info = adviceService.selectAdvice(params);
        PageBean<TAdvice> pageBean = new PageBean<>();
        pageBean.setTotal(info.getTotal());
        pageBean.setRows(info.getList());
        return pageBean;
    }

    //公告预览   produces属性可以设置返回数据的类型以及编码,和ResponseBody联用
    @RequestMapping(value = "/preAdvice",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public TAdvice preAdvice(Integer id){
        return adviceService.selectAdviceById(id);
    }

    @RequestMapping("/addAdvice")
    public String addAdvice(Integer flag, Integer uid, TAdvice advice, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            return "advice/showAddAdvice";
        }else{
            //真正实现添加
            if (adviceService.addAdvice(uid,advice)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/updateAdvice")
    public String updateAdvice(Integer flag, Integer id, Model model, TAdvice advice, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            model.addAttribute("advice",adviceService.selectAdviceById(id));
            return "advice/showUpdateAdvice";
        }else{
            if (adviceService.updateAdvice(advice)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/removeAdvice")
    @ResponseBody
    public ModelAndView removeAdvice(Integer flag, Integer id, ModelAndView modelAndView, @RequestParam(required = false,name = "ids[]") Integer[] ids, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            adviceService.removeOne(id);
            modelAndView.setViewName("redirect:/advice/selectAdvice");
            return modelAndView;
        }else{
            if (adviceService.removeMore(ids)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/addEmail")
    public String sendMail(Integer flag, String email, String title, String content, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        if (flag == 1) {
            return "advice/showAddEmail";
        } else {
            if (MailUtil.sendMail(email, title, content)) {
                writer.print("success");
            } else {
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/addMsg")
    public String sendMsg(Integer flag,String phone,String content,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            return "advice/showAddMsg";
        }else{
            /*if(MailUtil.sendMail(email,title,content)){
                writer.print("success");
            }else{
                writer.print("error");
            }*/
        }
        return null;
    }
}
