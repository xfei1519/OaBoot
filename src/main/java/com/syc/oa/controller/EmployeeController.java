package com.syc.oa.controller;

import com.syc.oa.model.TEmployee;
import com.syc.oa.model.TEmployee2;
import com.syc.oa.service.TEmployeeService;
import com.syc.oa.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private TEmployeeService tEmployeeService;

    @RequestMapping("/selectEmployee")
    public String selectEmployee(){
        return "employee/employee";
    }

    @RequestMapping(value = "/loadEmployee",method = RequestMethod.POST)
    @ResponseBody
    public PageBean<TEmployee2> getAll(@RequestParam(defaultValue = "-1") Integer did,
                                      @RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "") String cardId,
                                      @RequestParam(defaultValue = "-1") Integer jid,
                                      @RequestParam(value = "modules",defaultValue = "-1") Integer gender,
                                      @RequestParam(defaultValue = "") String phone,
                                      @RequestParam(defaultValue = "1")Integer pageSize,
                                      @RequestParam(defaultValue = "10")Integer pageNumber){
        System.out.println("gender******"+gender);
        System.out.println("did+++++++++"+did);
        System.out.println("jid---------"+jid);
        /*System.out.println("name********"+name);
        System.out.println("cardId++++++"+cardId);
        System.out.println("phone-------"+phone);*/
        PageBean<TEmployee2> pageBean= tEmployeeService.getAll(did,name,cardId,jid,gender,phone,pageSize,pageNumber);
        return pageBean;
    }

    @RequestMapping("/addEmployee")
    public String addEmployee(Integer flag, TEmployee employee, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            return "employee/showAddEmployee";
        }else{
            if (tEmployeeService.addEmployee(employee)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/updateEmployee")
    public ModelAndView addEmployee(Integer flag, Integer id,TEmployee employee,ModelAndView modelAndView,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            modelAndView.setViewName("employee/showUpdateEmployee");
            modelAndView.addObject("employee",tEmployeeService.findEmployeeById(id));
            return modelAndView;
        }else{
            if (tEmployeeService.updateEmployee(employee)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

    @RequestMapping("/removeEmployee")
    @ResponseBody
    public ModelAndView addEmployee(Integer flag, Integer id,@RequestParam(name = "ids[]",required = false)Integer[] ids,ModelAndView modelAndView,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        if (flag==1){
            tEmployeeService.removeOne(id);
            modelAndView.setViewName("employee/employee");
            return modelAndView;
        }else{
            if (tEmployeeService.removeMore(ids)){
                writer.print("success");
            }else{
                writer.print("error");
            }
        }
        return null;
    }

}
