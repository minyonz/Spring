package com.kh.sample01.vo;

public class ProductVo {
	String name;
	int price;
	public ProductVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductVo(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ProductVo [name=" + name + ", price=" + price + "]";
	}
	
	
}
