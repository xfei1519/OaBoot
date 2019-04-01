package com.syc.oa.controller;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TSign;
import com.syc.oa.service.SignService;
import com.syc.oa.vo.PageBean;
import com.syc.oa.vo.SignChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @RequestMapping("/selectSign")
    public String showSign(){
        return "sign/sign";
    }

    @RequestMapping("/loadSign")
    @ResponseBody
    public PageBean<TSign> loadSign(@RequestParam(defaultValue = "2019-01-01") String startDate,
                                    @RequestParam(defaultValue = "2019-12-31") String endDate,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<TSign> info = signService.findSign(startDate, endDate,pageNum,pageSize);
        PageBean<TSign> pageBean = new PageBean<>();
        pageBean.setTotal(info.getTotal());
        pageBean.setRows(info.getList());
        return pageBean;
    }

    //判断当天是否已打卡
    @ResponseBody
    @RequestMapping("/decideSign")
    public Map<String, Integer> decideSign(Integer uid, String nowDate) {
        Map<String, Integer> map = new HashMap<>();
        if (signService.isSign(uid)) {
            //注意:此处的0和1都是魔法值
            map.put("code", 1);
        } else {
            map.put("code", 0);
        }
        return map;
    }

    @RequestMapping("/addSign")
    @ResponseBody
    public Map<String, Integer> addSign(Integer uid){
        Map<String, Integer> map = new HashMap<>();
        if (signService.addSign(uid)){
            map.put("code",1);
        }else{
            map.put("code",0);
        }
        return map;
    }

    @RequestMapping("/showChart")
    public String showChart(){
        return "sign/signCharts";
    }

    //[{day:'03月01日',num:2},{day:'03月03日',num:4}]
    @ResponseBody
    @RequestMapping("/loadSignChart")
    public List<SignChart> loadSignChart(@RequestParam(defaultValue = "2019-01-01") String beginDay) {
        return signService.loadSignChart(beginDay);
    }
}
