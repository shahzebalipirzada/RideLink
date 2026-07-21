import sendRequest from '../utility/sendRequest'; // adjust path to wherever you put it

function toGeoPoint(locationData) {
  return {
    name: locationData.name,
    // type: 'Point',
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
  return sendRequest('/ride/create', payload, 'POST');
}