package com.syc.oa.controller;

import com.github.pagehelper.PageInfo;
import com.syc.oa.model.TDoc;
import com.syc.oa.service.DocumentService;
import com.syc.oa.vo.PageBean;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping("/selectDocument")
    public String selectEmployee(){
        return "document/document";
    }

    @RequestMapping(value = "/loadDocument")
    @ResponseBody
    public PageBean<TDoc> getAll(@RequestParam(defaultValue = "") String title,
                                 @RequestParam(defaultValue = "1")Integer pageSize,
                                 @RequestParam(defaultValue = "10")Integer pageNumber){
        PageInfo<TDoc> info= documentService.selectDocument(pageNumber,pageSize,title);
        PageBean<TDoc> pageBean = new PageBean<>();
        pageBean.setRows(info.getList());
        pageBean.setTotal(info.getTotal());
        return pageBean;
    }

    @RequestMapping("/addDocument")
    public String addEmployee(Integer flag) throws IOException {
            return "document/showAddDocument";
    }

    @RequestMapping(value = "/removeDocument", produces = {"application/json;charset=UTF-8"})
    public String removeDocument(Integer flag, Integer id, @RequestParam(value = "ids[]", required = false) Integer[] ids, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        boolean result;
        if (flag == 1) {
            documentService.removeOne(id);
            return "redirect:selectDocument";
        } else {
            result = documentService.removeMore(ids);
        }
        if (result) {
            writer.print("success");
        } else {
            writer.print("error");
        }
        return null;
    }

    //1、保存文件信息到数据库
    //2、保存文件到磁盘中
    @RequestMapping("/saveDocument")
    @ResponseBody
    public Map<String, Integer> saveDocument(Integer uid, TDoc document, MultipartFile file) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        if (!file.isEmpty()){
            //1、接收到上传的文件,保存到文件存储空间(本次使用磁盘)
            String filename = file.getOriginalFilename();
            File destFile = new File(getFileDir(),filename);
            file.transferTo(destFile);
            //2、保存文档信息到数据库中
            document.setFileName(filename);
            if (documentService.saveDocument(document)){
                map.put("code",1);
            }else{
                map.put("code",0);
            }
            return map;
        }else{
            return null;
        }
    }

    private File getFileDir() {
        File realFile = new File(new File("f:/oa"), "files");
        if (!realFile.exists()){
            realFile.mkdirs();
        }
        return realFile;
    }

    @RequestMapping("/downloadDocument")
    public ResponseEntity<byte[]> downloadDocument(String fileName){
        File file = new File(getFileDir(), fileName);
        if (file.exists()){
            try {
                HttpHeaders headers = new HttpHeaders();
                String downloadFileName=new String(file.getName().getBytes("UTF-8"),"iso-8859-1");
                //以附件形式下载文件
                headers.setContentDispositionFormData("attachment",downloadFileName);
                //设置文件类型,以流的形式被下载下来
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
