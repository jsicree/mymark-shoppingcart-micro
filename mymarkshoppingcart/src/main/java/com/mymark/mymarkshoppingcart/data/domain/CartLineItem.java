package com.mymark.mymarkshoppingcart.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name="CART_LINE_ITEM")
public class CartLineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CART_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private ShoppingCart shoppingCart;
	
	@Column(name = "PRODUCT_CODE", nullable=false)
	private String productCode;

	@Column(name = "PRODUCT_PRICE", nullable=false)
	private Double productPrice;
	
	@Column(name = "QUANTITY")
	private Integer quantity;

	public CartLineItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartLineItem(Long id, ShoppingCart shoppingCart, String productCode, Double productPrice, Integer quantity) {
		super();
		this.id = id;
		this.shoppingCart = shoppingCart;
		this.productCode = productCode;
		this.productPrice = productPrice;
		this.quantity = quantity;
	}

	public CartLineItem(ShoppingCart shoppingCart, String productCode, Double productPrice, Integer quantity) {
		super();
		this.shoppingCart = shoppingCart;
		this.productCode = productCode;
		this.productPrice = productPrice;
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((productPrice == null) ? 0 : productPrice.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartLineItem other = (CartLineItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (productPrice == null) {
			if (other.productPrice != null)
				return false;
		} else if (!productPrice.equals(other.productPrice))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CartLineItem [id=");
		builder.append(id);
		builder.append(", shoppingCart=");
		builder.append(shoppingCart.getId());
		builder.append(", productCode=");
		builder.append(productCode);
		builder.append(", productPrice=");
		builder.append(productPrice);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}
	
}
