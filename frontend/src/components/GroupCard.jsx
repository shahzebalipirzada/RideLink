 import { FiSearch, FiMapPin, FiClock, FiCalendar, FiUsers, FiUserPlus } from 'react-icons/fi';

 const GroupCard = (group) => {
    return (
     <div className="group-card glass-panel">
                      
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
    );

}

export default GroupCard;