package com.qixin.teamwork.biz.wx.model;

/**
 * 消息内容实体类
 * @author wuchao
 * @date 2017年6月14日
 * @time 下午4:11:44
 * @version V0.0.1
 */
public class TemplateParam {

	// 参数名称 
    private String name; 
    // 参数值 
    private String value; 
    // 颜色 
    private String color;
    
    public TemplateParam(String name,String value,String color){ 
        this.name=name; 
        this.value=value; 
        this.color=color; 
    } 
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	} 

    
}
