import React from 'react';
import './RouteCard.css';

const RouteCard = ({ source, destination, dateTime, onJoin, buttonText = "Join Group" }) => {
  return (
    <div className="list-card-item">
      <div className="item-route-details">
        <h4>{source} ➔ {destination}</h4>
        <p>{dateTime}</p>
      </div>
      <button className="join-btn" onClick={onJoin}>
        {buttonText}
      </button>
    </div>
  );
};

export default RouteCard;