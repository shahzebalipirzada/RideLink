// src/services/groupService.js
const API_BASE = '/ride'; // matches your existing proxy rule

function toGeoPoint(locationData) {
  return {
    name: locationData.name,
    type: 'Point',
    coordinates: [locationData.longitude, locationData.latitude],
  };
}

function toApiPayload(groupData) {
  return {
    role: groupData.hasVehicle ? 'DRIVER' : 'PASSENGER',
    origin: toGeoPoint(groupData.source),
    destination: toGeoPoint(groupData.destination),
    departureTime: new Date(`${groupData.date}T${groupData.time}`).toISOString(),
  };
}

export async function createGroup(groupData) {
  const payload = toApiPayload(groupData);

  const response = await fetch(`${API_BASE}/save`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error(`Failed to create group: ${response.status}`);
  }

  return response.json();
}