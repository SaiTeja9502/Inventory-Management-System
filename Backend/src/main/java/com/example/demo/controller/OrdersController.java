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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.OrderService;
import com.example.demo.exception.RaiseException;
import com.example.demo.model.Message;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.Quotation;
import com.example.demo.model.Supplier;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
@RequestMapping("/order")
public class OrdersController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/getproductsnotinsupplier")
	public ResponseEntity<List<Product>> getProductsNotInSupplier(@RequestParam String supplierId) throws RaiseException{
		if( supplierId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.fetchProductsNotInSupplier(supplierId),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/addsupplierproduct")
	public ResponseEntity<Supplier> addSupplierProduct(@RequestParam String supplierId, @RequestParam String productId) throws RaiseException{
		if( supplierId == null || productId == null )
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.addSupplierProduct(supplierId, productId),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deletesupplierproduct")
	public ResponseEntity<Supplier> deleteSupplierProduct(@RequestParam String supplierId, @RequestParam String productId) throws RaiseException{
		if( supplierId == null || productId == null )
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.deleteSupplierProduct(supplierId, productId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getpurchases")
	public ResponseEntity<List<Purchase>> getPurchases(@RequestParam String supplierId) throws RaiseException{
		if( supplierId == null )
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.getPurchases(supplierId), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getquotationbysupplier")
	public ResponseEntity<List<Quotation>> getQuotationBySupplierId(@RequestParam String supplierId) throws RaiseException{
		if ( supplierId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.getQuotationBySupplier(supplierId), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getquotation")
	public ResponseEntity<Quotation> getQuotation(@RequestParam String quotationId) throws RaiseException{
		if ( quotationId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.getQuotation(quotationId), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/addquotationforpurchase")
	public ResponseEntity<Quotation> addQuotationForPurchase(@RequestParam String purchaseId, @RequestParam String supplierId, @RequestParam Double price) throws RaiseException{
		if ( purchaseId == null || supplierId == null || price == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.addQuotation(purchaseId, supplierId, price), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getpurchasebyid")
	public ResponseEntity<Purchase> getPurchaseById(@RequestParam String purchaseId) throws RaiseException{
		if ( purchaseId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.getpurchasebyid(purchaseId), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updatequotation")
	public ResponseEntity<Quotation> updateQuotation(@RequestParam String quotationId, @RequestParam Double price) throws RaiseException{
		if ( quotationId == null || price == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.updateQuotation(quotationId, price), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deletequotationforpurchase")
	public ResponseEntity<String> deleteQuotationForPurchase(@RequestParam String quotationId) throws RaiseException{
		if ( quotationId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		orderService.deleteQuotation(quotationId);
		return new ResponseEntity<>("Successful", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getmessagessupplier")
	public ResponseEntity<List<Message>> getMessages(@RequestParam String supplierId) throws RaiseException{
		if( supplierId == null )
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.getMessagesSupplier(supplierId), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/markmessageread")
	public ResponseEntity<String> markMessageReadSupplier(@RequestParam String messageId) throws RaiseException{
		if( messageId == null )
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		orderService.markMessageasRead(messageId);
		return new ResponseEntity<>("Successful", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/orderproduct")
	public ResponseEntity<String> orderProduct(@RequestParam String retailerId, @RequestParam String productId, @RequestParam Integer quantity, @RequestParam String method, @RequestParam String role) throws RaiseException{
		if( retailerId == null || productId == null || quantity == null || method == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		orderService.orderProduct(retailerId, productId, quantity, method, role);
		return new ResponseEntity<>("Successful", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getorders")
	public ResponseEntity<List<Order>> getOrders(@RequestParam String retailerId) throws RaiseException{
		if( retailerId == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(orderService.getOrders(retailerId), HttpStatus.ACCEPTED);
	}
	
}
