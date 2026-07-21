import { useState } from "react";
import Login from "./pages/Login";
import "./App.css";
import Home from "./pages/Home";
import Messages from "./pages/Messages";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile";
import Groups from "./pages/Groups";
import MyGroups from "./pages/MyGroups";


function App() {
  const navigate = useNavigate();
  return (
 
  
      <Routes>
        <Route path="/" element={<Home />} /> 
         <Route path="/messages" element={<Messages onNavigateHome={() => navigate('/') } />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route path="/Profile" element={<Profile />} />
          <Route path="/Groups" element={<Groups />} />
          <Route path="/mygroups" element={<MyGroups />}/>
      </Routes>
      
  
       
  );
}

export default App;
