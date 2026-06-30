import React from 'react';
import { motion } from 'framer-motion';
import { FaUserFriends, FaTrain, FaShieldAlt } from 'react-icons/fa';

const CARDS = [
  {
    icon: <FaUserFriends color="#10b981" size={20} />,
    bgClass: 'bg-green',
    label: 'ACTIVE COMMUNITIES',
    value: '1,248 Groups',
  },
  {
    icon: <FaTrain color="#3b82f6" size={20} />,
    bgClass: 'bg-blue',
    label: 'REAL-TIME ROUTES',
    value: 'Live Updates',
  },
  {
    icon: <FaShieldAlt color="#6366f1" size={20} />,
    bgClass: 'bg-purple',
    label: 'SAFETY RATING',
    value: 'Premium Security',
  },
];

/**
 * InfoCards
 * Three highlight stat cards shown on the explore screen.
 * Fades out smoothly when the results panel appears.
 */
const InfoCards = () => (
  <motion.div
    initial={{ opacity: 1 }}
    exit={{ opacity: 0, height: 0, overflow: 'hidden' }}
    className="info-cards-container"
  >
    {CARDS.map(({ icon, bgClass, label, value }) => (
      <div key={label} className="info-card glass-panel">
        <div className={`info-icon-wrapper ${bgClass}`}>{icon}</div>
        <div className="info-text">
          <span className="info-label">{label}</span>
          <span className="info-value">{value}</span>
        </div>
      </div>
    ))}
  </motion.div>
);

export default InfoCards;