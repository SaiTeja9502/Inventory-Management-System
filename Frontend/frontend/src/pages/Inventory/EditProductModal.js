// src/components/EditProductModal.js
import React, { useState } from 'react';
import axios from 'axios';

const EditProductModal = ({ product, onClose }) => {
  const [editedProduct, setEditedProduct] = useState({
    // Initialize the state with the existing product details
    productId: product.productID,
    price: product.price,
    reorderpoint: product.reorderpoint,
    units: product.units,
  });

  const handleInputChange = (e) => {
    console.log(editedProduct)
    // Update the state when input fields change
    setEditedProduct({
      ...editedProduct,
      [e.target.name]: e.target.value,
    });
  };

  const handleSaveEdit = (product) => {
    console.log({editedProduct})
    // Make an API call to update the product with the edited details
    const api = `http://localhost:8080/inventory/addproducttoinventory?productId=${editedProduct.productId}&price=${editedProduct.price}&reorderPoint=${editedProduct.reorderpoint}&units=${editedProduct.units}`
    axios.post(api)
      .then(response => {
        // Update the inventory state after editing the product
        // setInventory(response.data);
        // Close the Edit Product Modal
        // setIsEditModalOpen(false);
      })
      .catch(error => console.error('Error editing product:', error));
  };

  return (
    <div className="edit-product-modal">
      <h2>Edit Product</h2>
      <label htmlFor="price">Price:</label>
      <input
        type="text"
        id="price"
        name="price"
        value={editedProduct.price}
        onChange={handleInputChange}
      />
      <label htmlFor="reorderpoint">Reorder Point:</label>
      <input
        type="text"
        id="reorderpoint"
        name="reorderpoint"
        value={editedProduct.reorderpoint}
        onChange={handleInputChange}
      />
      <label htmlFor="units">Units:</label>
      <input
        type="text"
        id="units"
        name="units"
        value={editedProduct.units}
        onChange={handleInputChange}
      />
      <button onClick={()=>handleSaveEdit(product)}>Save</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default EditProductModal;
