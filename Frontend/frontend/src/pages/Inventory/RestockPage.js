// src/components/RestockPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './RestockPage.css';

const RestockPage = () => {
  const [products, setProducts] = useState([]);
  const [allproducts, setAllproducts] = useState([]);
  const [restockQuantities, setRestockQuantities] = useState({});

  useEffect(() => {
    // Fetch product data from the backend
    // Replace with actual API endpoint
    axios.get('http://localhost:8080/inventory/fetchinventory')
      .then(response => setProducts(response.data))
      .catch(error => console.error('Error fetching products:', error));
      console.log({products})
      axios.get(' http://localhost:8080/inventory/fetchallproducts')
      .then(response => setAllproducts(response.data))
      .catch(error => console.error('Error fetching products:', error));
      console.log({allproducts})
  }, []);
  const ActualProducts = products.map((item1) => {
    const matchingItem = allproducts.find((item2) => item2.productID === item1.productID);
    console.log(matchingItem)
    return {
      ...item1,
      ...matchingItem, // Add properties from list2 to the merged item
    };
  });
  console.log({ActualProducts})

  const handleRestockQuantityChange = (productId, quantity) => {
    setRestockQuantities(prevState => ({
      ...prevState,
      [productId]: quantity,
    }));
  };

  const handlePurchase = (productId) => {
    // Add the item to the purchase table
    // Replace with actual API endpoint
    console.log(productId,restockQuantities[productId]);
    const api=`http://localhost:8080/inventory/purchaseproduct?productId=${productId}&quantity=${restockQuantities[productId]}`
    axios.post(api)
      .then(response => {
        console.log('Item restocked successfully:', response.data);
        // Optionally, you can navigate the user to another page or perform other actions
      })
      .catch(error => console.error('Error restocking item:', error));
  };

  return (
    <div className="restock-page">
      <h1>Restock Inventory</h1>
      <div className="restock-list">
        {ActualProducts.map(product => (
          <div key={product.productId} className="restock-card">
            <img src={product.imgURL} alt={product.name} />
            <div className="product-info">
              <h3>{product.name}</h3>
              <p>{product.description}</p>
            </div>
            <div className="quantity-input">
              <input
                type="number"
                placeholder="Quantity"
                value={restockQuantities[product.productID] || ''}
                onChange={(e) => handleRestockQuantityChange(product.productID, e.target.value)}
              />
            </div>
            <button onClick={() => handlePurchase(product.productID)}>Purchase</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RestockPage;
