// src/components/AddProductModal.js
import React, { useState, useEffect} from 'react';
import axios from 'axios';
import './AddProductModal.css';

const AddProductModal = ({ onAdd, onClose }) => {
    const [reorderpoint, setReorderpoint] = useState('0');
    const [products, setProducts] = useState([]);
    const [units,setUnits] = useState('');
    const [price,setPrice] = useState('0');
    const inputStyle = {
        width: '35%',
        padding: '8px',
        marginBottom: '16px',
        border: '1px solid #ccc',
        borderRadius: '4px',
      };
      const selectStyle = {
        width: '100%',
        padding: '8px',
        marginBottom: '16px',
        border: '1px solid #ccc',
        borderRadius: '4pxx',
      };

      useEffect(() => {
        // Fetch inventory data from the backend
        // Replace with actual API endpoint
          axios.get('http://localhost:8080/inventory/getproductsnotininventory')
          .then(response => setProducts(response.data))
          .catch(error => console.error('Error fetching products:', error));
          console.log(products);
    
         
      }, []);
      const handleAddProduct = (productId,reorderpoint) => {
        // Add the selected product to the inventory with a quantity of 0
        // Replace with actual API endpoint for adding products to inventory
        const api = `http://localhost:8080/inventory/addproducttoinventory?productId=${productId}&price=${price}&reorderPoint=${reorderpoint}&units=${units}`
        
        axios.post(api)
          .then(response => {   
            console.log({productId});
            // Update the inventory state after adding the product
            // Close the modal
          })
          .catch(error => console.error('Error adding product to inventory:', error))
          .finally(() => {
            // Reset editableProductId after the request is complete
            setReorderpoint('');
            setUnits('');
            setPrice('');
          });
          };
  return (
    <div className="modal">
      <div className="modal-content">
        <h2>Add Products to Inventory</h2>
        <div className="product-list">
          {products.map(product => (
            <div key={product.productId} className="product-card">
              <img src={product.imgURL} alt={product.name} />
              <h3>{product.name}</h3>
              <label>Reorder Points</label>
              <input
                    type="reorderpoints"
                    id="reorderpoints"
                    style={inputStyle}
                    name="reorderpoints"
                    value={reorderpoint}
                    onChange={(e)=>setReorderpoint(e.target.value)}
                  />
                   <label>Price</label>
              <input
                    type="price"
                    id="price"
                    style={inputStyle}
                    name="price"
                    value={price}
                    onChange={(e)=>setPrice(e.target.value)}
                  />
                    <label>Units of Measure</label>
                    <select
                      id="units"
                      style={selectStyle}
                      name="units"
                      value={units}
                      onChange={(e)=>setUnits(e.target.value)}
                    >
                      <option value="Kgs">Kgs</option>
                      <option value="Per Piece">Per Piece</option>
                      <option value="Lbs">Lbs</option>
                      <option value="Dozen">Dozen</option>
                      <option value="Liter">Liter</option>
                    </select>
              <button onClick={() => handleAddProduct(product.productID, reorderpoint)}>Add</button>
            </div>
          ))}
        </div>
        <button className="close-btn" onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default AddProductModal;
