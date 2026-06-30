import React from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { FiCalendar, FiClock, FiArrowRight, FiSearch } from 'react-icons/fi';
import SearchBoxComponent from './SearchBoxComponent';

/**
 * SearchForm
 * The main search card. Renders in two visual states:
 *   - Full (explore): shows hero text, vehicle toggle, radius slider, and Find button
 *   - Compact (results): collapses to a row of inputs with an Update button
 *
 * @param {boolean}  showResults        - Whether results are currently displayed
 * @param {string}   date               - Selected date value
 * @param {Function} setDate            - Date setter
 * @param {string}   time               - Selected time value
 * @param {Function} setTime            - Time setter
 * @param {boolean}  hasVehicle         - Whether the user is driving
 * @param {Function} setHasVehicle      - hasVehicle setter
 * @param {number}   radius             - Travel radius in km
 * @param {Function} setRadius          - Radius setter
 * @param {Function} onSourceSelect     - Callback when a source location is chosen
 * @param {Function} onDestinationSelect- Callback when a destination is chosen
 * @param {Function} onFindGroups       - Triggers the group search
 */
const SearchForm = ({
  showResults,
  date, setDate,
  time, setTime,
  hasVehicle, setHasVehicle,
  radius, setRadius,
  onSourceSelect,
  onDestinationSelect,
  onFindGroups,
}) => (
  <motion.div
    layout
    className={`search-card glass-panel ${showResults ? 'is-compact' : ''}`}
    transition={{ duration: 0.5, type: 'spring', bounce: 0.15 }}
  >
    {/* Hero text — hidden when results are shown */}
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

    {/* Core inputs — always visible */}
    <motion.div layout className="inputs-grid">
      <motion.div layout className="input-group">
        <label>Source</label>
        <SearchBoxComponent onLocationSelect={onSourceSelect} />
      </motion.div>

      <motion.div layout className="input-group">
        <label>Destination</label>
        <SearchBoxComponent onLocationSelect={onDestinationSelect} />
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

      {/* Compact Update button — only in results mode */}
      {showResults && (
        <motion.button
          layout
          initial={{ opacity: 0, scale: 0.8 }}
          animate={{ opacity: 1, scale: 1 }}
          className="find-btn compact-btn"
          onClick={onFindGroups}
        >
          <FiSearch /> Update
        </motion.button>
      )}
    </motion.div>

    {/* Extended options — hidden in results mode */}
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
              <span className="checkbox-custom" />
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
            <button className="find-btn" onClick={onFindGroups}>
              Find Groups <FiArrowRight />
            </button>
          </div>
        </motion.div>
      )}
    </AnimatePresence>
  </motion.div>
);

export default SearchForm;