package com.mymark.mymarkshoppingcart.service.impl;

import com.mymark.mymarkproduct.api.client.ClientException;
import com.mymark.mymarkproduct.api.client.ProductRestClient;
import com.mymark.mymarkshoppingcart.service.ProductWebService;
import com.mymark.product.api.ProductDetailsDto;
import com.mymark.product.api.ProductDto;

public class ProductWebServiceImpl implements ProductWebService {

	
	@Override
	public ProductDetailsDto lookupProductByProductCode(String productCode) {

		ProductRestClient productClient = new ProductRestClient("http://localhost:8082/api",null,null);
		ProductDetailsDto dto = null;
		try {
			dto = productClient.getProductDetails(productCode);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dto;
	}

}
