// PaymentPage.js
import React, { useState } from 'react';
import axios from 'axios';
import './PaymentPage.css';
import {useNavigate} from 'react-router-dom'; 

const PaymentPage = () => {
    const navigate = useNavigate()
  const [paymentType, setPaymentType] = useState('');
  const [cardNumber, setCardNumber] = useState('');
  const [ name, setName] = useState('');
const [ expiry, setExpiry] = useState('');
const [ cvv, setCvv] = useState('');

  // Add more state variables for other payment details

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     // Create a payload with the form data
//     const paymentData = {
//       paymentType,
//       cardNumber,
//       // Add other payment details as needed
//     };

//     try {
//       // Send a POST request to the backend
//       const response = await axios.post(`http://localhost:8080/order/orderproduct?retailerId=${localStorage.getItem('userId')}&productId=${item.productId}&quantity=${item.quantity}&method=${paymentType}&role=${localStorage.getItem('role')}`);

//       // Handle the response from the backend as needed
//       console.log('Payment successful:', response.data);
//     } catch (error) {
//       // Handle errors, e.g., display an error message to the user
//       console.error('Error processing payment:', error.message);
//     }
//   };

  const handlePayment =async()=>{
    try {
        // Retrieve ItemsForPayment from localStorage
        const itemsForPayment = JSON.parse(localStorage.getItem('ItemsForPayment'));
    
        // Check if there are items to process
        if (itemsForPayment && itemsForPayment.length > 0) {
          // Iterate over each item and send a POST request
          for (const item of itemsForPayment) {
            const response = await axios.post(`http://localhost:8080/order/orderproduct?retailerId=${localStorage.getItem('userId')}&productId=${item.productId}&quantity=${item.quantity}&method=${paymentType}&role=${localStorage.getItem('role')}`);
            console.log(response.data);
            // Handle the response as needed
            console.log(`Payment for Product ID ${item.productId} successful!`);
          }
    
          // Clear the ItemsForPayment from localStorage after processing
          localStorage.removeItem('ItemsForPayment');
        } else {
          console.log('No items for payment.');
        }
      } catch (error) {
        console.error('Error processing payment:', error);
      }
    localStorage.removeItem('ItemsForPayment')
    navigate('/retailer')
  }

  return (
    <div className="payment-container">
    <div className="payment-box">
      <h2>Payment Details</h2>
      <form >
        <div>
          <label>Select Payment Type:</label>
          <select onChange={(e) => setPaymentType(e.target.value)} value={paymentType}>
            <option value="">Select...</option>
            <option value="credit">Credit Card</option>
            <option value="debit">Debit Card</option>
          </select>
        </div>

        {paymentType && (
          <div>
            <p>
            <label>Card Number:</label>
            <input
              type="text"
              value={cardNumber}
              onChange={(e) => setCardNumber(e.target.value)}
            />
            </p>
            <p>
              <label>Name on card:</label>
            <input
              type="text"
              onChange={(e) => setName(e.target.value)}
            />
            </p>
            <p>
            <label>Expiry</label>
            <input
              type="text"
              onChange={(e) => setExpiry(e.target.value)}
            />
            </p>
            <p>
            <label>CVV</label>
            <input
              type="text"
              onChange={(e) => setCvv(e.target.value)}
            />
            </p>
             <div className="cart-total">

        <p>Total Price: ${localStorage.getItem("Total")}</p>
      </div>
          </div>
          
          // Add more input fields for other payment details (expiration date, CVV, etc.)
        )}

        <button type="submit" onClick={()=>handlePayment()}>Pay Now</button>
      </form>
      </div>
    </div>
  );
};

export default PaymentPage;
