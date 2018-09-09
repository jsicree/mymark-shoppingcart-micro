package com.mymark.mymarkshoppingcart.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mymark.customer.api.CustomerDto;
import com.mymark.mymarkshoppingcart.data.ShoppingCartDTOConverter;
import com.mymark.mymarkshoppingcart.data.domain.ShoppingCart;
import com.mymark.mymarkshoppingcart.service.CustomerWebService;
import com.mymark.mymarkshoppingcart.service.ProductWebService;
import com.mymark.mymarkshoppingcart.service.ShoppingCartService;
import com.mymark.mymarkshoppingcart.service.ShoppingCartServiceException;
import com.mymark.mymarkshoppingcart.ws.ApiException;
import com.mymark.mymarkshoppingcart.ws.ApiMessages;
import com.mymark.product.api.ProductDetailsDto;
import com.mymark.shoppingcart.api.DeleteShoppingCartResponse;
import com.mymark.shoppingcart.api.ShoppingCartDto;
import com.mymark.shoppingcart.api.ShoppingCartRequest;
import com.mymark.shoppingcart.api.ShoppingCartResponse;
import com.mymark.shoppingcart.api.UpdateShoppingCartRequest;

/**
 * Handles requests for the form page examples.
 */
@CrossOrigin
@RestController
@RequestMapping("api")
public class ShoppingCartServiceController {

	@Autowired
	protected ShoppingCartService cartService;

	@Autowired
	protected CustomerWebService customerService;

	@Autowired
	protected ProductWebService productService;
	
	@Autowired
	protected MessageSource messageSource;

	private final static String CART_ADD_OPERATION = "ADD";
	private final static String CART_REMOVE_OPERATION = "REMOVE";
	private final static String CART_REMOVE_ALL_OPERATION = "REMOVE_ALL";
	
	protected final static Logger log = LoggerFactory.getLogger(ShoppingCartServiceController.class);

	public ShoppingCartServiceController() {

	}
	
	@RequestMapping(value = "/shoppingcart/{customerIdentifier}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ShoppingCartResponse> getCartByCustomerIdentifier(
			@PathVariable(required = true) String customerIdentifier) throws ApiException {
		
		ShoppingCartResponse response = new ShoppingCartResponse();
		log.info("In getCartByCustomerIdentifier...");
		
		try {

			CustomerDto c = customerService.lookupCustomerByCustomerIdentifier(customerIdentifier);
			if (c != null) {
				ShoppingCart cart = cartService.getCartForCustomer(customerIdentifier);				
				ShoppingCartDto dto = ShoppingCartDTOConverter.toShoppingCartDto(cart);
				response.setCart(dto);
			}
						
		} catch (ShoppingCartServiceException e) {
			log.error("ShoppingCartServiceException thrown while getting shopping cart.", e);
			e.printStackTrace();
			throw new ApiException(ApiMessages.SERVICE_EXCEPTION_MSG, e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}		

	@RequestMapping(value = "/shoppingcart", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ShoppingCartResponse> creeatCart(
			@RequestBody ShoppingCartRequest request) throws ApiException {
		
		ShoppingCartResponse response = new ShoppingCartResponse();
		log.info("In createCart...");
		
		try {
			CustomerDto c = customerService.lookupCustomerByCustomerIdentifier(request.getCustomerIdentifier());
			if (c != null) {
				
				ShoppingCart cart = cartService.getCartForCustomer(c.getIdentifier());
				if (cart == null) {
					log.info("No cart found for customer. Creating cart.");
					cart = cartService.createCartForCustomer(c.getIdentifier());
				} {
					log.info("Cart already exist for customer. Not creating cart.");
				}
				ShoppingCartDto dto = ShoppingCartDTOConverter.toShoppingCartDto(cart);
				response.setCart(dto);
			} else {
				log.info("Customer " + request.getCustomerIdentifier() + " not found. No cart creted.");
			}
						
		} catch (ShoppingCartServiceException e) {
			log.error("ServiceException thrown while updating creating cart.", e);
			e.printStackTrace();
			throw new ApiException(ApiMessages.SERVICE_EXCEPTION_MSG, e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}		
	
	@RequestMapping(value = "/shoppingcart", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<ShoppingCartResponse> updateCart(
			@RequestBody UpdateShoppingCartRequest request) throws ApiException {
		
		ShoppingCartResponse response = new ShoppingCartResponse();
		log.info("In updateCart...");
		
		try {			
			CustomerDto c = customerService.lookupCustomerByCustomerIdentifier(request.getCustomerIdentifier());
			if (c != null) {
				ShoppingCart cart = cartService.getCartForCustomer(c.getIdentifier());				
				if (CART_ADD_OPERATION.equalsIgnoreCase(request.getOperation().name())) {
					ProductDetailsDto p = productService.lookupProductByProductCode(request.getProductCode());
					cartService.addItemtoCart(cart.getId(), p.getProductCode(), p.getPrice(), request.getQuantity());					
				} else if (CART_REMOVE_OPERATION.equalsIgnoreCase(request.getOperation().name())) {
					ProductDetailsDto p = productService.lookupProductByProductCode(request.getProductCode());
					cartService.removeItemFromCart(cart.getId(), p.getProductCode(), request.getQuantity());					
				} else if (CART_REMOVE_ALL_OPERATION.equalsIgnoreCase(request.getOperation().name())) {
					cartService.removeAllItemsFromCart(cart.getId());										
				}
				
				cart = cartService.getCartForCustomer(c.getIdentifier());				
				ShoppingCartDto dto = ShoppingCartDTOConverter.toShoppingCartDto(cart);
				response.setCart(dto);
			}
						
		} catch (ShoppingCartServiceException e) {
			log.error("ServiceException thrown while updating shopping cart.", e);
			e.printStackTrace();
			throw new ApiException(ApiMessages.SERVICE_EXCEPTION_MSG, e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}		
	

	@RequestMapping(value = "/shoppingcart", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<DeleteShoppingCartResponse> deleteCart(
			@RequestBody ShoppingCartRequest request) throws ApiException {
		
		DeleteShoppingCartResponse response = new DeleteShoppingCartResponse();
		log.info("In deleteCart...");
		
		try {			
			CustomerDto c = customerService.lookupCustomerByCustomerIdentifier(request.getCustomerIdentifier());
			if (c != null) {
				
				ShoppingCart cart = cartService.getCartForCustomer(request.getCustomerIdentifier());
				if (cart != null) {
					log.info("Found cart for customer. Deleting. ");
					cartService.deleteCart(cart.getId());					
				} else {
					log.info("No cart found for " + request.getCustomerIdentifier() + ". Nothing to delete.");					
				}
				
				response.setCustomerIdentifier(c.getIdentifier());
			}
						
		} catch (ShoppingCartServiceException e) {
			log.error("ServiceException thrown while updating shopping cart.", e);
			e.printStackTrace();
			throw new ApiException(ApiMessages.SERVICE_EXCEPTION_MSG, e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}		
	
}