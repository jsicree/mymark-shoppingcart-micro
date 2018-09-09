/**
 * 
 */
package com.mymark.mymarkshoppingcart.service;

import com.mymark.mymarkshoppingcart.data.domain.CartLineItem;
import com.mymark.mymarkshoppingcart.data.domain.ShoppingCart;

/**
 * @author joseph_sicree
 *
 */
public interface ShoppingCartService {
	
	public ShoppingCart createCartForCustomer(String customerIdentifier) throws ShoppingCartServiceException;

	public ShoppingCart getCart(Long cartId) throws ShoppingCartServiceException;

	public ShoppingCart getCartForCustomer(String customerIdentifier) throws ShoppingCartServiceException;

	public void addItemtoCart(Long cartId, String productCode, Double productPrice, Integer amount) throws ShoppingCartServiceException;

	public CartLineItem getItemFromCart(Long cartId, String productCode) throws ShoppingCartServiceException;
	
	public void removeItemFromCart(Long cartId, String productCode, Integer amount) throws ShoppingCartServiceException;

	public void removeAllItemsFromCart(Long cartId) throws ShoppingCartServiceException;

	public void deleteCart(Long cartId) throws ShoppingCartServiceException;


}
