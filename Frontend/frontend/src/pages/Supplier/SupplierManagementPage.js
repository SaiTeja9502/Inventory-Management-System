// src/components/SupplierManagementPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './Supplier.css';
import {useNavigate} from 'react-router-dom'; 
import MessagesModal from './MessagesModal';

const SupplierManagementPage = () => {
  const navigate = useNavigate()
  const [supplierProducts, setSupplierProducts] = useState([]);
  const [allProducts, setAllProducts] = useState([]);
  const [messages, setMessages] = useState([]); // Add state for messages
  const [isMessagesModalOpen, setIsMessagesModalOpen] = useState(false);

  useEffect(() => {
    const fetchSupplierProducts = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/inventory/getsupplier?supplierId=${localStorage.getItem('userId')}`);
        setSupplierProducts(response.data.products || []); // Ensure it's an array or use an empty array if undefined
      } catch (error) {
        console.error('Error fetching supplier products:', error);
      }
    };

    const fetchAllProducts = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/order/getproductsnotinsupplier?supplierId=${localStorage.getItem('userId')}`);
        setAllProducts(response.data || []); // Ensure it's an array or use an empty array if undefined
      } catch (error) {
        console.error('Error fetching all products:', error);
      }
    };

    fetchSupplierProducts();
    fetchAllProducts();
  }, []);

  const handleAddProduct = async (productId) => {
    try {
      const response = await axios.post(`http://localhost:8080/order/addsupplierproduct?supplierId=${localStorage.getItem('userId')}&productId=${productId}`);
      setSupplierProducts(response.data || []); // Ensure it's an array or use an empty array if undefined
    } catch (error) {
      console.error('Error adding product to supplier:', error);
    }
  };

  const handleOpenMessages = () => {
    // Fetch messages from the backend
    // Replace with actual API endpoint
    axios.get(`http://localhost:8080/order/getmessagessupplier?supplierId=${localStorage.getItem('userId')}`)
      .then(response => setMessages(response.data))
      .catch(error => console.error('Error fetching messages:', error));

    console.log({messages});
    setIsMessagesModalOpen(true);
  };

  const handleCloseMessagesModal = () => {
    setIsMessagesModalOpen(false);
  };

  const handleLogOut = ()=>{
    localStorage.clear();
    navigate('/');
  }

  return (
    <div>
            <div className="nav-bar">
        <div>
          <h1>Your Supplier App</h1>
        </div>
        <div>
        <a className="nav-link" onClick={handleOpenMessages}>
           Notifications
          </a>
          <a href="/purchase-orders" className="nav-link">
           Purchase Orders Page
          </a>
          <a href="#" className="nav-link" onClick={handleLogOut}>
            Logout
          </a>
        </div>
      </div>
      <div>
        <div>
          <h2>Supplier's Product List</h2>
          <ul>
            {Array.isArray(supplierProducts) && supplierProducts.map(product => (
              <li key={product.productId}>{product.name}</li>
            ))}
          </ul>
        </div>

        <div>
          <h2>All Products</h2>
          <ul className="product-grid">
            {Array.isArray(allProducts) && allProducts.map(product => (
              <div key={product.productID} className='card'>
                <div>
                  <img src={product.imgURL} alt={product.name} />
                  <p>{product.name}</p>
                  <button onClick={() => handleAddProduct(product.productID)}>Add to Supplier</button>
                </div>
              </div>
            ))}
          </ul>
        </div>
      </div>
      {isMessagesModalOpen && (
          <MessagesModal messages={messages} onClose={handleCloseMessagesModal} />
        )}
    </div>
  );
};

export default SupplierManagementPage;
