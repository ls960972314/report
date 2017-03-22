package com.report.common.dal.admin.util;

import org.apache.ibatis.session.RowBounds;

public class PageUtil {
    
    public static RowBounds paged(int currentNumber, int pageSize) {
        RowBounds row = new RowBounds(currentNumber * pageSize, pageSize);
        return row;
    }
}
