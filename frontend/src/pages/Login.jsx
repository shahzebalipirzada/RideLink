import React from "react";
import { useState } from "react";
import "../styles/Login.css";
import {
  FaLocationDot,
  FaShieldHalved,
  FaUserGroup,
  FaEye,
} from "react-icons/fa6";
import { FaGoogle, FaGithub } from "react-icons/fa";
import { FiMail, FiLock } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import sendRequest from "../utility/sendRequest";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [invalidCredentials, setInvalidCredentials] = useState(false);
  const navigate = useNavigate();

  const handleLogin = (e)=>{
        e.preventDefault();
  //   fetch('/auth/login', {
  //     method: 'POST',
  //     headers: {
  //       'Content-Type': 'application/json',
  //     },
  //     body: JSON.stringify({ username, password }),
  //   })
  //   .then(response => response.json())
  //   .then(data => {
  //     console.log('Success:', data);
  //     if(data.username) navigate('/');
  //     else {
  //       setInvalidCredentials(true);
  //     }
  //   })
  //   .catch((error) => {
  //     console.error('Error:', error);

  //   });
  // }

  sendRequest("/auth/login", { username, password }, "POST")
    .then((data) => {
      console.log("Success:", data);
      navigate("/");
    })
    .catch((error) => {
      if (error.status === 401) {
        setInvalidCredentials(true);
      } else {
        console.error("Unexpected error:", error);
        // maybe a generic toast/error state here
      }
    });
  }

  const handleGithubLogin = () => {
    window.location.href = "/oauth2/authorization/github";
  };

  const handleGoogleLogin = () => {
    window.location.href = "/oauth2/authorization/google";
  };

  return (
    <div className="login-page-background">
      <div className="login-wrapper">
        {/* Background Floating Map Pins */}
        <div className="floating-pin pin-1">
          <FaLocationDot className="pin-icon" color="#ef4444" /> University →
          City Center
        </div>
        <div className="floating-pin pin-2">
          <FaLocationDot className="pin-icon" color="#3b82f6" /> Airport → North
          Plaza
        </div>
        <div className="floating-pin pin-3">
          <FaLocationDot className="pin-icon" color="#10b981" /> Suburb →
          Downtown
        </div>
        {/* Main Glassmorphism Card */}
        <div className="login-card">
          <div className="logo-container">
            <div className="logo-icon">
              <svg
                width="28"
                height="28"
                viewBox="0 0 24 24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <rect width="24" height="24" rx="8" fill="#2563EB" />
                <path
                  d="M8 16V10C8 8.89543 8.89543 8 10 8H14C15.1046 8 16 8.89543 16 10V16"
                  stroke="white"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
                <circle cx="10" cy="16" r="2" fill="white" />
                <circle cx="16" cy="16" r="2" fill="white" />
              </svg>
            </div>
            <span className="logo-text">RideLink</span>
          </div>

          <h1 className="title">
            Travel Together.
            <br />
            Arrive Together.
          </h1>

          {/* Credentials Form */}
          <form className="login-form">
            <div className="input-group">
              <div className="input-wrapper">
                <FiMail className="input-icon" color="#64748b" />
                <input
                  type="email"
                  placeholder="Enter your email"
                  required
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>
            </div>

            <div className="input-group">
              <div className="input-wrapper">
                <FiLock className="input-icon" color="#64748b" />
                <input
                  type="password"
                  placeholder="Enter your password"
                  required
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>
            {invalidCredentials && (
              <p className="error-message" style={{ color: "red" }}>
                Invalid username or password. Please try again.
              </p>
            )}

            <button
              type="submit"
              className="find-btn login-submit-btn"
              onClick={handleLogin}
            >
              Login
            </button>
          </form>

          {/* Toggle Option to Register */}
          <p className="register-redirect">
            Don't have an account? <a href="/signup">Signup here</a>
          </p>

          {/* Social Authentication Splitter */}
          <div className="social-divider">
            <span>or continue with</span>
          </div>

          {/* OAuth Buttons */}
          <div className="social-login-row">
            <button
              type="button"
              className="social-btn google-btn"
              onClick={handleGoogleLogin}
            >
              <FaGoogle size={18} />
              <span>Google</span>
            </button>
            <button
              type="button"
              className="social-btn github-btn"
              onClick={handleGithubLogin}
            >
              <FaGithub size={18} />
              <span>GitHub</span>
            </button>
          </div>

          <p className="terms">
            By continuing, you agree to our{" "}
            <a href="#terms">Terms of Service</a> and{" "}
            <a href="#privacy">Privacy Policy</a>.
          </p>
        </div>

        {/* Footer Feature Highlights */}
        <div className="features-container">
          <div className="feature">
            <FaShieldHalved className="feature-icon" color="#64748b" />
            <span>
              Secure Google
              <br />
              Authentication
            </span>
          </div>
          <div className="feature">
            <FaUserGroup className="feature-icon" color="#64748b" />
            <span>
              Verified
              <br />
              Community
            </span>
          </div>
          <div className="feature">
            <FaEye className="feature-icon" color="#64748b" />
            <span>
              Real-Time
              <br />
              Discovery
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
