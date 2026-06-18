import React, { useState } from 'react';
import L from 'leaflet';
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
export default function SearchBox(){
  const [searchQuery, setSearchQuery] = useState('');


  return (
   
    <div className="container">
      <TextField id="outlined-basic" label="Search Location" variant="outlined" />
      <Button variant="contained" color="primary" style={{marginLeft:"10px"}}>Search</Button>
      {/* <input type="text" value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} placeholder="Search for a location..." className="search-input" />
      <button className="search-button">Search</button> */}
    </div>
    
  )

}