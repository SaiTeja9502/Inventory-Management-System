// PaymentPage.js
import React, { useState } from 'react';
import axios from 'axios';
import {useNavigate} from 'react-router-dom'; 

const PaymentPage = () => {
    const navigate = useNavigate()
  const [paymentType, setPaymentType] = useState('');
  const [cardNumber, setCardNumber] = useState('');
  const [ name, setName] = useState('');
const [ expiry, setExpiry] = useState('');
const [ cvv, setCvv] = useState('');

  // Add more state variables for other payment details

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Create a payload with the form data
    const paymentData = {
      paymentType,
      cardNumber,
      // Add other payment details as needed
    };

    try {
      // Send a POST request to the backend
      const response = await axios.post('YOUR_BACKEND_API_ENDPOINT', paymentData);

      // Handle the response from the backend as needed
      console.log('Payment successful:', response.data);
    } catch (error) {
      // Handle errors, e.g., display an error message to the user
      console.error('Error processing payment:', error.message);
    }
  };

  const handlePayment =async()=>{
    console.log('here');
    try {
      const api = `http://localhost:8080/inventory/acceptquotaion?purchaseId=${quotation.purchaseID}&quotationId=${quotation.quotationID}`;
      const response = await axios.post(api);
      console.log(response.data);
    } catch (error) {
      console.error('Error accepting quotation:', error);
    }
    navigate('/inventory')
  }

  const quotation = JSON.parse(localStorage.getItem('quotation'));


  return (
    <div className="payment-container">
    <div className="payment-box">
      <h2>Payment Details</h2>
      <form onSubmit={handleSubmit}>
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
              placeholder='MM/YYYY'
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

        <button type="submit" onClick={handlePayment}>Pay Now</button>
      </form>
      </div>
    </div>
  );
};

export default PaymentPage;
