import React from 'react';
import { motion } from 'framer-motion';
import { FiPlus } from 'react-icons/fi';
import RouteCard from './RouteCard';
import {useState} from 'react';
import AddGroupComponent from './AddGroupComponent';
import { useCreateGroupModal } from '../hooks/useCreateGroupModal';

/**
 * ResultsPanel
 * Animated panel that slides in to display available ride groups.
 *
 * @param {Array}   options   - List of ride group objects from the API
 * @param {boolean} isLoading - Whether a search is in progress
 * @param {string|null} error - Error message to display, if any
 */


const ResultsPanel = ({ options, isLoading, error, onGroupCreated}) => {
  const { isOpen, openModal, closeModal, handleSubmit } = useCreateGroupModal({ onGroupCreated });

  return (
<>

       <AddGroupComponent isOpen={isOpen} onClose={closeModal} onSubmit={handleSubmit} />

  <motion.div
    initial={{ opacity: 0, y: 50 }}
    animate={{ opacity: 1, y: 0 }}
    exit={{ opacity: 0, y: 20 }}
    transition={{ duration: 0.4, delay: 0.2 }}
    className="display-panel glass-panel large-panel"
  >
    <div className="panel-header">
      <h2 className="panel-title">Available Travel Groups</h2>
      <button className="action-accent-btn" onClick={openModal}>
        <FiPlus /> Create Travel Group
      </button>
    </div>

    <div className="panel-scroll-content">
      {isLoading && (
        <div className="no-results">
          <p>Searching for groups…</p>
        </div>
      )}

      {!isLoading && error && (
        <div className="no-results error">
          <p>{error}</p>
        </div>
      )}

      {!isLoading && !error && options.length === 0 && (
        <div className="no-results">
          <p>No groups found. Try adjusting your search criteria or radius.</p>
        </div>
      )}

      {!isLoading && !error && options.map((option, index) => (
        <RouteCard
          key={index}
          source={option.origin}
          destination={option.destination}
          dateTime={option.departureTime}
          onJoin={() => console.log(`Joining ${option.origin} → ${option.destination} group`)}
        />
      ))}
    </div>
  </motion.div>
  </>
);
}
export default ResultsPanel;