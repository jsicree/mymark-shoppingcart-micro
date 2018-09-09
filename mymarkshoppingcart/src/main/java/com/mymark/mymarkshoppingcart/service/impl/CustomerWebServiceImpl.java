package com.mymark.mymarkshoppingcart.service.impl;

import com.mymark.customer.api.CustomerDto;
import com.mymark.mymarkcustomer.api.client.ClientException;
import com.mymark.mymarkcustomer.api.client.CustomerRestClient;
import com.mymark.mymarkshoppingcart.service.CustomerWebService;

public class CustomerWebServiceImpl implements CustomerWebService {

	CustomerRestClient customerClient;
	
	@Override
	public CustomerDto lookupCustomerByCustomerIdentifier(String customerIdentifier) {
		CustomerDto dto = null;
		try {
			CustomerRestClient customerClient = new CustomerRestClient("http://localhost:8081/api", null, null);
			dto = customerClient.getCustomer(customerIdentifier);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return dto;
	}

}
