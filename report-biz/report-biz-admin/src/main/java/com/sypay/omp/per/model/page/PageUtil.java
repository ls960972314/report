package com.sypay.omp.per.model.page;

import org.apache.ibatis.session.RowBounds;

public class PageUtil {
    
    public static RowBounds paged(int currentNumber, int pageSize) {
        RowBounds row = new RowBounds(currentNumber * pageSize, pageSize);
        return row;
    }
}
