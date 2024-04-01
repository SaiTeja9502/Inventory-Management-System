// src/components/QuotationsPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import quotationData from '../../sampledata/quotation.json';
import QuotationModal from './QuotationModal';

const QuotationsPage = () => {
    const[allProducts,setAllProducts] = useState([]);
  const [quotations, setQuotations] = useState([]);
  const [Purchases, setPurchases] = useState([]);
  const [selectedProductQuotations, setSelectedProductQuotations] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    // Fetch quotation data from the backend
    // Replace with actual API endpoint
    axios.get('http://localhost:8080/inventory/viewpurchases')
    .then(response=>setPurchases(response.data))
    .catch(error => console.error('Error fetching quotations:', error));
    axios.get('http://localhost:8080/inventory/fetchallproducts')
    .then(response => setAllProducts(response.data))
    .catch(error => console.error('Error fetching quotations:', error));
    axios.get('http://localhost:8080/inventory/viewquotations')
      .then(response => setQuotations(response.data))
      .catch(error => console.error('Error fetching quotations:', error));
      console.log({Purchases});
  }, []);
  const PurchaseProducts = Purchases.map((item1) => {
    const matchingItem = allProducts.find((item2) => item2.productID === item1.productID);
    return {
      ...item1,
      ...matchingItem, // Add properties from list2 to the merged item
    };
  });
  console.log({PurchaseProducts});

  const handleAcceptQuotation = (quotationID) => {
    // Implement logic to accept the quotation
    // Replace with actual API endpoint
    axios.post(`/api/quotations/${quotationID}/accept`)
      .then(response => {
        console.log('Quotation accepted successfully:', response.data);
        // Optionally, you can refresh the page or perform other actions
      })
      .catch(error => console.error('Error accepting quotation:', error));
  };

  const handleRejectQuotation = (quotationID) => {
    // Implement logic to reject the quotation
    // Replace with actual API endpoint
    axios.post(`/api/quotations/${quotationID}/reject`)
      .then(response => {
        console.log('Quotation rejected successfully:', response.data);
        // Optionally, you can refresh the page or perform other actions
      })
      .catch(error => console.error('Error rejecting quotation:', error));
  };

  const handleViewQuotation = (purchaseID) => {
    const quotationsForProduct = PurchaseProducts.filter(product => product.purchaseID === purchaseID);
    setSelectedProductQuotations(quotationsForProduct);
    console.log({selectedProductQuotations});
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div>
      <h1>Purchases</h1>
      <table className="quotation-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Accepted Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {PurchaseProducts.map(product => (
            <tr key={product.productID}>
              <td>{product.name}</td>
              <td>{product.quantity}</td>
              <td>{product.status}</td>
              <td>{product.acceptedPrice}</td>
              <td>
                {product.status!="ACCEPTED"&&
              <button onClick={() => handleViewQuotation(product.purchaseID)}>View Quotation</button>
                }
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isModalOpen && (
        <QuotationModal product={selectedProductQuotations} onClose={handleCloseModal} />
      )}
    </div>
  );
};

export default QuotationsPage;
