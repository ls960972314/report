package com.report.facade.entity.query;

public class FormatVO {
	
	private int index;
	
	/**
	 * 字体大小
	 */
	private String fontSize;
	
	/**
	 * 加粗
	 */
	private String fontWeight;
	
	/**
	 * 格式化内容
	 */
	private String text;

	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
