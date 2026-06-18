import { useState } from "react";
import Login from "./components/Login";
import "./App.css";
import Home from "./components/Home";
import SearchBox from "./components/SearchBox";
import Maps from "./components/Maps";
function App() {
  return (
    <div
    className="app-container"
    border="2px solid red"
    style={{
      
      display:"flex",
      flexDirection:"row",
      width:"100%",
      height:"100vh"
    }}
    >
      <div style={{
        border:"2px solid blue",
        width:"50%",
        height:"100%"
      }}>
        <Maps />
      </div>

      <div style={{
        border:"2px solid green",
        width:"50%",
        height:"100%"
      }}>
        <SearchBox />
      </div>
    </div>
  );
}

export default App;
