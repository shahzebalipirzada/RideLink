import sendRequest from '../utility/sendRequest';

export async function fetchCurrentUser() {
  return sendRequest('/user', {}, 'GET');
}