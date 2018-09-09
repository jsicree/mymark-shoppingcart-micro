package com.mymark.mymarkshoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymark.mymarkshoppingcart.data.domain.ShoppingCart;

/**
 * Repository to manage {@link ShoppingCart} instances.
 * 
 */
@Repository 
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
	
	public ShoppingCart findByCustomerIdentifier(String customerIdentifier);

}
