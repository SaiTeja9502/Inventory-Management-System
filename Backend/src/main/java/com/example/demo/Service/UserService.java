package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRequest;
import com.example.demo.exception.RaiseException;
import com.example.demo.model.Product;
import com.example.demo.model.Retailer;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;
import com.example.demo.repository.RetailerRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RetailerRepository retailerRepository;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	public User addUser(UserRequest userRequest) throws RaiseException {
		if( userRequest.getUserID() != null && userRepository.findById(userRequest.getUserID()).isPresent())
			throw new RaiseException("User already exists!");
		User user = new User(userRequest.getUserID(), userRequest.getPassword(), User.Role.valueOf(userRequest.getRole()), userRequest.isAuthorized());
		User userObject = userRepository.save(user);
		if(user.getRole().compareTo(User.Role.RETAILER) == 0) {
			Retailer retailer = new Retailer(userObject.getUserID(), userRequest.getName(), userRequest.getContact(), userRequest.getAddress());
			retailerRepository.save(retailer);
		}
		else {
			List<Product> products = new ArrayList<>();
			Supplier supplier = new Supplier(userObject.getUserID(), userRequest.getName(), userRequest.getContact(), products, userRequest.getAddress());
			supplierRepository.save(supplier);
		}
		return userObject;
	}
	
	public List<UserRequest> getAllUsers(){
		List<User> users =  userRepository.findAll();
		List<UserRequest> userrequests = new ArrayList<>();
		for( User user: users) {
			if( user.getRole() == User.Role.RETAILER) {
				Optional<Retailer> retailerOptional = retailerRepository.findById(user.getUserID());
				if( retailerOptional.isPresent()) {
					Retailer retailer = retailerOptional.get();
					UserRequest userRequest = new UserRequest(user.getUserID(), user.getPassword(), user.getRole().toString(), user.isAuthorized(), retailer.getName(), retailer.getContact(), retailer.getAddress()); 
					userrequests.add(userRequest);
				}
			}
			else {
				if( user.getRole() == User.Role.SUPPLIER) {
					Optional<Supplier> supplierOptional = supplierRepository.findById(user.getUserID());
					if( supplierOptional.isPresent()) {
						Supplier supplier = supplierOptional.get();
						UserRequest userRequest = new UserRequest(user.getUserID(), user.getPassword(), user.getRole().toString(), user.isAuthorized(), supplier.getName(), supplier.getContact(), supplier.getAddress());
						userrequests.add(userRequest);
					}
				}
			}		
		}
		return userrequests;
	}
	
	public User updateUser(String userId, String name, String contact) throws RaiseException {
		Optional<User> userOptional = userRepository.findById(userId);
		if( !userOptional.isPresent())
			throw new RaiseException("User with ID " + userId + " cannot be found");
		
		User user = userOptional.get();
		if(user.getRole().compareTo(User.Role.RETAILER) == 0) {
			Retailer retailer = retailerRepository.findById(userId).get();
			retailer.setName(name);
			retailer.setContact(contact);
			retailerRepository.save(retailer);
		}
		else {
			Supplier supplier = supplierRepository.findById(userId).get();
			supplier.setName(name);
			supplier.setContact(contact);
			supplierRepository.save(supplier);
		}
		return user;
	}
	
	public User loginUser(String id, String password) throws RaiseException{
		Optional<User> userOptional = userRepository.findById(id);
		if( !userOptional.isPresent())
			throw new RaiseException("User with ID " + id + " cannot be found");
		User user = userOptional.get();
		if( !user.getPassword().equals(password))
			throw new RaiseException("Password is incorrect");
		if( user.getRole().equals(User.Role.SUPPLIER) && !user.isAuthorized())
			throw new RaiseException("Not yet authorized!");
		return user;	 
	}
	
	public User grantAccess(String requestId) throws RaiseException {
		
	    Optional<User> userOptional = userRepository.findById(requestId);
	    if (!userOptional.isPresent()) {
	        throw new RaiseException("User with ID: " + requestId + " not found!");
	    }
	    
	    User user = userOptional.get();

	    if (!user.getRole().equals(User.Role.SUPPLIER)) {
	        throw new RaiseException("Only Supplier Can be Authorized!");
	    }

	    user.setAuthorized(true);
	    return userRepository.save(user);
	}
	
	public void deleteUser(String requestId) throws RaiseException {
	    Optional<User> userOptional = userRepository.findById(requestId);
	    if (!userOptional.isPresent()) {
	        throw new RaiseException("User with ID: " + requestId + " not found!");
	    }
	    
	    User user = userOptional.get();
		userRepository.delete(user);
	}
}
