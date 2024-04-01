// OrderHistoryPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

import './OrderHistoryPage.css'; // Import your CSS file with these styles

const OrderHistoryPage = () => {
  const [orderHistory, setOrderHistory] = useState([]);

  useEffect(() => {
    // Fetch order history data from the backend
    // Replace with actual API endpoint
    axios.get('/api/orders/history')
      .then(response => setOrderHistory(response.data))
      .catch(error => console.error('Error fetching order history:', error));
  }, []);

  return (
    <div className="order-history-page">
      <h2 className="heading">Order History</h2>
      <div className="order-history">
        {orderHistory.map(order => (
          <div key={order.orderId} className="order-card">
            {/* Display order details */}
          </div>
        ))}
      </div>
    </div>
  );
};

export default OrderHistoryPage;
