package com.mymark.mymarkshoppingcart.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.mymark.mymarkproduct.api.client.ClientException;
import com.mymark.mymarkproduct.api.client.ProductRestClient;
import com.mymark.mymarkshoppingcart.service.ProductWebService;
import com.mymark.product.api.ProductDetailsDto;
import com.mymark.product.api.ProductDto;

public class ProductWebServiceImpl implements ProductWebService {

	protected final static Logger log = LoggerFactory.getLogger(ProductWebServiceImpl.class);

	@Value("${PRODUCT_MICRO_URL}")
	protected String serviceUrl;
	
	public ProductWebServiceImpl() {
		log.info("Initializing ProductWebService. ServiceURL: " + serviceUrl);
	}

	@Override
	public ProductDetailsDto lookupProductByProductCode(String productCode) {

		ProductRestClient productClient = new ProductRestClient(serviceUrl,null,null);
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
