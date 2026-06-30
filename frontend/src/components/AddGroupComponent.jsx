import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { FiCalendar, FiClock, FiX } from 'react-icons/fi';
import SearchBoxComponent from './SearchBoxComponent';
import '../styles/AddGroupComponent.css';

const AddGroupComponent = ({ isOpen, onClose, onSubmit }) => {
  // Local state for the modal's form fields
  const [source, setSource] = useState(null);
  const [destination, setDestination] = useState(null);
  const [date, setDate] = useState('');
  const [time, setTime] = useState('');
  const [hasVehicle, setHasVehicle] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    
    // Validate that locations were actually selected from the dropdown
    if (!source || !destination) {
      alert("Please select a valid source and destination from the dropdown.");
      return;
    }

    // Package the data and send it back up to Home.jsx
    const groupData = {
      source,
      destination,
      date,
      time,
      hasVehicle
    };
    
    onSubmit(groupData);
  };

  return (
    <AnimatePresence>
      {isOpen && (
        <motion.div 
          className="modal-backdrop"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          onClick={onClose} // Clicking the dark background closes the modal
        >
          <motion.div 
            className="modal-view glass-panel form-modal"
            initial={{ y: 50, opacity: 0, scale: 0.95 }}
            animate={{ y: 0, opacity: 1, scale: 1 }}
            exit={{ y: 20, opacity: 0, scale: 0.95 }}
            transition={{ type: 'spring', duration: 0.5, bounce: 0.3 }}
            onClick={(e) => e.stopPropagation()} // Prevents closing when clicking inside the white box
          >
            
            <div className="modal-header-row">
              <h3>Create Travel Group</h3>
              <button type="button" className="modal-close-btn" onClick={onClose}>
                <FiX />
              </button>
            </div>

            <form onSubmit={handleSubmit} className="modal-form">
              
              <div className="form-element">
                <label>Source</label>
                <SearchBoxComponent 
                  type="source" 
                  placeholder="Where are you starting?" 
                  onLocationSelect={setSource} 
                />
              </div>

              <div className="form-element">
                <label>Destination</label>
                <SearchBoxComponent 
                  type="destination" 
                  placeholder="Where are you heading?" 
                  onLocationSelect={setDestination} 
                />
              </div>

              <div className="form-row-split">
                <div className="form-element">
                  <label>Departure Date</label>
                  <div className="modal-input-wrapper">
                    <FiCalendar className="input-icon" color="#64748b" />
                    <input 
                      type="date" 
                      required 
                      value={date} 
                      onChange={(e) => setDate(e.target.value)} 
                    />
                  </div>
                </div>
                
                <div className="form-element">
                  <label>Departure Time</label>
                  <div className="modal-input-wrapper">
                    <FiClock className="input-icon" color="#64748b" />
                    <input 
                      type="time" 
                      required 
                      value={time} 
                      onChange={(e) => setTime(e.target.value)} 
                    />
                  </div>
                </div>
              </div>

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

              <button type="submit" className="modal-submit-action-btn">
                Publish Group Route
              </button>
            </form>

          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default AddGroupComponent;