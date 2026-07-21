let refreshPromise = null; // shared across all callers to dedupe concurrent refreshes

const sendRequest = async (url, data = {}, method = 'GET', isRetry = false) => {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include', // sends the httpOnly cookie
  };

  if (method !== 'GET') {
    options.body = JSON.stringify(data);
  }

  const response = await fetch(url, options);

  if (!response.ok) {
    if (response.status === 401 && !isRetry && url !== '/auth/refresh-token') {
      // Dedupe: if a refresh is already in flight, wait for that one
      if (!refreshPromise) {
        refreshPromise = sendRequest('/auth/refresh-token', {}, 'POST', true)
          .finally(() => {
            refreshPromise = null; // reset so future 401s can trigger a new refresh
          });
      }

      try {
        await refreshPromise; // wait for refresh to finish
        return sendRequest(url, data, method, true); // retry original request once
      } catch (refreshError) {
        // refresh failed -> force login
        window.location.href = '/login';
        throw new Error('Session expired. Redirecting to login.');
      }
    }

    // Non-401 error, or refresh already retried and still failing
    const errorBody = await response.json().catch(() => null);
    const error = new Error(errorBody?.message || `Request failed with status ${response.status}`);
    error.status = response.status;
    error.body = errorBody;
    throw error;
  }

  return response.json();
};

export default sendRequest;