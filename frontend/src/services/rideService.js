/**
 * rideService.js
 * All API calls related to ride/group search and management.
 */

/**
 * Search for available ride groups within a given radius.
 * @param {Object} payload - The search payload (role, origin, destination, departureTime)
 * @param {number} radius  - Search radius in km
 * @returns {Promise<Array>} - Array of matching ride groups
 */
export async function searchRideGroups(payload, radius) {
  const response = await fetch(`/ride/search/${radius}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error(`Ride search failed: ${response.status} ${response.statusText}`);
  }

  return response.json();
}