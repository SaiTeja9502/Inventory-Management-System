// src/pages/AdminPage.js
import React, { useState } from 'react';
import UserGrid from './UserGrid';
import CategoryGrid from './CategoryGrid'; 
import UnauthorizedUserGrid from './UnauthorizedUserGrid';
import {useNavigate} from 'react-router-dom'; 
import ProductGrid from './ProductGrid';
import './AdminPage.css'; 

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState('users');
  const navigate = useNavigate()


  const handleTabChange = (tab) => {
    setActiveTab(tab);
  };


  const handleLogOut = ()=>{
    localStorage.clear();
    navigate('/');
  }

  return (
    <div>
      <h1>Welcome, Admin!</h1>

      {/* Open Add Category Modal Button */}
     

      {/* Add Category Form Modal */}


      <button onClick={handleLogOut}>LogOut</button>

      {/* Render your existing product list here */}

<div className="tab-container">
        <button
          className={activeTab === 'users' ? 'active' : ''}
          onClick={() => handleTabChange('users')}
        >
          Users
        </button>
        <button
          className={activeTab === 'products' ? 'active' : ''}
          onClick={() => handleTabChange('products')}
        >
          Products
        </button>
        <button
          className={activeTab === 'categories' ? 'active' : ''}
          onClick={() => handleTabChange('categories')}
        >
          Categories
        </button>
        <button
          className={activeTab === 'unauthorizedUsers' ? 'active' : ''}
          onClick={() => handleTabChange('unauthorizedUsers')}
        >
          Pending Approval
        </button>
      </div>
      <div className="tab-content">
        {activeTab === 'users' && <UserGrid />}
        {activeTab === 'products' && <ProductGrid />}
        {activeTab === 'categories' && <CategoryGrid />}
        {activeTab === 'unauthorizedUsers' && <UnauthorizedUserGrid />}

      </div>

      {/* Other components */}
    </div>
  );
};

export default AdminPage;
