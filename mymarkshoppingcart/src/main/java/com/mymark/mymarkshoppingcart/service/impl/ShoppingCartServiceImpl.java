/**
 * 
 */
package com.mymark.mymarkshoppingcart.service.impl;

import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mymark.mymarkshoppingcart.data.domain.CartLineItem;
import com.mymark.mymarkshoppingcart.data.domain.ShoppingCart;
import com.mymark.mymarkshoppingcart.repository.CartLineItemRepository;
import com.mymark.mymarkshoppingcart.repository.ShoppingCartRepository;
import com.mymark.mymarkshoppingcart.service.ShoppingCartService;
import com.mymark.mymarkshoppingcart.service.ShoppingCartServiceException;

/**
 * @author joseph_sicree
 *
 */
public class ShoppingCartServiceImpl implements ShoppingCartService {

	protected final static Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

	@Autowired
	private ShoppingCartRepository shoppingCartRepo;

	@Autowired
	private CartLineItemRepository lineItemRepo;

	@Override
	public ShoppingCart createCartForCustomer(String customerIdentifier) throws ShoppingCartServiceException {
		log.info("In ShoppingCartService.createCartForCustomer()");

		ShoppingCart cart = null;
		log.info("Creating a new cart for customer " + customerIdentifier);
		cart = new ShoppingCart(customerIdentifier);
		cart = shoppingCartRepo.save(cart);

		return cart;
	}

	@Override
	public ShoppingCart getCart(Long cartId) throws ShoppingCartServiceException {
		log.info("In ShoppingCartService.getCart()");

		ShoppingCart cart = null;
		Optional<ShoppingCart> optCart = shoppingCartRepo.findById(cartId);

		if (optCart.isPresent()) {
			cart = optCart.get();
		}
		return cart;
	}

	@Override
	public ShoppingCart getCartForCustomer(String customerIdentifier) throws ShoppingCartServiceException {

		ShoppingCart cart = null;

		log.info("In ShoppingCartService.getCartForCustomer()");

		log.info("Getting ShoppingCart for Customer: " + customerIdentifier);
		cart = shoppingCartRepo.findByCustomerIdentifier(customerIdentifier);

		return cart;
	}

	@Override
	public void addItemtoCart(Long cartId, String productCode, Double productPrice, Integer amount) throws ShoppingCartServiceException {
		log.info("In ShoppingCartService.addItemtoCart()");

		Optional<ShoppingCart> optCart = shoppingCartRepo.findById(cartId);

		if (optCart.isPresent()) {
			ShoppingCart cart = optCart.get();
			CartLineItem lineItem = null;
			lineItem = lineItemRepo.findByShoppingCartAndProductCode(cart, productCode);
			if (lineItem != null) {
				log.info("Found a lineItem for product and customer");
				lineItem.setQuantity(lineItem.getQuantity() + amount);
				lineItemRepo.save(lineItem);
			} else {
				log.info("Did not find a lineItem for product and customer. Creating new lineItem.");
				lineItem = new CartLineItem(cart, productCode, productPrice, amount);
				cart.addLineItem(lineItem);
				cart = shoppingCartRepo.save(cart);
			}
		}
	}

	@Override
	public CartLineItem getItemFromCart(Long cartId, String productCode) throws ServiceException {
		log.info("In ShoppingCartService.getItemFromCart()");

		CartLineItem lineItem = null;

		Optional<ShoppingCart> optCart = shoppingCartRepo.findById(cartId);

		if (optCart.isPresent()) {
			lineItem = lineItemRepo.findByShoppingCartAndProductCode(optCart.get(), productCode);
			if (lineItem != null) {
				log.info("Found a lineItem for cart and product");
			}
		}
		return lineItem;
	}

	@Override
	public void removeItemFromCart(Long cartId, String productCode, Integer amount) throws ServiceException {
		log.info("In ShoppingCartService.removeItemFromCart()");

		Optional<ShoppingCart> optCart = shoppingCartRepo.findById(cartId);

		CartLineItem lineItem = null;
		if (optCart.isPresent()) {
			ShoppingCart cart = optCart.get();
			lineItem = lineItemRepo.findByShoppingCartAndProductCode(cart, productCode);
			if (lineItem != null) {
				if (amount == null || ((lineItem.getQuantity() - amount) <= 0)) {
					// Remove the item from the cart
					cart.removeLineItem(lineItem);
					cart = shoppingCartRepo.save(cart);
				} else {
					// Remove specified amount from a line item
					lineItem.setQuantity((lineItem.getQuantity() - amount));
					lineItemRepo.save(lineItem);
				}
			} else {
				log.info("Cart does not contain line item");
			}
		} else {
			log.info("No lineItem found for cart and product. Nothing to remove.");
		}
	}

	@Override
	public void removeAllItemsFromCart(Long cartId) throws ServiceException {
		log.info("In ShoppingCartService.removeAllItemsFromCart()");

		Optional<ShoppingCart> optCart = shoppingCartRepo.findById(cartId);

		if (optCart.isPresent()) {
			lineItemRepo.deleteAllCartLineItemsFromShoppingCart(optCart.get());
			log.info("Deleted line items for cart: " + cartId);
		}
	}

	@Override
	public void deleteCart(Long cartId) throws ServiceException {
		log.info("In ShoppingCartService.deleteCart()");

		Optional<ShoppingCart> optCart = shoppingCartRepo.findById(cartId);

		if (optCart.isPresent()) {
			ShoppingCart cart = optCart.get();
			log.info("Deleting cart.");
			shoppingCartRepo.delete(cart);
		}
	}

}
