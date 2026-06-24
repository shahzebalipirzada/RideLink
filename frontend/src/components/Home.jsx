import React, { useEffect, useRef, useState } from 'react';
import mapboxgl from 'mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import './Home.css';
import { FiMessageSquare, FiBell, FiSearch, FiMapPin, FiCalendar, FiClock, FiArrowRight } from "react-icons/fi";
import { FaUserFriends, FaTrain, FaShieldAlt } from "react-icons/fa";
import SearchBoxComponent from './SearchBoxComponent';

// IMPORTANT: Replace this with your actual Mapbox API Token
mapboxgl.accessToken = 'pk.eyJ1Ijoic2hhaHplYmFsaSIsImEiOiJjbXE5Y2wzYWgwMXg1MnNzYzluMzh0eDgyIn0.x3OfkUHnSq_FuB9_R0RkLA';

const Home = () => {
  const mapContainer = useRef(null);
  const map = useRef(null);


 useEffect(() => {
    // 1. If the map already exists, do nothing
    if (map.current) return; 

    // 2. Initialize the map
    map.current = new mapboxgl.Map({
      container: mapContainer.current,
      style: 'mapbox://styles/mapbox/streets-v12', 
      center: [68.8191347, 27.7267609],
      zoom: 13,
      pitch: 45,
    });

    // Optional: Force resize just in case
    map.current.on('load', () => {
      map.current.resize();
    });

  
    // 3. THE FIX: Properly destroy AND nullify the reference
    return () => {
      if (map.current) {
        map.current.remove();
        map.current = null; // This line prevents the invisible map bug
      }
    };
  }, []);


const [date, setDate] = useState('');
const [time, setTime] = useState('');
const [source_data, setSourceData] = useState(null);
const [destination_data, setDestinationData] = useState(null);
const [radius, setRadius] = useState(15);

const handleSourceSelect = (data) => {
  console.log("Source selected:", data);
  setSourceData(data);
  
};

const handleDestinationSelect = (data) => {
  console.log("Destination selected:", data);
  setDestinationData(data);
  
};

const handleFindGroups = () => {
 let data = {
  source: source_data,
  destination: destination_data,
  date: date,
  time: time,
  radius: radius
 }
 
alert("Searching for groups with the following criteria:\n" + JSON.stringify(data, null, 2)); 


useEffect(() => {
  fetch('API Request here', {    
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then(response => response.json())
  .then(result => {
    console.log("Search results:", result);
  })
  .catch(error => {
    console.error("Error searching groups:", error);
  });

}, [data]);
}

  return (
    <div className="home-wrapper">
      {/* Mapbox Background Container */}
      <div ref={mapContainer} className="map-container" />
      
      {/* Optional overlay to soften the map and make UI pop */}
      <div className="map-overlay"></div>

      {/* Main UI Overlay */}
      <div className="ui-container">
        
        {/* Top Navbar */}
        <nav className="navbar glass-panel">
          <div className="nav-brand">RideLink</div>
          <div className="nav-links">
            <a href="#explore" className="active">Explore</a>
            <a href="#trips">Trips</a>
            <a href="#groups">Groups</a>
          </div>
          <div className="nav-actions">
            <button className="icon-btn"><FiMessageSquare /></button>
            <button className="icon-btn"><FiBell /></button>
            <div className="profile-avatar">
              <img src="https://ui-avatars.com/api/?name=SA&background=2563eb&color=fff" alt="Profile" />
            </div>
          </div>
        </nav>

        {/* Center Search Card */}
        <main className="main-content">
          <div className="search-card glass-panel">
            <h1 className="hero-title">Find your next journey.</h1>
            <p className="hero-subtitle">
              Seamlessly discover routes, collaborative trips, and transit groups tailored to your rhythm.
            </p>

            <div className="inputs-grid">
              <div className="input-group">
                <label>Source</label>
              
                  
                  <SearchBoxComponent  onLocationSelect={handleSourceSelect} />
            
              </div>

              <div className="input-group">
                <label>Destination</label>
              
                  
                  <SearchBoxComponent  onLocationSelect={handleDestinationSelect} />
             
              </div>

              <div className="input-group">
                <label>Date</label>
                <div className="input-wrapper">
                  <FiCalendar className="input-icon" color="#64748b" />
                  <input value = {date} onChange={(e) => setDate(e.target.value)} type="date" />
                </div>
              </div>

              <div className="input-group">
                <label>Time</label>
                <div className="input-wrapper">
                  <FiClock className="input-icon" color="#64748b" />
                  <input value = {time} onChange={(e) => setTime(e.target.value)} type="time" />
                </div>
              </div>
            </div>

            <div className="search-actions">
              <div className="slider-group">
                <div className="slider-header">
                  <label>Travel Radius</label>
                  <span className="radius-value">{radius} km</span>
                </div>
                <input 
                  type="range" 
                  min="1" 
                  max="50" 
                  value={radius} 
                  onChange={(e) => setRadius(e.target.value)} 
                  className="radius-slider"
                />
              </div>
              <button className="find-btn" onClick={handleFindGroups}>
                Find Groups <FiArrowRight />
              </button>
            </div>
          </div>

          {/* Bottom Info Cards */}
          <div className="info-cards-container">
            <div className="info-card glass-panel">
              <div className="info-icon-wrapper bg-green">
                <FaUserFriends color="#10b981" size={20} />
              </div>
              <div className="info-text">
                <span className="info-label">ACTIVE COMMUNITIES</span>
                <span className="info-value">1,248 Groups</span>
              </div>
            </div>

            <div className="info-card glass-panel">
              <div className="info-icon-wrapper bg-blue">
                <FaTrain color="#3b82f6" size={20} />
              </div>
              <div className="info-text">
                <span className="info-label">REAL-TIME ROUTES</span>
                <span className="info-value">Live Updates</span>
              </div>
            </div>

            <div className="info-card glass-panel">
              <div className="info-icon-wrapper bg-purple">
                <FaShieldAlt color="#6366f1" size={20} />
              </div>
              <div className="info-text">
                <span className="info-label">SAFETY RATING</span>
                <span className="info-value">Premium Security</span>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default Home;