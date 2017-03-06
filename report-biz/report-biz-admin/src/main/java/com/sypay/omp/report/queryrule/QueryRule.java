package com.sypay.omp.report.queryrule;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面封装对象中规则对象
 * @author lishun
 *
 */
public class QueryRule {
	public static Map<String, String> opMap = new HashMap<String, String>();
	static{
		opMap.put("eq", "=");
		opMap.put("ne", "<>");
		opMap.put("le", "<=");
		opMap.put("lt", "<");
		opMap.put("gt", ">");
		opMap.put("ge", ">=");
		opMap.put("in", " in ");
		opMap.put("ni", " not in");
	}
	
    private String field;
    private String op;
    private String data;
    private String funcBefore;
    private String funcAfter;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public String getOpt() {
		return opMap.get(getOp());
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getFuncBefore() {
		return funcBefore;
	}
	public void setFuncBefore(String funcBefore) {
		this.funcBefore = funcBefore;
	}
	public String getFuncAfter() {
		return funcAfter;
	}
	public void setFuncAfter(String funcAfter) {
		this.funcAfter = funcAfter;
	}
	
}
