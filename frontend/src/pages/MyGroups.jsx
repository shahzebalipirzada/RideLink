import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { FiMapPin, FiClock, FiCalendar, FiUsers, FiMessageSquare, FiSettings, FiLogOut } from 'react-icons/fi';

import { useMap } from '../hooks/useMap';
import Navbar from '../components/Navbar';
import '../styles/MyGroups.css';

const MyGroups = () => {
  const navigate = useNavigate();
  const mapContainer = useRef(null);
  const [activeTab, setActiveTab] = useState('upcoming'); // 'upcoming' or 'past'

  // Background map
  useMap(mapContainer, {
    center: [68.8191347, 27.7267609], // Sukkur
    zoom: 11,
    pitch: 45,
    interactive: false
  });

  const currentUser = "Shahzeb Ali";

  // Mock Data: Groups the user has already joined or created
  const myGroupsData = [
    {
      id: 'mg1',
      name: 'Tech Expo Travelers',
      from: 'Sukkur City Center',
      to: 'Expo Center, Lahore',
      date: 'July 25, 2026',
      time: '09:00 PM',
      members: 5,
      capacity: 5,
      creator: 'Shahzeb Ali',
      status: 'upcoming'
    },
    {
      id: 'mg2',
      name: 'Morning IBA Commuters',
      from: 'City Center, Sukkur',
      to: 'IBA University Campus',
      date: 'July 18, 2026',
      time: '08:00 AM',
      members: 3,
      capacity: 4,
      creator: 'Shahrukh',
      status: 'upcoming'
    },
    {
      id: 'mg3',
      name: 'Eid Break Journey',
      from: 'IBA Hostels',
      to: 'Karachi Cantt Station',
      date: 'June 10, 2026',
      time: '10:00 PM',
      members: 4,
      capacity: 4,
      creator: 'Muhammad Khizar',
      status: 'past'
    }
  ];

  const displayedGroups = myGroupsData.filter(group => group.status === activeTab);

  const handleOpenChat = (groupId) => {
    // Navigate to messages page and open this specific group chat
    navigate('/messages'); 
  };

  return (
    <div className="my-groups-wrapper">
      {/* Background Layer */}
      <div ref={mapContainer} className="map-container" />
      <div className="map-overlay my-groups-overlay" />

      {/* UI Layer */}
      <div className="ui-container">
        <Navbar onLogoClick={() => navigate('/')} activeLink="mygroups" />

        <main className="my-groups-main-content">
          
          {/* Header & Tabs */}
          <div className="my-groups-header-section">
            <h1 className="my-groups-title">My Travel Groups</h1>
            <p className="my-groups-subtitle">Manage your upcoming journeys and chat with your travel buddies.</p>
            
            <div className="my-groups-tabs glass-panel">
              <button 
                className={`tab-btn ${activeTab === 'upcoming' ? 'active' : ''}`}
                onClick={() => setActiveTab('upcoming')}
              >
                Upcoming Rides
              </button>
              <button 
                className={`tab-btn ${activeTab === 'past' ? 'active' : ''}`}
                onClick={() => setActiveTab('past')}
              >
                Past Rides
              </button>
            </div>
          </div>

          {/* Groups Grid */}
          <div className="my-groups-grid">
            {displayedGroups.length > 0 ? (
              displayedGroups.map(group => {
                const isCreator = group.creator === currentUser;

                return (
                  <div key={group.id} className={`my-group-card glass-panel ${activeTab === 'past' ? 'past-card' : ''}`}>
                    
                    <div className="my-group-card-header">
                      <div>
                        <h3 className="my-group-name">{group.name}</h3>
                        <span className="my-group-role">
                          {isCreator ? 'Organized by You' : `Organized by ${group.creator}`}
                        </span>
                      </div>
                      <span className="my-group-capacity">
                        <FiUsers /> {group.members}/{group.capacity}
                      </span>
                    </div>

                    <div className="my-group-route">
                      <div className="my-route-point">
                        <FiMapPin className="my-route-icon start" />
                        <span>{group.from}</span>
                      </div>
                      <div className="my-route-line"></div>
                      <div className="my-route-point">
                        <FiMapPin className="my-route-icon end" />
                        <span>{group.to}</span>
                      </div>
                    </div>

                    <div className="my-group-details">
                      <div className="my-detail-pill">
                        <FiCalendar /> {group.date}
                      </div>
                      <div className="my-detail-pill">
                        <FiClock /> {group.time}
                      </div>
                    </div>

                    <div className="my-group-actions">
                      {activeTab === 'upcoming' ? (
                        <>
                          <button className="my-btn my-btn-chat" onClick={() => handleOpenChat(group.id)}>
                            <FiMessageSquare /> Group Chat
                          </button>
                          
                          {isCreator ? (
                            <button className="my-btn my-btn-manage">
                              <FiSettings /> Manage
                            </button>
                          ) : (
                            <button className="my-btn my-btn-leave">
                              <FiLogOut /> Leave
                            </button>
                          )}
                        </>
                      ) : (
                        <button className="my-btn my-btn-secondary" style={{ width: '100%' }}>
                          View Ride Details
                        </button>
                      )}
                    </div>

                  </div>
                );
              })
            ) : (
              <div className="no-my-groups glass-panel">
                <FiCalendar size={32} color="#94a3b8" />
                <h3>No {activeTab} rides found</h3>
                <p>You don't have any {activeTab} travel plans at the moment.</p>
                {activeTab === 'upcoming' && (
                  <button className="find-ride-btn" onClick={() => navigate('/groups')}>
                    Find a Group
                  </button>
                )}
              </div>
            )}
          </div>

        </main>
      </div>
    </div>
  );
};

export default MyGroups;