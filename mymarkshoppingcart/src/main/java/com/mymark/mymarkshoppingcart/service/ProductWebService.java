package com.mymark.mymarkshoppingcart.service;

import com.mymark.product.api.ProductDetailsDto;

public interface ProductWebService {
	
	ProductDetailsDto lookupProductByProductCode(String productCode);
}
