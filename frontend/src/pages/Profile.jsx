import React, {useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { FiUser, FiEdit2, FiLogOut } from 'react-icons/fi';
import { useMap } from '../hooks/useMap';
import Navbar from '../components/Navbar';
import '../styles/Profile.css';
import EditProfileModal from '../components/EditProfileModal';


const Profile = () => {
  const navigate = useNavigate();
  const mapContainer = useRef(null);

  // Initialize the aesthetic background map
  useMap(mapContainer, {
    center: [68.8191347, 27.7267609], 
    zoom: 12,
    pitch: 40,
    interactive: false 
  });

    const [name, setName] = useState('Loading...');
    const [email, setEmail] = useState('Loading...');

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [userDetails, setUserDetails] = useState({
    Phone: "+92 300 1234567",
    University: "IBA Sukkur",
    Role: "Student",
    Joined: "August 2025"
  });


  const handleEdit = () => {
    setIsEditModalOpen(true);
  };


  // Create a function to receive the saved data
  const handleSaveDetails = (updatedDetails) => {
    setUserDetails(updatedDetails);
  };


useEffect(() => { 
    fetch('/user', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Failed to fetch user profile');
      }
      return response.json();
    })
    .then(data => {
      setName(data.name);
      setEmail(data.email);
    })
    .catch(error => {
      console.error('Error fetching user profile:', error);
      //navigate('/login'); 
    });

},[])


  const handleLogout = () => {
    console.log("Logging out...");
    navigate('/login');
  };


  return (
    <div className="profile-wrapper">
      {/* Background Layer */}
      <div ref={mapContainer} className="map-container" />
      <div className="map-overlay profile-overlay" />

      {/* UI Layer */}
      <div className="ui-container">
        <Navbar onLogoClick={() => navigate('/')} activeLink="profile" />

        <main className="profile-main-content">
          <div className="profile-card glass-panel">
            
            {/* Centered Avatar and Identity */}
            <header className="profile-header">
              <div className="avatar-placeholder">
                <FiUser className="avatar-icon" />
              </div>
              <h1 className="profile-name">{name}</h1>
              <p className="profile-email">{email}</p>
            </header>

            {/* Basic Details Box */}
            <div className="basic-details-container">
              <h3 className="details-title">Basic Details</h3>
             <ul className="details-list">
        {Object.entries(userDetails).map(([key, value]) => (
          <li key={key} className="detail-item">
            <span className="detail-label">{key}</span>
            <span className="detail-value">{value}</span>
          </li>
        ))}
      </ul>
            </div>

            {/* Action Buttons */}
            <div className="profile-actions">
              <button className="btn-action btn-edit" onClick={handleEdit}>
                <FiEdit2 /> Edit Details
              </button>
              <button className="btn-action btn-logout" onClick={handleLogout}>
                <FiLogOut /> Logout
              </button>
            </div>

          </div>
        </main>
      </div>
      <EditProfileModal 
        isOpen={isEditModalOpen} 
        onClose={() => setIsEditModalOpen(false)} 
        initialDetails={userDetails}
        onSave={handleSaveDetails}
      />
    </div>
  );
};

export default Profile;