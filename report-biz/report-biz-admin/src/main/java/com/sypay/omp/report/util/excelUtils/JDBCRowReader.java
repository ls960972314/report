package com.sypay.omp.report.util.excelUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.sypay.omp.report.util.JDBCUtils;

public class JDBCRowReader implements IRowReader {
	JDBCUtils jdbcUtils;
	/* 多少条提交一次 */
	private Integer num;
	
	private Integer sum;
	
	public Integer getSum() {
		return sum;
	}

	public JDBCRowReader(JDBCUtils jdbcUtils, Integer num) {
		this.jdbcUtils = jdbcUtils;
		this.num = num;
	}

	/**
	 * 运营EXCEL导入一
	 */
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
		sum = curRow;
		if (curRow != 0) {
			try {
				excelOneInsert(jdbcUtils.preparedStatement, rowlist);
				if (curRow % num == 0) {
					jdbcUtils.comit();
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 运营批量导入excel 一
	 * 
	 * @param preparedStatement
	 * @param da
	 * @param num
	 */
	public void excelOneInsert(PreparedStatement preparedStatement, List<String> rowList) {
		try {
			for (int i = 0; i < rowList.size(); i++) {
				if (rowList.get(i).startsWith("`")) {
					preparedStatement.setString(i+1 , rowList.get(i).substring(1));
				} else {
					preparedStatement.setString(i+1 , rowList.get(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			preparedStatement.addBatch();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
