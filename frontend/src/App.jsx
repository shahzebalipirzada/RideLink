import { useState } from "react";
import Login from "./pages/Login";
import "./App.css";
import Home from "./pages/Home";
import Messages from "./pages/Messages";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import Signup from "./pages/Signup";


function App() {
  const navigate = useNavigate();
  return (
 
  
      <Routes>
        <Route path="/" element={<Home />} /> 
         <Route path="/messages" element={<Messages onNavigateHome={() => navigate('/') } />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
      </Routes>
      
  
       
  );
}

export default App;
