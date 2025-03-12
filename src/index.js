// /index.js
import React from 'react';
import ReactDOM from 'react-dom/client'; // Importando de 'react-dom/client'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Usando Routes
import Login from './components/Login'; // Importando o componente de Login
import Register from './components/Register'; // Importando o componente de Registro
import Skins from './components/Skins'; 
import AddSkins from './components/AddSkins';
import { AuthProvider } from './context/AuthContext'; // Importando o AuthProvider

// Criando a raiz do aplicativo
const root = ReactDOM.createRoot(document.getElementById('root')); // Criando a raiz
root.render(
  <AuthProvider>
    <Router>
      <Routes> {/* Usando Routes para definir as rotas */}
        <Route path="/login" element={<Login />} /> {/* Usando element para renderizar o componente */}
        <Route path="/register" element={<Register />} /> {/* Usando element para renderizar o componente */}
        <Route path="/skins" element={<Skins />} /> {/* Usando element para renderizar o componente */}
        <Route path="/addskins" element={<AddSkins/>} /> {/* Usando element para renderizar o componente */}
      </Routes>
    </Router>
  </AuthProvider>
);