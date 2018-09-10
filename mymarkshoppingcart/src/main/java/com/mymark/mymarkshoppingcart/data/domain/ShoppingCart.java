package com.mymark.mymarkshoppingcart.data.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity(name = "SHOPPING_CART")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CUSTOMER_IDENTIFIER", unique=true, nullable=false)
	private String customerIdentifier;

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shoppingCart", orphanRemoval = true)
	@OrderBy("id")
	private Set<CartLineItem> lineItems = new LinkedHashSet<CartLineItem>();

	public ShoppingCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public ShoppingCart(Long id, String customerIdentifier) {
		super();
		this.id = id;
		this.customerIdentifier = customerIdentifier;
	}

    public ShoppingCart(String customerIdentifier) {
		super();
		this.customerIdentifier = customerIdentifier;
	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCustomerIdentifier() {
		return customerIdentifier;
	}

	public void setCustomerIdentifier(String customerIdentifier) {
		this.customerIdentifier = customerIdentifier;
	}

	public Set<CartLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<CartLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public void addLineItem(CartLineItem item) {
        lineItems.add(item);
        item.setShoppingCart(this);
    }
 
    public void removeLineItem(CartLineItem item) {
        lineItems.remove(item);
        item.setShoppingCart(null);
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerIdentifier == null) ? 0 : customerIdentifier.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ShoppingCart other = (ShoppingCart) obj;
		if (customerIdentifier == null) {
			if (other.customerIdentifier != null)
				return false;
		} else if (!customerIdentifier.equals(other.customerIdentifier))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ShoppingCart [id=");
		builder.append(id);
		builder.append(", customerIdentifier=");
		builder.append(customerIdentifier);
		builder.append(", lineItems=");
		builder.append(lineItems);
		builder.append("]");
		return builder.toString();
	}	
	
	
}
