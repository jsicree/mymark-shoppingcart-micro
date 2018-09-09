package com.mymark.mymarkshoppingcart.service;

import com.mymark.customer.api.CustomerDto;

public interface CustomerWebService {

	CustomerDto lookupCustomerByCustomerIdentifier(String customerIdentifier);
	
}
