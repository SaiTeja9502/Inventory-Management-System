package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.InventoryService;
import com.example.demo.Service.UserService;
import com.example.demo.dto.LoginRequest;
import com.example.demo.exception.RaiseException;
import com.example.demo.model.Category;
import com.example.demo.model.Inventory;
import com.example.demo.model.Message;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.Quotation;
import com.example.demo.model.Retailer;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;

import jakarta.validation.Valid;



@RestController
@CrossOrigin(origins = "http://localhost:3000") 
@RequestMapping("/inventory")
public class InventoryManagementSystemController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	InventoryService inventoryService;
	
	@PostMapping("/addproduct")
	public ResponseEntity<Product> addProduct(@RequestParam String name,@RequestParam String description,@RequestParam String categoryId,@RequestParam String imgURL) throws RaiseException{
		if(name == null || description == null || categoryId == null || imgURL == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.addProduct(name, description, categoryId, imgURL),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getproduct")
	public ResponseEntity<Product> getProduct(@RequestParam String productId) throws RaiseException{
		if(productId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.getProduct(productId),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateproduct")
	public ResponseEntity<Product> updateProduct(@RequestParam String productId, @RequestParam String description, @RequestParam String imgURL) throws RaiseException{
		if(productId == null || description == null || imgURL == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.updateProduct(productId, description, imgURL),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/addcategory")
	public ResponseEntity<Category> addCategory(@RequestParam String name) throws RaiseException{
		if(name == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.addCategory(name),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getcategory")
	public ResponseEntity<Category> getCategory(@RequestParam String categoryId) throws RaiseException{
		if(categoryId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.getCategory(categoryId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/fetchallproducts")
	public ResponseEntity<List<Product>> fetchAllProducts() throws RaiseException{
		return new ResponseEntity<>(inventoryService.fetchAllProducts(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/fetchproductsbycategory")
	public ResponseEntity<List<Product>> fetchProductsByCategoryId(@RequestParam String categoryId) throws RaiseException{
		return new ResponseEntity<>(inventoryService.fetchProductsByCategory(categoryId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/fetchallcategories")
	public ResponseEntity<List<Category>> fetchAllCategories() throws RaiseException{
		return new ResponseEntity<>(inventoryService.fetchAllCategories(),HttpStatus.ACCEPTED);
	}
	
	// Inventory Endpoints
	
	@GetMapping("/fetchinventory")
	public ResponseEntity<List<Inventory>> fetchInventory() throws RaiseException{
		return new ResponseEntity<>(inventoryService.fetchInventory(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/fetchinventorybasedoncategory")
	public ResponseEntity<List<Inventory>> fetchInventoryBasedOnCategory(@RequestParam String categoryName) throws RaiseException{
		return new ResponseEntity<>(inventoryService.fetchInventoryBasedOnCategory(categoryName),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getproductsnotininventory")
	public ResponseEntity<List<Product>> getProductsNotInInventory() throws RaiseException{
		return new ResponseEntity<>(inventoryService.fetchProductsNotInInventory(),HttpStatus.ACCEPTED);
	}
	
	//User for both saving and updating
	@PostMapping("/addproducttoinventory")
	public ResponseEntity<Inventory> addProductToInventory(@RequestParam String productId, @RequestParam Double price, @RequestParam Integer reorderPoint, @RequestParam String units) throws RaiseException{
		if(productId == null || price == null || reorderPoint == null || units == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.addProductToInventory(productId, price, reorderPoint, units),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("deletefrominventory")
	public ResponseEntity<String> deleteFromInventory(@RequestParam String productId) throws RaiseException{
		if(productId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		inventoryService.removeProductFromInventory(productId);
		return new ResponseEntity<>("Successfully deleted", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/purchaseproduct")
	public ResponseEntity<Purchase> purchaseProduct(@RequestParam String productId, @RequestParam Integer quantity) throws RaiseException{
		if(productId == null || quantity == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.purchaseProduct(productId, quantity), HttpStatus.CREATED);
	}
	
	@GetMapping("/viewpurchases")
	public ResponseEntity<List<Purchase>> viewPurchase(){
		return new ResponseEntity<>(inventoryService.viewPurchases(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/viewquotations")
	public ResponseEntity<List<Quotation>> viewQuotations(@RequestParam String purchaseId) throws RaiseException{
		if(purchaseId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.getQuotationsForAPurchase(purchaseId), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getmessagesinventory")
	public ResponseEntity<List<Message>> getMessagesInventory(){
		return new ResponseEntity<>(inventoryService.retrieveMessagesInventory(), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deleteapruchase")
	public ResponseEntity<String> deleteAPurchase(@RequestParam String purchaseId) throws RaiseException{
		if(purchaseId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		inventoryService.deletePurchase(purchaseId);
		return new ResponseEntity<>("Successfully Deleted!", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/acceptquotaion")
	public ResponseEntity<Purchase> acceptQuotation(@RequestParam String purchaseId, @RequestParam String quotationId) throws RaiseException{
		if(purchaseId == null || quotationId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.acceptPurchase(purchaseId, quotationId), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getretailer")
	public ResponseEntity<Retailer> getRetailer(@RequestParam String retailerId) throws RaiseException{
		if(retailerId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.getRetailer(retailerId), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getsupplier")
	public ResponseEntity<Supplier> getSupplier(@RequestParam String supplierId) throws RaiseException{
		if(supplierId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(inventoryService.getSupplier(supplierId), HttpStatus.ACCEPTED);
	}
	@GetMapping("/getallnonverifiedsupplier")
	public ResponseEntity<List<Supplier>> getAllNonVerifiedSupplier() throws RaiseException{
		return new ResponseEntity<>( inventoryService.getNonVerifiedSuppliers() ,HttpStatus.ACCEPTED);
	}
}
	
