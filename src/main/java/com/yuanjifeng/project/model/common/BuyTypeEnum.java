package com.yuanjifeng.project.model.common;

public enum BuyTypeEnum {
	ZhiYou(0,"直邮"),
	ZhuanYun(1,"转运");
	
	private int value;
	private String desc;
	
	BuyTypeEnum(int value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue(){
		return value;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public static String getDesc(int value){
		if (value == 0 ) {
			return ZhiYou.getDesc();
		}
		if (value == 1 ) {
			return ZhuanYun.getDesc();
		}
		return null;
	}
}
