package com.syc.oa.controller;

import com.syc.oa.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoiService poiService;

    @RequestMapping("/createPoi")
    public String showCreatePoi() {
        return "poi/poi";
    }

    @RequestMapping("/createExcel")
    public String exportExcel(String username) {
        String fileName = poiService.exportExcel(username);
        System.out.println(fileName);
        if (fileName != null) {
            return "redirect:/doc/downloadDocument?fileName=" + fileName;
        }
        return null;
    }

}
