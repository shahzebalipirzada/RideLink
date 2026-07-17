import React, { useState, useEffect } from 'react';
import { FiX, FiPhone, FiBook, FiBriefcase, FiCalendar } from 'react-icons/fi';
import '../styles/EditProfileModal.css';

const EditProfileModal = ({ isOpen, onClose, initialDetails, onSave }) => {
  // Local state to manage form inputs
  const [formData, setFormData] = useState({
    Phone: '',
    University: '',
    Role: '',
    Joined: ''
  });

  // Populate the form with existing details when the modal opens
  useEffect(() => {
    if (initialDetails) {
      setFormData(initialDetails);
    }
  }, [initialDetails, isOpen]);

  // Prevent rendering if the modal is closed
  if (!isOpen) return null;

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
    onClose();
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      {/* Stop click propagation so clicking inside the card doesn't close it */}
      <div className="modal-card glass-panel" onClick={(e) => e.stopPropagation()}>
        
        <div className="modal-header">
          <h2>Edit Basic Details</h2>
          <button className="close-modal-btn" onClick={onClose}>
            <FiX />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="edit-details-form">
          
          <div className="input-group">
            <label>Phone Number</label>
            <div className="input-wrapper">
              <FiPhone className="input-icon" />
              <input 
                type="tel" 
                name="Phone"
                value={formData.Phone}
                onChange={handleChange}
                placeholder="+92 300 1234567"
                required
              />
            </div>
          </div>

          <div className="input-group">
            <label>University</label>
            <div className="input-wrapper">
              <FiBook className="input-icon" />
              <input 
                type="text" 
                name="University"
                value={formData.University}
                onChange={handleChange}
                placeholder="IBA Sukkur"
                required
              />
            </div>
          </div>

          <div className="input-group">
            <label>Role</label>
            <div className="input-wrapper">
              <FiBriefcase className="input-icon" />
              <input 
                type="text" 
                name="Role"
                value={formData.Role}
                onChange={handleChange}
                placeholder="Student"
                required
              />
            </div>
          </div>

          <div className="input-group">
            <label>Joined Date</label>
            <div className="input-wrapper readonly-wrapper">
              <FiCalendar className="input-icon" />
              <input 
                type="text" 
                name="Joined"
                value={formData.Joined}
                readOnly
                className="readonly-input"
                title="Join date cannot be changed"
              />
            </div>
          </div>

          <div className="modal-actions">
            <button type="button" className="btn-action btn-cancel" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn-action btn-save">
              Save Changes
            </button>
          </div>

        </form>
      </div>
    </div>
  );
};

export default EditProfileModal;