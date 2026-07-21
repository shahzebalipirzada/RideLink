import sendRequest from '../utility/sendRequest';

export async function searchRideGroups(payload, radius) {
  return sendRequest(`/ride/search/${radius}`, payload, 'POST');
}