import React, { useEffect, useRef, useState } from 'react';
import mapboxgl from 'mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import { useMap } from '../hooks/useMap';
import './Home.css';
import { FiMessageSquare, FiBell, FiSearch, FiMapPin, FiCalendar, FiClock, FiArrowRight, FiPlus } from "react-icons/fi";
import { FaUserFriends, FaTrain, FaShieldAlt } from "react-icons/fa";
import SearchBoxComponent from './SearchBoxComponent';
import { motion, AnimatePresence } from 'framer-motion';
import RouteCard from './RouteCard';
import axios from 'axios'



mapboxgl.accessToken = "pk.eyJ1Ijoic2hhaHplYmFsaSIsImEiOiJjbXE5Y2wzYWgwMXg1MnNzYzluMzh0eDgyIn0.x3OfkUHnSq_FuB9_R0RkLA";

const Home = () => {

  //useMap hook to initialize the map
  const mapContainer = useRef(null);
  useMap(mapContainer);




// My Logic for sending initial search datac


  const [date, setDate] = useState('');
  const [time, setTime] = useState('');
  const [source_data, setSourceData] = useState(null);
  const [destination_data, setDestinationData] = useState(null);
  const [radius, setRadius] = useState(15);
  const [hasVehicle, setHasVehicle] = useState(false);
  const [options, setOptions] = useState([]); // State to hold search results
  // NEW: State to trigger the Framer Motion layout shift
  const [showResults, setShowResults] = useState(false);

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
      role: hasVehicle ? "DRIVER" : "PASSENGER",
      origin:{
        type:"Point",
        coordinates:[source_data?.longitude, source_data?.latitude]
      },
      destination: {
        type:"Point",
        coordinates:[destination_data?.longitude, destination_data?.latitude]
      },
      "departure-time":new Date(`${date}T${time}`).toISOString(),
    };
   // console.log("Searching groups with data:", data);

    alert(JSON.stringify(data, null, 2)); // For debugging: Show the data being sent
    
    // Trigger the compact layout animation
    setShowResults(true);



    fetch(`/ride/search/${radius}`, {    
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
    //.then(response => response.json())
    .then(result => {
      // setOptions(result);
      console.log("Search results:", result);
    })
    .catch(error => {
      console.error("Error searching groups:", error);
    });
  }

  return (
    <div className="home-wrapper">
      <div ref={mapContainer} className="map-container" />
      <div className="map-overlay"></div>

      <div className="ui-container">
        
        <nav className="navbar glass-panel">
          <div className="nav-brand" onClick={() => setShowResults(false)} style={{cursor: 'pointer'}}>RideLink</div>
          <div className="nav-links">
            <a href="#explore" className="active" onClick={() => setShowResults(false)}>Explore</a>
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

        <main className={`main-content ${showResults ? 'results-layout' : 'explore-layout'}`}>
          {/* FRAMER MOTION: layout prop handles the smooth resize automatically */}
          <motion.div 
            layout 
            className={`search-card glass-panel ${showResults ? 'is-compact' : ''}`}
            transition={{ duration: 0.5, type: "spring", bounce: 0.15 }}
          >
            
            {/* FRAMER MOTION: Smoothly hides the headers when search is clicked */}
            <AnimatePresence>
              {!showResults && (
                <motion.div 
                  layout 
                  initial={{ opacity: 1, height: 'auto' }} 
                  exit={{ opacity: 0, height: 0, overflow: 'hidden' }}
                  transition={{ duration: 0.3 }}
                >
                  <h1 className="hero-title">Find your next journey.</h1>
                  <p className="hero-subtitle">
                    Seamlessly discover routes, collaborative trips, and transit groups tailored to your rhythm.
                  </p>
                </motion.div>
              )}
            </AnimatePresence>

            {/* Layout prop smoothly animates children into a row */}
            <motion.div layout className="inputs-grid">
              <motion.div layout className="input-group">
                <label>Source</label>
                <SearchBoxComponent onLocationSelect={handleSourceSelect} />
              </motion.div>

              <motion.div layout className="input-group">
                <label>Destination</label>
                <SearchBoxComponent onLocationSelect={handleDestinationSelect} />
              </motion.div>

              <motion.div layout className="input-group">
                <label>Date</label>
                <div className="input-wrapper">
                  <FiCalendar className="input-icon" color="#64748b" />
                  <input value={date} onChange={(e) => setDate(e.target.value)} type="date" />
                </div>
              </motion.div>

              <motion.div layout className="input-group">
                <label>Time</label>
                <div className="input-wrapper">
                  <FiClock className="input-icon" color="#64748b" />
                  <input value={time} onChange={(e) => setTime(e.target.value)} type="time" />
                </div>
              </motion.div>

              {/* Smaller update button that appears when in compact mode */}
              {showResults && (
                 <motion.button 
                   layout 
                   initial={{ opacity: 0, scale: 0.8 }} 
                   animate={{ opacity: 1, scale: 1 }} 
                   className="find-btn compact-btn" 
                   onClick={handleFindGroups}
                 >
                   <FiSearch /> Update
                 </motion.button>
              )}
            </motion.div>

            {/* FRAMER MOTION: Smoothly hides the extended options */}
            <AnimatePresence>
              {!showResults && (
                <motion.div 
                  layout 
                  initial={{ opacity: 1, height: 'auto' }} 
                  exit={{ opacity: 0, height: 0, overflow: 'hidden' }}
                  transition={{ duration: 0.3 }}
                >
                  <div className="vehicle-option-container">
                    <label className="vehicle-checkbox-label">
                      <input 
                        type="checkbox" 
                        checked={hasVehicle}
                        onChange={(e) => setHasVehicle(e.target.checked)}
                      />
                      <span className="checkbox-custom"></span>
                      I will be driving my own vehicle
                    </label>
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
                        onChange={(e) => setRadius(Number(e.target.value))} 
                        className="radius-slider"
                      />
                    </div>
                    <button className="find-btn" onClick={handleFindGroups}>
                      Find Groups <FiArrowRight />
                    </button>
                  </div>
                </motion.div>
              )}
            </AnimatePresence>
          </motion.div>

          {/* FRAMER MOTION: The results panel sliding up from the bottom */}
          <AnimatePresence>
            {showResults && (
              <motion.div 
                initial={{ opacity: 0, y: 50 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: 20 }}
                transition={{ duration: 0.4, delay: 0.2 }}
                className="display-panel glass-panel large-panel"
              >
                <div className="panel-header">
                  <h2 className="panel-title">Available Travel Groups</h2>
                  <button className="action-accent-btn"><FiPlus /> Create Travel Group</button>
                </div>
                <div className="panel-scroll-content">
                  {
                    options.length == 0 ? (
                      <div className="no-results">
                        <p>No groups found. Try adjusting your search criteria.</p>
                      </div>
                    ) : (
                    
                    options.map((option, index) => (
                      <RouteCard 
                        key={index}
                        source={option.origin}
                        destination={option.destination}
                        dateTime={option.stringify("departure-time")}
                        onJoin={() => console.log(`Joining ${option.origin} ➔ ${option.destination} group`)}
                      />
                    ))
                  )
                  }
                    
                  
                  
                </div>
              </motion.div>
            )}
          </AnimatePresence>

          {/* Hide info cards smoothly when searching */}
          <AnimatePresence>
            {!showResults && (
              <motion.div 
                initial={{ opacity: 1 }} 
                exit={{ opacity: 0, height: 0, overflow: 'hidden' }} 
                className="info-cards-container"
              >
                <div className="info-card glass-panel">
                  <div className="info-icon-wrapper bg-green"><FaUserFriends color="#10b981" size={20} /></div>
                  <div className="info-text"><span className="info-label">ACTIVE COMMUNITIES</span><span className="info-value">1,248 Groups</span></div>
                </div>
                <div className="info-card glass-panel">
                  <div className="info-icon-wrapper bg-blue"><FaTrain color="#3b82f6" size={20} /></div>
                  <div className="info-text"><span className="info-label">REAL-TIME ROUTES</span><span className="info-value">Live Updates</span></div>
                </div>
                <div className="info-card glass-panel">
                  <div className="info-icon-wrapper bg-purple"><FaShieldAlt color="#6366f1" size={20} /></div>
                  <div className="info-text"><span className="info-label">SAFETY RATING</span><span className="info-value">Premium Security</span></div>
                </div>
              </motion.div>
            )}
          </AnimatePresence>

        </main>
      </div>
    </div>
  );
};

export default Home;