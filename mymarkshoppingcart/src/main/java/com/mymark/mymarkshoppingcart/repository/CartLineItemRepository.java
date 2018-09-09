package com.mymark.mymarkshoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mymark.mymarkshoppingcart.data.domain.CartLineItem;
import com.mymark.mymarkshoppingcart.data.domain.ShoppingCart;

/**
 * Repository to manage {@link CartLineItem} instances.
 * 
 */
@Repository 
public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {

	CartLineItem findByShoppingCartAndProductCode(ShoppingCart sc, String productCode);
	
	@Transactional
    @Modifying
    @Query("delete from CART_LINE_ITEM i where i.shoppingCart=:c")
    public void deleteAllCartLineItemsFromShoppingCart(@Param("c") ShoppingCart c); 
}
