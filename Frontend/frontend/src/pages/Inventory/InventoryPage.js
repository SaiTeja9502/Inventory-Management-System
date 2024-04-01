// src/components/InventoryPage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom
import AddProductModal from './AddProductModal';
import EditProductModal from './EditProductModal';
import './Inventory.css';
import MessagesModal from './MessagesModal';
import {useNavigate} from 'react-router-dom'; 


const InventoryPage = () => {
  const [productCategory, setProductCategory] = useState('');
  const [categories,setCategories]= useState([]);
  const [productsnotin,setProduc]=useState()
  const [inventory, setInventory] = useState([]);
  const [products, setProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [editableProduct, setEditableProduct] = useState(null);
  const [messages, setMessages] = useState([]); // Add state for messages
  const [isMessagesModalOpen, setIsMessagesModalOpen] = useState(false);
  const navigate = useNavigate()

  useEffect(() => {
    // Fetch inventory data from the backend
    // Replace with actual API endpoint
    axios.get(' http://localhost:8080/inventory/fetchallcategories')
    .then(response => setCategories(response.data))
    .catch(error => console.error('Error fetching categories:', error));
    console.log(categories);

    axios.get('http://localhost:8080/inventory/fetchinventory ')
      .then(response => setInventory(response.data))
      .catch(error => console.error('Error fetching inventory:', error));
      console.log(inventory);

      axios.get('http://localhost:8080/inventory/fetchallproducts')
      .then(response => setProducts(response.data))
      .catch(error => console.error('Error fetching products:', error));
      console.log(products);

     
  }, []);

  useEffect(() => {

    axios.get(`http://localhost:8080/inventory/fetchinventorybasedoncategory?categoryName=${productCategory}`)
    .then((res)=>console.log(res.data));

  }, [productCategory]);

  const InventoryList = inventory.map((item1) => {
    const matchingItem = products.find((item2) => item2.productID === item1.productID);
    return {
      ...item1,
      ...matchingItem, // Add properties from list2 to the merged item
    };
  });
  console.log({InventoryList});
  const handleAddProduct = (productId,reorderpoint) => {
    // Add the selected product to the inventory with a quantity of 0
    // Replace with actual API endpoint for adding products to inventory
    axios.post('/api/inventory/add', { productId, quantity: 0 })
      .then(response => {
        // Update the inventory state after adding the product
        setInventory(response.data);
        // Close the modal
        setIsModalOpen(false);
      })
      .catch(error => console.error('Error adding product to inventory:', error));

      setIsModalOpen(false);
  };
  const handleEdit = (productId) => {
    // Find the selected product for editing
    const productToEdit = InventoryList.find(item => item.productID === productId);
    setSelectedProduct(productToEdit);
    setIsEditModalOpen(true);
  };
  const handleDelete=(productId)=>{
    const api = `http://localhost:8080/inventory/deletefrominventory?productId=${productId}`
    axios.delete(api)
    .then(res=>{console.log('deleted successfully')})
    .catch(error=>console.error('Error adding product to inventory:', error))
  }
  const handleCloseModal = () => {
    // Close the modal without adding the product
    setIsModalOpen(false);
  };
  const handleSaveEdit = (editedProduct) => {
    console.log({editableProduct})
    // Make an API call to update the product with the edited details
    // const api = `http://localhost:8080/inventory/addproducttoinventory?productId=${productId}&price=${price}&reorderPoint=${reorderpoint}&units=${units}`
    axios.put(`http://localhost:8080/inventory/addproducttoinventory`, editedProduct)
      .then(response => {
        // Update the inventory state after editing the product
        setInventory(response.data);
        // Close the Edit Product Modal
        setIsEditModalOpen(false);
      })
      .catch(error => console.error('Error editing product:', error));
  };
  const handleOpenMessages = () => {
    // Fetch messages from the backend
    // Replace with actual API endpoint
    axios.get('http://localhost:8080/inventory/getmessagesinventory')
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
      {/* Header Section */}
      <div className="header">
        <button className="notification-btn"  onClick={handleOpenMessages}>Notifications</button>
        {/* <button className="orders-btn">Orders</button> */}
        <button className="logout-btn" onClick={handleLogOut}>Logout</button>
      </div>

      {/* Body Section */}
      <div className="body">
        <h1>Inventory</h1>
        {/* <h2>
        <label >Category:</label>
        <select
                      id="category"
                      name="category"
                      onChange={(e)=>setProductCategory(e.target.value)}
                    >
                    {categories.map((cat)=>{
                        console.log(cat);
                        return(<option value={cat.categoryID}>{cat.name}</option>)
                        
                    })}     
                    </select>
        </h2> */}
        <button onClick={() => setIsModalOpen(true)}>Add Products</button>
        {isModalOpen && (
          <AddProductModal onAdd={handleAddProduct} onClose={handleCloseModal} />
          )}
        <button><Link to="/restock">Restock Inventory</Link></button>
        <button><Link to="/quotations">View Quotations</Link></button>

        {/* Display Inventory Products */}
        <table className="inventory-table">
          <thead>
            <tr>
              <th>Product</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Reorder Point</th>
              <th>Units</th>
              <th>Edit</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {InventoryList.map(item => (
              <tr key={item.productID}>
                <td>{item.name}</td>
                <td>{item.quantity}</td>
                <td>{item.price}</td>
                <td>{item.reorderpoint}</td>
                <td>{item.units}</td>
                <td><button onClick={() => handleEdit(item.productID)}>Edit</button></td>
                <td><button onClick={() => handleDelete(item.productID)}>Delete</button></td>
              </tr>
            ))}
          </tbody>
        </table>
        {selectedProduct && (
        <EditProductModal
          product={selectedProduct}
          onClose={handleCloseModal}
        />
      )}
      {isMessagesModalOpen && (
          <MessagesModal messages={messages} onClose={handleCloseMessagesModal} />
        )}
      </div>
    </div>
  );
};

export default InventoryPage;
