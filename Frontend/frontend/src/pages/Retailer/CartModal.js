import React, { useEffect, useState } from 'react';
import './CartModal.css';
import { Link } from 'react-router-dom';

const CartModal = ({ isOpen, onClose, cartItems, totalCartPrice, updateCartItems }) => {
  const [localCartItems, setLocalCartItems] = useState([]);

  // Use useEffect to update localCartItems when cartItems change
  useEffect(() => {
    setLocalCartItems(cartItems);
  }, [cartItems]);

  const handleRemovefromcart = (item) => {
    // Remove the item from localCartItems
    const updatedCart = localCartItems.filter((cartItem) => cartItem.productId !== item.productId);
    setLocalCartItems(updatedCart);

    // Update the parent component's state
    updateCartItems(updatedCart);
  };

  const itemsForPayment = localCartItems.map((item) => ({
    productId: item.productId,
    quantity: item.quantity,
  }));

  return (
    <div className={`cart-modal ${isOpen ? 'open' : ''}`}>
      <div className="modal-content">
        <span className="close" onClick={onClose}>&times;</span>
        <h2>Shopping Cart</h2>
        <ul>
          {localCartItems.length ? localCartItems.map((item, index) => (
            <div className='item' key={index}>
              <div className='itemlist'>
                <p>{item.name}</p>
                <p>Quantity: {item.quantity}</p>
                <p>Price: ${item.price * item.quantity}</p>
                <p><button onClick={() => handleRemovefromcart(item)}>Remove from cart</button></p>
              </div>
            </div>
          )) : (
            <div className='item'>
              <div className='itemlist'>
                <p>No items in the cart</p>
              </div>
            </div>
          )}
        </ul>
        <div className="cart-total">
          <p>Total Price: ${totalCartPrice}</p>
        </div>
        <Link to="/payment">
          <button onClick={() => {
            localStorage.setItem("Total", `${totalCartPrice}`);
            localStorage.setItem("ItemsForPayment", JSON.stringify(itemsForPayment));
          }}>Proceed to Payment</button>
        </Link>
      </div>
    </div>
  );
};

export default CartModal;
