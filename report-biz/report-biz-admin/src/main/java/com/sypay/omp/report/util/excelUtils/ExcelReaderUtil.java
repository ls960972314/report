package com.sypay.omp.report.util.excelUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

public class ExcelReaderUtil {  
    //excel2003扩展名  
    public static final String EXCEL03_EXTENSION = ".xls";  
    //excel2007扩展名  
    public static final String EXCEL07_EXTENSION = ".xlsx";  
      
    /** 
     * 读取Excel文件，可能是03也可能是07版本 
     * @param excel03 
     * @param excel07 
     * @param fileName 
     * @throws Exception  
     */  
    public static void readExcel(IRowReader reader,MultipartFile file) throws Exception{
        // 处理excel2003文件  
    	String fileName = file.getOriginalFilename();
        if (fileName.endsWith(EXCEL03_EXTENSION)){  
            Excel2003Reader excel03 = new Excel2003Reader();  
            excel03.setRowReader(reader);  
            excel03.process(file.getInputStream());  
        // 处理excel2007文件  
        } else if (fileName.endsWith(EXCEL07_EXTENSION)){  
            Excel2007Reader excel07 = new Excel2007Reader();  
            excel07.setRowReader(reader);  
            excel07.process(file.getInputStream());  
        } else {  
            throw new  Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");  
        }  
    }
    
}  