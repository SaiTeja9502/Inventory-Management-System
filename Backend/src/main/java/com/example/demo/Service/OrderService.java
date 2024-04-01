package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RaiseException;
import com.example.demo.model.Inventory;
import com.example.demo.model.Message;
import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.Quotation;
import com.example.demo.model.Retailer;
import com.example.demo.model.Supplier;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.MessageRepositoryImpl;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.PurchaseRepositoryImpl;
import com.example.demo.repository.QuotationRepository;
import com.example.demo.repository.QuotationRepositoryImpl;
import com.example.demo.repository.RetailerRepository;
import com.example.demo.repository.SupplierRepository;

@Service
public class OrderService {
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	PurchaseRepositoryImpl purchaseRepositoryImpl; 
	
	@Autowired
	PurchaseRepository purchaseRepository;
	
	@Autowired
	QuotationRepository quotationRepository;
	
	@Autowired
	MessageRepositoryImpl messageRepositoryImpl;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	RetailerRepository retailerRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	QuotationRepositoryImpl quotationRepositoryImpl;
	
	public List<Product> fetchProductsNotInSupplier(String supplierId) {
	    List<Product> productsNotInInventory = new ArrayList<>();
	    List<Product> allProducts = productRepository.findAll();
	    Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        List<Product> supplierProducts = supplierOptional.get().getProducts();
        
	    for (Product product : allProducts) {
	        if ( !supplierProducts.contains(product) ) {
	            productsNotInInventory.add(product);
	        }
	    }

	    return productsNotInInventory;
	}
	
	public Supplier addSupplierProduct(String supplierId, String productId) throws RaiseException {
		Optional<Product> productOptional = productRepository.findById(productId);
		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
		if( !productOptional.isPresent())
			throw new RaiseException("Product doesn't exist!");
		if( supplierOptional.isEmpty())
			throw new RaiseException("Supplier not found");
		Supplier supplier = supplierOptional.get();
		Product product = productOptional.get();
		List<Product> products = supplier.getProducts();
		for (Product prod : products) {
	        if ( prod.equals(product) ) {
	            throw new RaiseException("Cannot add existing product");
	        }
	    }
		products.add(product);
		supplier.setProducts(products);
		return supplierRepository.save(supplier);
	}
	
	public Supplier deleteSupplierProduct(String supplierId, String productId) throws RaiseException {
		Optional<Product> productOptional = productRepository.findById(productId);
		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
		if( !productOptional.isPresent())
			throw new RaiseException("Product doesn't exist!");
		if( supplierOptional.isEmpty())
			throw new RaiseException("Supplier not found");
		if( !supplierOptional.get().getProducts().contains(productOptional.get()))
			throw new RaiseException("Supplier do not supply this product");
		Supplier supplier = supplierOptional.get();
		List<Product> products = supplier.getProducts();
		products.remove(productOptional.get());
		supplier.setProducts(products);
		return supplierRepository.save(supplier);
	}
	
	public List<Purchase> getPurchases(String supplierId) throws RaiseException{
		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
		if( supplierOptional.isEmpty())
			throw new RaiseException("Supplier not found");
		List<Product> products = supplierOptional.get().getProducts();
		List<Purchase> purchases =  purchaseRepositoryImpl.findPurchasesByProducts(products);
		List<Purchase> prchs = new ArrayList<>();
		for(Purchase purchase: purchases) {
			if(purchase.getStatus().compareTo(Purchase.Status.ACCEPTED)!=0) {
				if( quotationRepositoryImpl.findQuotationsByPurchaseId(purchase.getPurchaseID()).isEmpty())
					prchs.add(purchase);
			}
		}
		return prchs;
	}
	
	public List<Quotation> getQuotationBySupplier(String supplierId){
		return quotationRepositoryImpl.findQuotationsBySupplierId(supplierId);
	}
	
	public Quotation getQuotation(String quotationId) throws RaiseException {
		Optional<Quotation> quotationOptional = quotationRepository.findById(quotationId);
		if( quotationOptional.isEmpty())
			throw new RaiseException("Quotation not found");
		return quotationRepository.findById(quotationId).get();
	}
	
	public Quotation updateQuotation(String quotationId, double price) throws RaiseException {
		Optional<Quotation> quotationOptional = quotationRepository.findById(quotationId);
		if( quotationOptional.isEmpty())
			throw new RaiseException("Quotation not found");
		Quotation quotation = quotationOptional.get();
		quotation.setPrice(price);
		return quotationRepository.save(quotation);
	}
	
	public Quotation addQuotation(String purchaseId, String supplierId, double price) throws RaiseException {
		Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
		if( supplierOptional.isEmpty())
			throw new RaiseException("Supplier not found");
		if( purchaseOptional.isEmpty())
			throw new RaiseException("Purchase not found");
		Purchase purchase = purchaseOptional.get();
		purchase.setStatus(Purchase.Status.QUOTATIONS_AVAILABLE);
		purchaseRepository.save(purchase);
		Quotation quotation = new Quotation(purchaseId, supplierId, purchaseOptional.get().getProductID(), price);
		return quotationRepository.save(quotation);
	}
	
	public void deleteQuotation(String quotationId) throws RaiseException {
		Optional<Quotation> quotationOptional = quotationRepository.findById(quotationId);
		if( quotationOptional.isEmpty())
			throw new RaiseException("Quotation not found");
		Optional<Purchase> purchaseOptional = purchaseRepository.findById(quotationOptional.get().getPurchaseID());
		if( purchaseOptional.isEmpty())
			throw new RaiseException("Purchase not found");
		if( purchaseOptional.get().getStatus().equals(Purchase.Status.ACCEPTED))
			throw new RaiseException(" Quotaion accepted by Inventory, cannot be deleted. Contact administrator.");
		quotationRepository.deleteById(quotationId);
	}
	
	public List<Message> getMessagesSupplier(String supplierId){
		return messageRepositoryImpl.findUnreadMessagesForUser(supplierId);
	}
	
	public void markMessageasRead(String messageId) throws RaiseException {
		Optional<Message> messageOptional = messageRepository.findById(messageId);
		if( messageOptional.isEmpty())
			throw new RaiseException("Message not found");
		Message message = messageOptional.get();
		message.setRead(false);
		messageRepository.save(message);
	}
	
	public void orderProduct(String retailerId, String productId, int quantity, String method, String role) throws RaiseException {
		Optional<Inventory> inventoryOptional = inventoryRepository.findById(productId);
		if( inventoryOptional.isEmpty())
			throw new RaiseException("Product not found");
		Optional<Retailer> retailerOptional = retailerRepository.findById(retailerId);
		if( retailerOptional.isEmpty())
			throw new RaiseException("Retailer not found");
		Optional<Product> productOptional = productRepository.findById(productId);
		if( productOptional.isEmpty())
			throw new RaiseException("Retailer not found");
		Product product = productOptional.get();
		Inventory inventory = inventoryOptional.get();
		inventory.setQuantity(inventory.getQuantity() - quantity);
		inventoryRepository.save(inventory);
		if( inventory.getQuantity() < inventory.getReorderpoint()) {
			messageRepository.save(new Message(product.getName() + " is less than Reorder Point!", Message.UserType.INVENTORY, null));
		}
		Order order = new Order(retailerId, inventory.getPrice() * quantity, productId, quantity);
		order = orderRepository.save(order);
		Payment payment = new Payment(order.getOrderID(), inventory.getPrice() * quantity, method, role);
		paymentRepository.save(payment);
	}
	
	public List<Order> getOrders(String retailerId) throws RaiseException{
		Optional<Retailer> retailerOptional = retailerRepository.findById(retailerId);
		if( retailerOptional.isEmpty())
			throw new RaiseException("Retailer not found");
		List<Order> orders = orderRepository.findAll();
		List<Order> orderByRetailerId = new ArrayList<>();
		for( Order order : orders) {
			if( order.getRetailerID().compareTo(retailerId) == 0)
				orderByRetailerId.add(order);
		}
		return orderByRetailerId;
	}

	public Purchase getpurchasebyid(String purchaseId) {
		// TODO Auto-generated method stub
		
		return purchaseRepository.findById(purchaseId).get();
	}
}
