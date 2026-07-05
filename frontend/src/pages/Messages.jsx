import React, { useState, useEffect, useRef } from 'react';
import { FiSend, FiSearch, FiMoreVertical, FiUsers, FiUser, FiMessageSquare } from 'react-icons/fi';
import { chatService } from '../services/chatService';
import Navbar from '../components/Navbar';
import { useMap } from '../hooks/useMap'; // Using your new custom hook
import '../styles/Messages.css';

const Messages = ({ onNavigateHome }) => {
  const currentUserId = "user_shahzeb_123"; 

  // --- MAPBOX INITIALIZATION ---
  const mapContainer = useRef(null);
  
  // Call your hook, passing in specific options for the Messages background
  useMap(mapContainer, {
    center: [68.8191347, 27.7267609], // Default to Sukkur
    zoom: 12,
    pitch: 30, // Slight tilt looks great in the background
  });
  // -----------------------------

  const [activeTab, setActiveTab] = useState('individual');
  const [activeChat, setActiveChat] = useState(null);
  const [messageInput, setMessageInput] = useState('');
  const [messages, setMessages] = useState([]);

  const mockChats = {
    individual: [
      { id: 'c1', name: 'Muhammad Khizar', role: 'Developer', avatar: 'https://ui-avatars.com/api/?name=MK&background=0D8ABC&color=fff', lastMessage: 'The API endpoints are ready for testing.' },
      { id: 'c2', name: 'Sheikh Muhammad', role: 'Hardware', avatar: 'https://ui-avatars.com/api/?name=SM&background=10B981&color=fff', lastMessage: 'Sensor integration is almost complete.' },
      { id: 'c3', name: 'Shahrukh', role: 'ML Specialist', avatar: 'https://ui-avatars.com/api/?name=S&background=F59E0B&color=fff', lastMessage: 'Model accuracy improved by 4%.' }
    ],
    groups: [
      { id: 'g1', name: 'RideLink Core Team', members: 4, avatar: 'https://ui-avatars.com/api/?name=RL&background=6366F1&color=fff', lastMessage: 'Meeting at 5 PM.' },
      { id: 'g2', name: 'IBA Campus Route', members: 12, avatar: 'https://ui-avatars.com/api/?name=IC&background=8B5CF6&color=fff', lastMessage: 'Anyone leaving early today?' }
    ]
  };

  const currentList = activeTab === 'individual' ? mockChats.individual : mockChats.groups;

  useEffect(() => {
    if (!activeChat) return;

    const loadMessages = async () => {
      try {
        setMessages([
          { id: 1, text: "Hey, how's the progress?", sender_id: 'other_user', created_at: new Date(Date.now() - 3600000).toISOString() },
          { id: 2, text: "Working on the messaging UI right now.", sender_id: currentUserId, created_at: new Date(Date.now() - 3500000).toISOString() }
        ]);
      } catch (error) {
        console.error("Error loading messages", error);
      }
    };

    loadMessages();

    if (chatService && chatService.subscribeToMessages) {
      const subscription = chatService.subscribeToMessages(activeChat.id, (newMessage) => {
        setMessages((prev) => [...prev, newMessage]);
      });
      return () => subscription.unsubscribe();
    }
  }, [activeChat]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!messageInput.trim() || !activeChat) return;

    const newMessage = {
      id: Date.now(),
      text: messageInput,
      sender_id: currentUserId,
      created_at: new Date().toISOString()
    };

    setMessages(prev => [...prev, newMessage]);
    setMessageInput('');
  };

  return (
    <div className="messages-page-wrapper">
      
      {/* Background Mapbox Layer */}
      <div ref={mapContainer} className="messages-map-container" />
      
      {/* Soft white overlay to make text readable */}
      <div className="messages-map-overlay"></div>

      {/* Foreground UI Layer */}
      <div className="messages-ui-layer">
        <div className="messages-navbar-container">
          <Navbar onLogoClick={onNavigateHome} activeLink="messages" />
        </div>

        <div className="messages-container glass-panel">
          
          {/* LEFT SIDEBAR */}
          <div className="chat-sidebar">
            <div className="sidebar-header">
              <h2>Messages</h2>
              <div className="input-wrapper chat-search-pill">
                <FiSearch className="input-icon" color="#64748b" />
                <input type="text" placeholder="Search conversations..." />
              </div>
            </div>

            <div className="chat-tabs">
              <button className={`tab-btn ${activeTab === 'individual' ? 'active' : ''}`} onClick={() => setActiveTab('individual')}>
                <FiUser /> Direct
              </button>
              <button className={`tab-btn ${activeTab === 'groups' ? 'active' : ''}`} onClick={() => setActiveTab('groups')}>
                <FiUsers /> Groups
              </button>
            </div>

            <div className="chat-list">
              {currentList.map(chat => (
                <div key={chat.id} className={`chat-list-item ${activeChat?.id === chat.id ? 'active' : ''}`} onClick={() => setActiveChat(chat)}>
                  <img src={chat.avatar} alt={chat.name} className="chat-avatar" />
                  <div className="chat-info">
                    <h4>{chat.name}</h4>
                    <p className="last-message">{chat.lastMessage}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* RIGHT PANE */}
          <div className="chat-main">
            {activeChat ? (
              <>
                <div className="chat-main-header">
                  <div className="active-chat-profile">
                    <img src={activeChat.avatar} alt={activeChat.name} />
                    <div>
                      <h3>{activeChat.name}</h3>
                      <span>{activeChat.role || `${activeChat.members} members`}</span>
                    </div>
                  </div>
                  <button className="icon-btn"><FiMoreVertical /></button>
                </div>

                <div className="chat-messages-area">
                  {messages.map((msg) => {
                    const isMine = msg.sender_id === currentUserId;
                    return (
                      <div key={msg.id} className={`message-bubble-wrapper ${isMine ? 'mine' : 'theirs'}`}>
                        <div className={`message-bubble ${isMine ? 'bg-blue' : 'bg-glass'}`}>
                          <p>{msg.text}</p>
                          <span className="msg-time">
                            {new Date(msg.created_at).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                          </span>
                        </div>
                      </div>
                    );
                  })}
                </div>

                <form className="chat-input-area" onSubmit={handleSendMessage}>
                  <div className="input-wrapper chat-input-pill">
                    <input 
                      type="text" 
                      placeholder="Type a message..." 
                      value={messageInput}
                      onChange={(e) => setMessageInput(e.target.value)}
                    />
                  </div>
                  <button type="submit" className="find-btn send-btn" disabled={!messageInput.trim()}>
                    <FiSend />
                  </button>
                </form>
              </>
            ) : (
              <div className="empty-chat-state">
                <FiMessageSquare size={48} color="#94a3b8" />
                <h3>Your Messages</h3>
                <p>Select a conversation from the sidebar or start a new one.</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Messages;