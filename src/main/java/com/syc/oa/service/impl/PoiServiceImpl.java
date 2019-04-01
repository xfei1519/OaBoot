package com.syc.oa.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syc.oa.mapper.TUserMapper;
import com.syc.oa.model.TUser;
import com.syc.oa.model.TUserExample;
import com.syc.oa.service.PoiService;
import com.syc.oa.vo.PageBean;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("poiService")
public class PoiServiceImpl implements PoiService {

    @Autowired
    private TUserMapper userMapper;

    //1.先根据username去t_user表中查询对应的记录
    //2.然后把查询到的结果,转储都excel中
    //3.把生成的excel供用户下载
    @Override
    public String exportExcel(String username) {

        //1.先根据username去t_user表中查询对应的记录;
        TUserExample example = new TUserExample();
        example.createCriteria().andUsernameLike("%"+username+"%");
        List<TUser> users = userMapper.selectByExample(example);
        PageBean<TUser> bean = new PageBean<>();
        bean.setTotal(users.size());
        bean.setRows(users);

        //JSON的操作:
        //封装--->把数据库里的数据查询出来,封装到bean中,然后利用各种json工具,比如
        //        JackSON,FastJson,GSon等,生成json字符串,然后传递给前端移动端
        //解析--->前端/移动端有时候,给后台服务器传递的数据是一个json字符串,后台就需要
        //        把这个json字符串进行解析,然后封装到bean中,最后再存储到数据库中

        //2.然后把查询到的结果,转储都excel中
        //[{id:1,username:'xxx',...},{id:2,username:'xxx',...}]
        //JSONObject jsonObject = (JSONObject) JSONObject.toJSON(users);
        //{total:5,rows:[{id:1,username:'xxx',...},{id:2,username:'xxx',...}]}
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(bean);  //fastjson转为json字符串

        //[{id:1,username:'xxx',...},{id:2,username:'xxx',...}]
        JSONArray rows = jsonObject.getJSONArray("rows");
        JSONObject object = rows.getJSONObject(0);

        //存储json中key的集合,id,username...,用来写第一行表头
        List<String> keys = new ArrayList<>();
        for (String key : object.keySet()) {
            keys.add(key);
        }

        //创建excel
        //HSSFWorkbook就是excel文件薄
        HSSFWorkbook book = new HSSFWorkbook();
        //创建文件夹里的具体的sheet文件
        HSSFSheet sheet = book.createSheet();
        //创建第一行
        HSSFRow firstRow = sheet.createRow(0);
        for (int i = 0; i < keys.size(); i++) {
            //得到每一行中的单元格
            HSSFCell cell = firstRow.createCell(i);
            //给单元格填充内容
            cell.setCellValue(keys.get(i));
        }

        //创建第二行及其以后每一行里的内容
        for (int i = 1; i <= rows.size(); i++) {
            JSONObject dataObject = rows.getJSONObject(i - 1);
            //第2行
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < keys.size(); j++) {
                //得到每一行中的单元格
                HSSFCell cell = row.createCell(j);
                //给单元格填充内容
                String key = keys.get(j);
                Object value = dataObject.get(key);
                if (value instanceof Date) {
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
                } else {
                    cell.setCellValue(value.toString());
                }
            }
        }

        //把内存中的excel中进行持久化保存,保存到磁盘中
        FileOutputStream stream;
        try {
            File file = new File("f:/oa/files" + File.separator + System.currentTimeMillis() + ".xls");
            if (!file.exists()) {
                file.createNewFile();
                //stream=new FileOutputStream(file);
                stream = FileUtils.openOutputStream(file);
                book.write(stream);
                stream.flush();
                stream.close();
            }
            return file.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
