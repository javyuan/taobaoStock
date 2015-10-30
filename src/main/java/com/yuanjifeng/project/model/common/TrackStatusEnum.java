package com.yuanjifeng.project.model.common;

public enum TrackStatusEnum {
	SubmitAmazonOrder(0,"已提交Amazon订单"),
	AmazonShipped(1,"Amazon已发货"),
	HTWSigned(2,"转运仓库已收货"),
	HTWShipped(3,"转运仓库已发货"),
	Signed(4,"本人已收货");
	
	private int value;
	private String desc;
	
	TrackStatusEnum(int value,String desc){
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
			return SubmitAmazonOrder.getDesc();
		}
		if (value == 1 ) {
			return AmazonShipped.getDesc();
		}
		if (value == 2 ) {
			return HTWSigned.getDesc();
		}
		if (value == 3 ) {
			return HTWShipped.getDesc();
		}
		if (value == 4 ) {
			return Signed.getDesc();
		}
		
		return null;
	}
}
