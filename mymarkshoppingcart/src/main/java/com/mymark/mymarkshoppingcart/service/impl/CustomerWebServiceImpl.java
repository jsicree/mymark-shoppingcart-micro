package com.mymark.mymarkshoppingcart.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.mymark.customer.api.CustomerDto;
import com.mymark.mymarkcustomer.api.client.ClientException;
import com.mymark.mymarkcustomer.api.client.CustomerRestClient;
import com.mymark.mymarkshoppingcart.service.CustomerWebService;

public class CustomerWebServiceImpl implements CustomerWebService {

	protected final static Logger log = LoggerFactory.getLogger(CustomerWebServiceImpl.class);

	@Value("${CUSTOMER_MICRO_URL}")
	protected String serviceUrl;
	
	public CustomerWebServiceImpl() {
		log.info("Initializing CustomerWebService. ServiceURL: " + serviceUrl);
	}
	
	@Override
	public CustomerDto lookupCustomerByCustomerIdentifier(String customerIdentifier) {
		CustomerDto dto = null;
		try {
			CustomerRestClient customerClient = new CustomerRestClient(serviceUrl, null, null);
			dto = customerClient.getCustomer(customerIdentifier);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return dto;
	}

}
