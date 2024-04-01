import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CartModal from './CartModal';
import {useNavigate} from 'react-router-dom'; 
import './Retailer.css';

const RetailerPage = () => {
    const navigate = useNavigate()
  const [inventory, setInventory] = useState([]);
  const [products, setProducts] = useState([]);
  const [orderHistory, setOrderHistory] = useState([]);
  const [isCartModalOpen, setCartModalOpen] = useState(false);
  const [cartItems, setCartItems] = useState([]);

  const openCartModal = () => {
    setCartModalOpen(true);
  };

  const closeCartModal = () => {
    setCartModalOpen(false);
  };

  const updateCartItems = (newCartItems) => {
    setCartItems(newCartItems);
  };

  useEffect(() => {
    axios.get('http://localhost:8080/inventory/fetchinventory ')
      .then(response => setInventory(response.data))
      .catch(error => console.error('Error fetching inventory:', error));

    axios.get('http://localhost:8080/inventory/fetchallproducts')
      .then(response => setProducts(response.data))
      .catch(error => console.error('Error fetching products:', error));

    axios.get('/api/orders/history')
      .then(response => setOrderHistory(response.data))
      .catch(error => console.error('Error fetching order history:', error));
  }, []);

  const InventoryList = inventory.map((item1) => {
    const matchingItem = products.find((item2) => item2.productID === item1.productID);
    return {
      ...item1,
      ...matchingItem, // Add properties from list2 to the merged item
    };
  });
  console.log({InventoryList})

  const addToCart = (product) => {
    const existingItem = cartItems.find((item) => item.productId === product.productID);

    if (existingItem) {
      setCartItems((prevItems) =>
        prevItems.map((item) =>
          item.productId === product.productID ? { ...item, quantity: item.quantity + 1, totalPrice: product.price * (item.quantity + 1) * 1.1 } : item
        )
      );
    } else {
      setCartItems((prevItems) => [
        ...prevItems,
        {
          productId: product.productID,
          name: product.name,
          price: product.price,
          quantity: 1,
          totalPrice: product.price * 1.1,
        },
      ]);
    }
  };

  const totalCartPrice = cartItems.reduce((total, item) => total + item.totalPrice, 0);
  console.log({totalCartPrice});

  const handleLogOut = () =>{
    navigate('/');
    localStorage.clear();
  }

  return (
    <div>
      <div className="nav-bar">
        <div>
          <h1>Your Retailer App</h1>
        </div>
        <div>
          {/* <a href="/orders" className="nav-link">
            Orders History
          </a> */}
          <a href="#" className="nav-link" onClick={handleLogOut}>
            Logout
          </a>
          <i className="fas fa-shopping-cart action-btn" onClick={openCartModal}>Cart</i>
        </div>
      </div>

      <h2 className="heading">Available Products</h2>
      <CartModal
        isOpen={isCartModalOpen}
        onClose={closeCartModal}
        cartItems={cartItems}
        totalCartPrice={Math.round(totalCartPrice*100)/100}
        updateCartItems={updateCartItems} 
      />
      <div className="product-list">
        {InventoryList.map(item => {
          const cartItem = cartItems.find(cartItem => cartItem.productId === item.productID);
          const quantityInCart = cartItem ? cartItem.quantity : 0;
          const isAddToCartDisabled = quantityInCart >= item.quantity;

          return (
            <div key={item.productID} className="product-card">
              <img src={item.imgURL} alt={item.name} className="product-img" />
              <h3>{item.name}</h3>
              <p>{item.description}</p>
              <p>Price: ${item.price}</p>
              <p>Quantity in Cart: {quantityInCart}</p>
              <p>Available in Inventory:{item.quantity}</p>
              <button
                className="action-btn"
                onClick={() => addToCart(item)}
                disabled={isAddToCartDisabled}
              >
                {isAddToCartDisabled ? 'Out of Stock' : 'Add to Cart'}
              </button>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default RetailerPage;
