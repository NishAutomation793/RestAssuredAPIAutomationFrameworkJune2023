package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductFakeStorePojo {

	private Integer id;
	private String title;
	private double price;
	private String description;
	private String image;
	private String category;
	
	public ProductFakeStorePojo(String title, double price, String description, String image, String category) {
		super();
		this.title = title;
		this.price = price;
		this.description = description;
		this.image = image;
		this.category = category;
	}
	
	
	
}
