package com.mymark.mymarkshoppingcart.data;

import java.util.Set;

import com.mymark.mymarkshoppingcart.data.domain.CartLineItem;
import com.mymark.mymarkshoppingcart.data.domain.ShoppingCart;
import com.mymark.shoppingcart.api.CartLineItemDto;
import com.mymark.shoppingcart.api.CartLineItemDtoList;
import com.mymark.shoppingcart.api.ShoppingCartDto;


public final class ShoppingCartDTOConverter {

	public static CartLineItemDto toCartLineItemDto(CartLineItem lineItem) {
		CartLineItemDto dto = new CartLineItemDto();

		dto.setId(lineItem.getId());
		dto.setQuantity(lineItem.getQuantity());
		dto.setProductCode(lineItem.getProductCode());
		dto.setLinePrice(lineItem.getProductPrice());
		return dto;
	}
			
	public static CartLineItemDtoList toCartLineItemDtoList(Set<CartLineItem> lineItems) {

		CartLineItemDtoList dtoList = new CartLineItemDtoList();

		if (lineItems != null && !lineItems.isEmpty()) {
			for (CartLineItem i : lineItems) {
				dtoList.getLineItems().add(toCartLineItemDto(i));					
			}				
		}
		
		return dtoList;
	}	

	public static ShoppingCartDto toShoppingCartDto(ShoppingCart cart) {
		ShoppingCartDto dto = new ShoppingCartDto();
		dto.setId(cart.getId());
		dto.setCustomerIdentifier(cart.getCustomerIdentifier());
		CartLineItemDtoList itemList = toCartLineItemDtoList(cart.getLineItems());
		dto.setLineItems(itemList);

		Long totalQuantity = 0L;
		Double totalPrice = 0.0;
		
		for (CartLineItemDto item : itemList.getLineItems()) {
			totalQuantity += item.getQuantity();
			totalPrice += (item.getQuantity() * item.getLinePrice());				
		}
		dto.setTotalPrice(totalPrice.doubleValue());
		dto.setNumLineItems(itemList.getLineItems().size());
		dto.setTotalQuantity(totalQuantity);
		return dto;
	}	
	
}
