import React from 'react';
import '../styles/Login.css';
import { FaLocationDot, FaShieldHalved, FaUserGroup, FaEye } from "react-icons/fa6";
import { FcGoogle } from "react-icons/fc";

const Login = () => {
  return (
    <div className="login-page-background">
      <div className="login-wrapper">
        {/* Background Floating Map Pins */}
        <div className="floating-pin pin-1">
          <FaLocationDot className="pin-icon" color="#ef4444" /> University → City Center
        </div>
        <div className="floating-pin pin-2">
          <FaLocationDot className="pin-icon" color="#3b82f6" /> Airport → North Plaza
        </div>
        <div className="floating-pin pin-3">
          <FaLocationDot className="pin-icon" color="#10b981" /> Suburb → Downtown
        </div>

        {/* Main Glassmorphism Card */}
        <div className="login-card">
          <div className="logo-container">
            <div className="logo-icon">
              <svg width="28" height="28" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <rect width="24" height="24" rx="8" fill="#2563EB"/>
                <path d="M8 16V10C8 8.89543 8.89543 8 10 8H14C15.1046 8 16 8.89543 16 10V16" stroke="white" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                <circle cx="10" cy="16" r="2" fill="white"/>
                <circle cx="16" cy="16" r="2" fill="white"/>
              </svg>
            </div>
            <span className="logo-text">RideLink</span>
          </div>

          <h1 className="title">Travel Together.<br />Arrive Together.</h1>
          <p className="subtitle">
            Discover nearby travel groups, connect with people heading to the same destination, and make every journey smarter and safer.
          </p>

          <button className="google-btn">
            <FcGoogle size={22} />
            Continue with Google
          </button>

          <p className="terms">
            By continuing, you agree to our <a href="#terms">Terms of Service</a> and <a href="#privacy">Privacy Policy</a>.
          </p>
        </div>

        {/* Footer Feature Highlights */}
        <div className="features-container">
          <div className="feature">
            <FaShieldHalved className="feature-icon" color="#64748b" />
            <span>Secure Google<br />Authentication</span>
          </div>
          <div className="feature">
            <FaUserGroup className="feature-icon" color="#64748b" />
            <span>Verified<br />Community</span>
          </div>
          <div className="feature">
            <FaEye className="feature-icon" color="#64748b" />
            <span>Real-Time<br />Discovery</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;