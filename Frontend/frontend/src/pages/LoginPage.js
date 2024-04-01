// src/pages/LoginPage.js
import React, { useState } from 'react';
import axios from 'axios';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('https://api.example.com/login', {
        username,
        password,
      });

      // Assuming your API returns a token upon successful login
      const token = response.data.token;
      console.log('Login Successful. Token:', token);

      // Add your logic for storing the token or redirecting to another page
    } catch (error) {
      console.error('Login failed:', error.message);
      // Handle error, show error message to the user, etc.
    }
  };

  return (
    <div className="login-container">
      <h1>Login to Your Account</h1>
      <form onSubmit={handleLogin}>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginPage;
