import React from 'react';
import { FiMessageSquare, FiBell } from 'react-icons/fi';

/**
 * Navbar
 * Top navigation bar with branding, links, and user actions.
 *
 * @param {Function} onLogoClick - Resets the search/results view when logo is clicked
 * @param {string} activeLink - The currently active link
 */
const Navbar = ({ onLogoClick, activeLink }) => (
  <nav className="navbar glass-panel">
    <div className="nav-brand" onClick={onLogoClick} style={{ cursor: 'pointer' }}>
      RideLink
    </div>

    <div className="nav-links">
      <a href="/" className={activeLink === 'home' ? 'active' : ''} onClick={onLogoClick}>Home</a>
      <a href="/messages" className={activeLink === 'messages' ? 'active' : ''}>Messages</a>
      <a href="/groups" className={activeLink === 'groups' ? 'active' : ''}>Groups</a>
    </div>

    <div className="nav-actions">
      {/* <button className="icon-btn" aria-label="Messages"><FiMessageSquare /></button>
      <button className="icon-btn" aria-label="Notifications"><FiBell /></button> */}
      <div className="profile-avatar">
        <a href="/Profile">
        <img
          src="https://ui-avatars.com/api/?name=SA&background=2563eb&color=fff"
          alt="User profile"
        />
        </a>
      </div>
    </div>
  </nav>
);

export default Navbar;