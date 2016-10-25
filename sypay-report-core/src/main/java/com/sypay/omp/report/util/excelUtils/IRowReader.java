package com.sypay.omp.report.util.excelUtils;

import java.util.List;

public interface IRowReader {  
    
	/**
	 * 读取多少条记录
	 * @return
	 */
	public Integer getSum();
	
    /**业务逻辑实现方法 
     * @param sheetIndex 
     * @param curRow 
     * @param rowlist 
     */  
    public  void getRows(int sheetIndex,int curRow, List<String> rowlist);  
}  