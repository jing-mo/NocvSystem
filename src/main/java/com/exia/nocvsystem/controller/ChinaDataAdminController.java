package com.exia.nocvsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exia.nocvsystem.entity.NocvData;
import com.exia.nocvsystem.service.IndexService;
import com.exia.nocvsystem.vo.DataView;
import com.exia.nocvsystem.vo.NocvDataView;
import org.apache.ibatis.annotations.Update;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChinaDataAdminController {
    @Autowired
    private IndexService indexService;
    @RequestMapping("/toChinaManager")
    public String toChinaManager(){
        return "admin/chinadatamanager";
    }
    @RequestMapping("/listDataByPage")
    @ResponseBody
    public DataView listDataByPage(NocvDataView nocvDataView){
        //1.创建分页的对象 当前页 每页限制大小
        IPage<NocvData> page=new Page<>(nocvDataView.getPage(), nocvDataView.getLimit());
        //2.模糊查询条件
        QueryWrapper<NocvData> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!(nocvDataView.getName()==null),"name",nocvDataView.getName());
        //3.疫情数据确诊最多的排在上面
        queryWrapper.orderByDesc("value");
        //4.查询数据库
        indexService.page(page,queryWrapper);
        //5.返回数据封装
        DataView dataView=new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }
    @RequestMapping("/china/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        indexService.removeById(id);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除中国地图数据成功！");
        return dataView;
    }
    //新增或修改,有值就是修改，没有值就是新增
    @RequestMapping("/china/AddOrUpdateChina")
    @ResponseBody
    public DataView addChina(NocvDataView nocvDataView){
        UpdateWrapper<NocvData> updateWrapper=new UpdateWrapper<NocvData>();
        updateWrapper.eq("name",nocvDataView.getName());
        NocvData nocvData=new NocvData();
        nocvData.setName(nocvDataView.getName());
        nocvData.setValue(nocvDataView.getValue());
        boolean save=indexService.saveOrUpdate(nocvData,updateWrapper);
        DataView dataView=new DataView();
        if(save){
            dataView.setCode(200);
            dataView.setMsg("新增中国地图数据成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("新增中国地图数据失败！");
        return dataView;
    }
    /*
        Excel的拖拽或点击上传
        1.前台页面发送一个请求，上传文件MutilpartFile
        2.Controller,删除给文件件MutilpartFile参数
        3.POI解析文件，里面数据逐个解析
        4.每一条数据插入数据库
     */

    @RequestMapping(value = "/excelImportChina",method = RequestMethod.POST)
    @ResponseBody
    public DataView excelImportChina(@RequestParam("file")MultipartFile file) throws IOException {
        DataView dataView=new DataView();
        //1.文件不能为空
        if(file.isEmpty()) {
            dataView.setMsg("数据不能为空！");
        }
        //2.POI获取Excel解析数据
        XSSFWorkbook wb=new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet=wb.getSheetAt(0);
        //3.定义一个程序集合，接受文件中的数据
        List<NocvData> list=new ArrayList<>();
        XSSFRow row=null;
        //4.解析数据，装到集合里面
        for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
            //定义实体
            NocvData nocvData=new NocvData();
            //每一行的数据,放到实体类中
            row=sheet.getRow(1);
            //解析数据
            nocvData.setName(row.getCell(0).getStringCellValue());
            nocvData.setValue((int)row.getCell(1).getNumericCellValue());
            //添加list集合
            list.add(nocvData);
        }
        indexService.saveBatch(list);
        dataView.setCode(200);
        dataView.setMsg("Excel插入成功");
        return dataView;
    }
        /*
        导出Excel疫情数据文件
        1.查询数据库
        2.建立Excel对象，封装数据
        3.建立输出流，输出文件
     */
        @RequestMapping("/excelOutPortChina")
        @ResponseBody
        public void excelOutPortChina(HttpServletResponse response) throws Exception {
            //1.查询数据库（查询所有，符合条件的数据项）
            List<NocvData> list = indexService.list();
            //2.建立Excel对象，封装数据
            response.setCharacterEncoding("UTF-8");
            //3.创建Excel对象
            XSSFWorkbook workbook=new XSSFWorkbook();
            //4.创建sheet对象
            XSSFSheet sheet=workbook.createSheet("中国疫情数据sheet1");
            //5.创建表头标题
            XSSFRow xssfRow=sheet.createRow(0);
            xssfRow.createCell(0).setCellValue("城市名称");
            xssfRow.createCell(1).setCellValue("确诊数量");
            //6.遍历数据，封装Excel工作对象
            for(NocvData data:list){
                XSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
                dataRow.createCell(0).setCellValue(data.getName());
                dataRow.createCell(1).setCellValue(data.getValue());
            }
            //7.建立输出流，输出浏览器文件
            OutputStream os=null;
            //8.设置Excel名字,输出类型编码
            response.setContentType("application/octet-stream;chartset=utf8");
            response.setHeader("Content-Disposition","attachment;filename="+new String("中国疫情数据表".getBytes(),"iso-8859-1")+".xlsx");
            //9.输出文件
            os=response.getOutputStream();
            workbook.write(os);
            os.flush();
            //10.关闭输出流
            os.close();
        }

}
