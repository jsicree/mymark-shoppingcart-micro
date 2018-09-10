package test.com.mymark.mymarkshoppingcart.api.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymark.mymarkshoppingcart.api.client.ClientException;
import com.mymark.mymarkshoppingcart.api.client.ShoppingCartRestClient;
import com.mymark.shoppingcart.api.ShoppingCartDto;

@RunWith(JUnit4.class)
public class ShoppingCartRestClientTest {

	private static ShoppingCartRestClient client;

	protected final static Logger log = LoggerFactory.getLogger(ShoppingCartRestClientTest.class);

	public static String SERVICE_URL = "http://localhost:8083/api";
	public static String USERNAME = "appuser";
	public static String PASSWORD = "password";

	public ShoppingCartRestClientTest() {
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	public static void setup() {
		client = new ShoppingCartRestClient(SERVICE_URL, USERNAME, PASSWORD);
	}

	@Test
	public void createAddRemoveCart() {
		String custIdentifier = "A-SAMPLE-CUST-IDENTIFIER";
		String productCode = "PROD-001";
		String anotherProductCode = "PROD-002";
		
		try {
			log.info("Creating cart for user: " + custIdentifier);
			ShoppingCartDto cartDto = client.createShoppingCart(custIdentifier);
			org.junit.Assert.assertNotNull("Create cart returned null", cartDto);
			log.info("Cart ID: " + cartDto.getId());
			log.info("Cart CustId: " + cartDto.getCustomerIdentifier());
			log.info("Cart # Items: " + cartDto.getNumLineItems());
			log.info("Cart Qty: " + cartDto.getTotalQuantity());
			log.info("Cart Total: " + cartDto.getTotalPrice());

			log.info("\nAdding 1 PROD-001 to cart");
			cartDto = client.addItemToShoppingCart(custIdentifier, productCode, 1);
			log.info("Cart ID: " + cartDto.getId());
			log.info("Cart CustId: " + cartDto.getCustomerIdentifier());
			log.info("Cart # Items: " + cartDto.getNumLineItems());
			log.info("Cart Qty: " + cartDto.getTotalQuantity());
			log.info("Cart Total: " + cartDto.getTotalPrice());
			
			log.info("\nAdding 2 PROD-002 to cart");
			cartDto = client.addItemToShoppingCart(custIdentifier, anotherProductCode, 2);
			log.info("Cart ID: " + cartDto.getId());
			log.info("Cart CustId: " + cartDto.getCustomerIdentifier());
			log.info("Cart # Items: " + cartDto.getNumLineItems());
			log.info("Cart Qty: " + cartDto.getTotalQuantity());
			log.info("Cart Total: " + cartDto.getTotalPrice());

			log.info("\nAdding 1 PROD-001 to cart");
			cartDto = client.addItemToShoppingCart(custIdentifier, productCode, 1);
			log.info("Cart ID: " + cartDto.getId());
			log.info("Cart CustId: " + cartDto.getCustomerIdentifier());
			log.info("Cart # Items: " + cartDto.getNumLineItems());
			log.info("Cart Qty: " + cartDto.getTotalQuantity());
			log.info("Cart Total: " + cartDto.getTotalPrice());

			log.info("\nRemoving 1 PROD-002 from cart");
			cartDto = client.removeItemFromShoppingCart(custIdentifier, anotherProductCode, 1);
			log.info("Cart ID: " + cartDto.getId());
			log.info("Cart CustId: " + cartDto.getCustomerIdentifier());
			log.info("Cart # Items: " + cartDto.getNumLineItems());
			log.info("Cart Qty: " + cartDto.getTotalQuantity());
			log.info("Cart Total: " + cartDto.getTotalPrice());
			

			log.info("\nRemoving 1 PROD-002 from cart");
			cartDto = client.removeItemFromShoppingCart(custIdentifier, anotherProductCode, 1);
			log.info("Cart ID: " + cartDto.getId());
			log.info("Cart CustId: " + cartDto.getCustomerIdentifier());
			log.info("Cart # Items: " + cartDto.getNumLineItems());
			log.info("Cart Qty: " + cartDto.getTotalQuantity());
			log.info("Cart Total: " + cartDto.getTotalPrice());
			
			log.info("\nDeleting cart for user: " + custIdentifier);
			String retVal = client.deleteShoppingCart(custIdentifier);
			org.junit.Assert.assertEquals("Delete op did not return expected customerIdentifier", custIdentifier, retVal);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@AfterClass
	public static void after() {
	}

}
