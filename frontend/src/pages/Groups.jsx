import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { FiSearch, FiMapPin, FiClock, FiCalendar, FiUsers, FiUserPlus } from 'react-icons/fi';

import { useMap } from '../hooks/useMap';
import Navbar from '../components/Navbar';
import '../styles/Groups.css';
import AddGroupComponent from '../components/AddGroupComponent';
import { useCreateGroupModal } from '../hooks/useCreateGroupModal';

const Groups = () => {
  const navigate = useNavigate();
  const mapContainer = useRef(null);
  const [searchQuery, setSearchQuery] = useState('');

  // Aesthetic Mapbox background
  useMap(mapContainer, {
    center: [68.8191347, 27.7267609], // Sukkur
    zoom: 11,
    pitch: 45,
    interactive: false
  });

  // Mock Group Documents (Matches what you would store in Supabase/MongoDB)
  const userGroups = [ 
    {
      id: 'g1',
      name: 'Morning IBA Commuters',
      from: 'City Center, Sukkur',
      to: 'IBA University Campus',
      date: 'July 16, 2026',
      time: '08:00 AM',
      members: 3,
      capacity: 4,
      creator: 'Shahrukh'
    },
    {
      id: 'g2',
      name: 'Weekend Karachi Trip',
      from: 'Sukkur IBA',
      to: 'Clifton, Karachi',
      date: 'July 18, 2026',
      time: '06:00 AM',
      members: 2,
      capacity: 4,
      creator: 'Muhammad Khizar'
    }
  ];  
    



  const mockGroups = [
    {
      id: 'g1',
      name: 'Morning IBA Commuters',
      from: 'City Center, Sukkur',
      to: 'IBA University Campus',
      date: 'July 16, 2026',
      time: '08:00 AM',
      members: 3,
      capacity: 4,
      creator: 'Shahrukh'
    },
    {
      id: 'g2',
      name: 'Weekend Karachi Trip',
      from: 'Sukkur IBA',
      to: 'Clifton, Karachi',
      date: 'July 18, 2026',
      time: '06:00 AM',
      members: 2,
      capacity: 4,
      creator: 'Muhammad Khizar'
    },
    {
      id: 'g3',
      name: 'Airport Drop-off Pool',
      from: 'North Plaza',
      to: 'Sukkur Airport',
      date: 'July 16, 2026',
      time: '05:30 PM',
      members: 1,
      capacity: 3,
      creator: 'Sheikh Muhammad'
    },
    {
      id: 'g4',
      name: 'Tech Expo Travelers',
      from: 'Sukkur City Center',
      to: 'Expo Center, Lahore',
      date: 'July 25, 2026',
      time: '09:00 PM',
      members: 4,
      capacity: 5,
      creator: 'Shahzeb Ali'
    }
  ];

  // Filter groups based on search input
  const filteredGroups = mockGroups.filter(group => 
    group.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    group.to.toLowerCase().includes(searchQuery.toLowerCase()) ||
    group.from.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const handleJoinGroup = (groupId) => {
    console.log(`Requesting to join group: ${groupId}`);
    // Add Supabase DB update logic here
  };

   const { isOpen, openModal, closeModal, handleSubmit } = useCreateGroupModal();


  return (
    <>
    <AddGroupComponent isOpen={isOpen} onClose={closeModal} onSubmit={handleSubmit} />

    <div className="groups-wrapper">
      {/* Background Layer */}
      <div ref={mapContainer} className="map-container" />
      <div className="map-overlay groups-overlay" />

      {/* UI Layer */}
      <div className="ui-container">
        <Navbar onLogoClick={() => navigate('/')} activeLink="groups" />

        <main className="groups-main-content">
          
          {/* Header & Search Bar */}
          <div className="groups-header-section">
            <h1 className="groups-page-title">Discover Travel Groups</h1>
            <p className="groups-page-subtitle">Find people heading your way and share the journey.</p>
            
            <div className="groups-search-wrapper glass-panel">
              <FiSearch className="search-icon" />
              <input 
                type="text" 
                placeholder="Search by destination, area, or group name..." 
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
             
            </div>
             <button className="create-group-btn" onClick={openModal}>
                + Create New Group
              </button>
            

          
          </div>
         

          {/* Groups Grid */}
          <div className="groups-grid">
            
            {filteredGroups.length > 0 ? (
              filteredGroups.map(group => (
                <div key={group.id} className="group-card glass-panel">
                  
                  <div className="group-card-header">
                    <h3 className="group-name">{group.name}</h3>
                    <span className="group-capacity">
                      <FiUsers /> {group.members}/{group.capacity}
                    </span>
                  </div>

                  <div className="group-route">
                    <div className="route-point">
                      <FiMapPin className="route-icon start" />
                      <span>{group.from}</span>
                    </div>
                    <div className="route-line"></div>
                    <div className="route-point">
                      <FiMapPin className="route-icon end" />
                      <span>{group.to}</span>
                    </div>
                  </div>

                  <div className="group-details">
                    <div className="detail-pill">
                      <FiCalendar /> {group.date}
                    </div>
                    <div className="detail-pill">
                      <FiClock /> {group.time}
                    </div>
                  </div>

                  <div className="group-card-footer">
                    <span className="group-creator">Organized by <strong>{group.creator}</strong></span>
                    <button 
                      className="join-group-btn" 
                      onClick={() => handleJoinGroup(group.id)}
                      disabled={group.members >= group.capacity}
                    >
                      {group.members >= group.capacity ? 'Full' : <><FiUserPlus /> Join</>}
                    </button>
                  </div>

                </div>
              ))
            ) : (
              <div className="no-groups-found glass-panel">
                <FiSearch size={32} color="#94a3b8" />
                <h3>No groups found</h3>
                <p>Try adjusting your search terms or create a new group yourself.</p>
              </div>
            )}
          </div>

        </main>
      </div>
    </div>
    </>
  );

};

export default Groups;