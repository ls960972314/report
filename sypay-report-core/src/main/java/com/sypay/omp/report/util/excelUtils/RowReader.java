package com.sypay.omp.report.util.excelUtils;

import java.util.List;

public class RowReader implements IRowReader{  
	  
	  
    /* 业务逻辑实现方法 
     * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List) 
     */  
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {  
        // TODO Auto-generated method stub  
        System.out.print(curRow+" ");  
        for (int i = 0; i < rowlist.size(); i++) {  
            System.out.print(rowlist.get(i) + " ");  
        }  
        System.out.println();  
    }

	@Override
	public Integer getSum() {
		// TODO Auto-generated method stub
		return null;
	}  
  
} 
