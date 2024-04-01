// src/components/UnauthorizedUserGrid.js
import React, { useState, useEffect } from 'react';
import UnauthorizedUsers from '../../sampledata/unauthorizedusers.json';
import axios from 'axios';

const UnauthorizedUserGrid = () => {
  const [unauthorizedUsers, setUnauthorizedUsers] = useState([]);

  useEffect(() => {
    // Fetch unauthorized users from the backend or your data source
    // Replace this with the actual API endpoint for fetching unauthorized users
    axios.get(' http://localhost:8080/inventory/getallnonverifiedsupplier')
      .then(response => setUnauthorizedUsers(response.data))
      .catch(error => console.error('Error fetching unauthorized users:', error));
      console.log({unauthorizedUsers});
  }, []);

  const handleApproveClick = (username) => {
    // Make an API call to update the approval status
    axios.put(` http://localhost:8080/user/grantaccess?&requestId=${username}`)
      .then(response => {
        // Update the local state with the updated user data
        setUnauthorizedUsers(unauthorizedUsers.map(user => (user.username === username ? response.data : user)));
      })
      .catch(error => console.error('Error updating approval status:', error));
  };

  return (
    <div>
      <h2>Unauthorized Suppliers</h2>
      <div className="card-grid">
        {unauthorizedUsers.map(user => (
          <div key={user.username} className="card">
            <h3>{user.username}</h3>
            <p>Name: {user.name}</p>
            <p>Contact: {user.contact}</p>
            <p>Email: {user.supplierID}</p>
            <button onClick={() => handleApproveClick(user.supplierID)}>Approve</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UnauthorizedUserGrid;
