import React from 'react';
import { FiMessageSquare, FiBell } from 'react-icons/fi';

/**
 * Navbar
 * Top navigation bar with branding, links, and user actions.
 *
 * @param {Function} onLogoClick - Resets the search/results view when logo is clicked
 */
const Navbar = ({ onLogoClick }) => (
  <nav className="navbar glass-panel">
    <div className="nav-brand" onClick={onLogoClick} style={{ cursor: 'pointer' }}>
      RideLink
    </div>

    <div className="nav-links">
      <a href="#explore" className="active" onClick={onLogoClick}>Explore</a>
      <a href="#messages">Messages</a>
      <a href="#groups">Groups</a>
    </div>

    <div className="nav-actions">
      <button className="icon-btn" aria-label="Messages"><FiMessageSquare /></button>
      <button className="icon-btn" aria-label="Notifications"><FiBell /></button>
      <div className="profile-avatar">
        <img
          src="https://ui-avatars.com/api/?name=SA&background=2563eb&color=fff"
          alt="User profile"
        />
      </div>
    </div>
  </nav>
);

export default Navbar;