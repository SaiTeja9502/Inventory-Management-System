package com.example.demo.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RaiseException;
import com.example.demo.model.Category;
import com.example.demo.model.Inventory;
import com.example.demo.model.Message;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.Purchase.Status;
import com.example.demo.model.Quotation;
import com.example.demo.model.Retailer;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CategoryRepositoryImpl;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.MessageRepositoryImpl;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductRepositoryImpl;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.PurchaseRepositoryImpl;
import com.example.demo.repository.QuotationRepository;
import com.example.demo.repository.QuotationRepositoryImpl;
import com.example.demo.repository.RetailerRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.UserRepository;

@Service
public class InventoryService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepositoryImpl productRepositoryImpl;
	
	@Autowired 
	InventoryRepository inventoryRepository;
	
	@Autowired
	PurchaseRepository purchaseRepository;
	
	@Autowired
	PurchaseRepositoryImpl purchaseRepositoryImpl;
	
	@Autowired
	QuotationRepository quotationRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	MessageRepositoryImpl messageRepositoryImpl;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	QuotationRepositoryImpl quotationRepositoryImpl;

	@Autowired
	RetailerRepository retailerRepository;
	
	@Autowired
	CategoryRepositoryImpl categoryRepositoryImpl;
	
	public Product addProduct(String name, String description, String categoryId, String imgURL) throws RaiseException {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		if( !categoryOptional.isPresent())
			throw new RaiseException("Invalid Category!");
		Product product = new Product(null, name, description, categoryId, imgURL);
		return productRepository.save(product);
	}
	
	public Product getProduct(String productId) {
		return productRepository.findById(productId).get();
	}
	
	public Product updateProduct(String productId, String description, String imgURL) throws RaiseException {
		Optional<Product> productOptional = productRepository.findById(productId);
		if( !productOptional.isPresent())
			throw new RaiseException("Product doesn't exist!");
		Product product = productOptional.get();
		product.setDescription(description);
		product.setImgURL(imgURL);
		return productRepository.save(product);
}
	
	public Category addCategory(String name) throws RaiseException {
		Category category = new Category(null, name);
		return categoryRepository.save(category);
	}
	
	public Category getCategory(String categoryId) {
		return categoryRepository.findById(categoryId).get();
	}
	
	public List<Product> fetchAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Product> fetchProductsByCategory(String categoryId) throws RaiseException{
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		if( !categoryOptional.isPresent())
			throw new RaiseException("Invalid Category!");
		return productRepositoryImpl.findByCategory(categoryId);
	}
	
	public List<Category> fetchAllCategories(){
		return categoryRepository.findAll();
	}
	
	// Inventory methods
	public List<Inventory> fetchInventory(){
		return inventoryRepository.findAll();
	}
	
	public List<Inventory> fetchInventoryBasedOnCategory(String categoryName) throws RaiseException {
	    List<Inventory> inventoryProducts = inventoryRepository.findAll();
	    List<Inventory> productsCategory = new ArrayList<>();
	    Category category = categoryRepositoryImpl.findByName(categoryName);

	    // Check if the category is null
	    if (category == null) {
	        return productsCategory; // or throw an exception or handle accordingly
	    }

	    // Fetch all products for the given category in a batch
	    List<Product> categoryProducts = fetchProductsByCategory(category.getCategoryID());

	    // Create a set of category product IDs for faster lookup
	    Set<String> categoryProductIds = categoryProducts.stream()
	            .map(Product::getProductID)
	            .collect(Collectors.toSet());

	    // Filter inventory products based on the category product IDs
	    for (Inventory product : inventoryProducts) {
	        if (categoryProductIds.contains(product.getProductID())) {
	            productsCategory.add(product);
	        }
	    }

	    return productsCategory;
	}

	
	public List<Product> fetchProductsNotInInventory() {
	    List<Product> productsNotInInventory = new ArrayList<>();
	    List<Product> allProducts = productRepository.findAll();

	    for (Product product : allProducts) {
	        Optional<Inventory> inventoryOptional = inventoryRepository.findById(product.getProductID());
	        if (!inventoryOptional.isPresent()) {
	            productsNotInInventory.add(product);
	        }
	    }

	    // Return the list of products not in inventory
	    return productsNotInInventory;
	}
	
	public Inventory addProductToInventory(String productId, double price, int reorderPoint, String units) throws RaiseException {
		Optional<Product> productOptional = productRepository.findById(productId);
		if( !productOptional.isPresent())
			throw new RaiseException("Product doesn't exist!");
		Optional<Inventory> inventoryOptional = inventoryRepository.findById(productId);
		Inventory inventory;
		if( !inventoryOptional.isPresent()) {
			 inventory = new Inventory(productId, new ArrayList<Supplier>(), 0, price, reorderPoint, units);
		}
		else {
			 inventory = inventoryRepository.findById(productId).get();
			 inventory.setPrice(price);
			 inventory.setReorderpoint(reorderPoint);
			 inventory.setUnits(units);
		}
//		Inventory inventory = new Inventory(productId, new ArrayList<Supplier>(), 0, price, reorderPoint, units);
		return inventoryRepository.save(inventory);
	}
	
	public void removeProductFromInventory(String productId) throws RaiseException {
		Optional<Inventory> inventoryOptional = inventoryRepository.findById(productId);
		if( !inventoryOptional.isPresent())
			throw new RaiseException("Product doesnot exist in the inventory!");
		inventoryRepository.deleteById(productId);
	}
	
	public Purchase purchaseProduct(String productId, int quantity) throws RaiseException {
		Optional<Product> productOptional = productRepository.findById(productId);
		if( !productOptional.isPresent())
			throw new RaiseException("Product doesn't exist!");
		Purchase purchase = new Purchase(productOptional.get().getProductID(), Purchase.Status.INITIATED, quantity);
		return purchaseRepository.save(purchase);
	}
	
	public List<Purchase> viewPurchases(){
		return purchaseRepositoryImpl.findByStatusInitiated();
	}
	
	public void deletePurchase(String purchaseId) throws RaiseException {
		Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
		if( purchaseOptional.isEmpty())
			throw new RaiseException("No Purchase found");
		if( purchaseOptional.get().getStatus().equals(Status.ACCEPTED))
			throw new RaiseException("Accepted Purchase Cannot be removed!");
		purchaseRepository.deleteById(purchaseId);
	}
	
	public List<Quotation> getQuotationsForAPurchase(String purchaseId){
		return quotationRepositoryImpl.findQuotationsByPurchaseId(purchaseId);
	}
	
	public Purchase acceptPurchase(String purchaseId, String quotationId) throws RaiseException {
		Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
		Optional<Quotation> quotationOptional = quotationRepository.findById(quotationId);
		if( purchaseOptional.isEmpty())
			throw new RaiseException("No Purchase found");
		if( quotationOptional.isEmpty())
			throw new RaiseException("No Quotation found");
		if( !purchaseOptional.get().getStatus().equals(Status.QUOTATIONS_AVAILABLE))
			throw new RaiseException("Cannot be accepted!");
		if ( !quotationOptional.get().getPurchaseID().equals(purchaseId))
			throw new RaiseException("Wrong Quotation for this pruchase!");
		Purchase purchase = purchaseOptional.get();
		purchase.setStatus(Purchase.Status.ACCEPTED);
		purchase.setAcceptedPrice(quotationOptional.get().getPrice());
		Inventory inventory = inventoryRepository.findById(purchase.getProductID()).get();
		inventory.setQuantity( inventory.getQuantity() + purchase.getQuantity());
		Supplier supplier = supplierRepository.findById(quotationOptional.get().getSupplierID()).get();
		List<Supplier> suppliersList = inventory.getSuppliers();
		suppliersList.add(supplier);
		inventory.setSuppliers(suppliersList);
		inventoryRepository.save(inventory);
		Message message = new Message(" Quotation is accepted!", Message.UserType.SUPPLIER, quotationOptional.get().getSupplierID());
		messageRepository.save(message);
		return purchaseRepository.save(purchase);
	}
	
	public List<Message> retrieveMessagesInventory(){
		return messageRepositoryImpl.findUnreadMessagesForInventory();
		
	}
	
	public Retailer getRetailer(String retailerId) throws RaiseException {
		Optional<Retailer> retailerOptional = retailerRepository.findById(retailerId);
		if( retailerOptional.isEmpty())
			throw new RaiseException("Retailer not found");
		return retailerRepository.findById(retailerId).get();
	}
	
	public Supplier getSupplier(String supplierId) throws RaiseException {
		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
		if( supplierOptional.isEmpty())
			throw new RaiseException("Supplier not found");
		return supplierRepository.findById(supplierId).get();
	}
	public List<Supplier> getNonVerifiedSuppliers(){
		List<User> users = userRepository.findAll();
		List<Supplier> suppliers = new ArrayList<>();
		for(User user: users) {
			if( user.getRole().compareTo(User.Role.SUPPLIER) == 0 && !user.isAuthorized()) {
				Optional<Supplier> supplierOptional = supplierRepository.findById(user.getUserID());
				if( supplierOptional.isPresent())
					suppliers.add(supplierOptional.get());
			}
		}
		return suppliers;
	}
}
