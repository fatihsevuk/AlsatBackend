package com.alsat.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alsat.domain.CartItem;
import com.alsat.domain.Product;
import com.alsat.domain.ProductRequest;
import com.alsat.domain.ShoppingCart;
import com.alsat.domain.User;
import com.alsat.service.CartItemService;
import com.alsat.service.ProductService;
import com.alsat.service.ShoppingCartService;
import com.alsat.service.UserService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity addItem(@RequestBody HashMap<String, String> mapper, Principal principal) {

		String productId = (String) mapper.get("productId");
		String qty = (String) mapper.get("qty");

		User user = userService.findByUsername(principal.getName());
		Product product = productService.findOne(Long.parseLong(productId));

		if (Integer.parseInt(qty) > product.getInStockNumber()) {
			return new ResponseEntity("Stok yeterli değil.", HttpStatus.BAD_REQUEST);
		}

		if (user != product.getUser()) {

			CartItem cartItem = cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty));

			return new ResponseEntity("Ürün başarıyla sepete eklendi.", HttpStatus.OK);

		} else {
			return new ResponseEntity("Kendi Ürününü Sepete Ekleyemezsin", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping("/getCartItemList")
	public List<CartItem> getCartItemList(Principal principal) {

		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		shoppingCartService.updateShoppingCart(shoppingCart);

		return cartItemList;

	}

	@RequestMapping("/getShoppingCart")
	public ShoppingCart getShoppingCart(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		shoppingCartService.updateShoppingCart(shoppingCart);

		return shoppingCart;
	}

	@RequestMapping(value = "/removeItem", method = RequestMethod.POST)
	public ResponseEntity removeItem(@RequestBody String id) {

		cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)));

		return new ResponseEntity("Ürün sepetten silindi.", HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCartItem", method = RequestMethod.POST)
	public ResponseEntity updateCartItem(@RequestBody HashMap<String, String> mapper) {
		String cartItemId = mapper.get("cartItemId");
		String qty = mapper.get("qty");

		CartItem cartItem = cartItemService.findById(Long.parseLong(cartItemId));
		cartItem.setQty(Integer.parseInt(qty));

		cartItemService.updateCartItem(cartItem);

		return new ResponseEntity("Sepet güncellendi", HttpStatus.OK);
	}

	@RequestMapping("/productRequest")
	public ResponseEntity sendProductRequest(@RequestBody HashMap<String, String> mapper, Principal principal) {

		User customer = userService.findByUsername(principal.getName());

		String productId = (String) mapper.get("productId");
		System.out.println(productId);

		Product product = productService.findOne(Long.parseLong(productId));

		String qty = (String) mapper.get("qty");

		if (Integer.parseInt(qty) > product.getInStockNumber()) {
			return new ResponseEntity("Stok yeterli değil.", HttpStatus.BAD_REQUEST);
		}

		ProductRequest productRequest = new ProductRequest();
		productRequest.setCustomer(customer);
		productRequest.setProductOwner(product.getUser().getId());
		productRequest.setProduct(product);
		cartItemService.sendProductRequest(productRequest);

		return new ResponseEntity("Ürün isteği gönderildi", HttpStatus.OK);
	}

}
