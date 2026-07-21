const sendRequest = async (url, data = {}, method = 'GET', isRetry = false) => {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  };

  if (method !== 'GET') {
    options.body = JSON.stringify(data);
  }

  const response = await fetch(url, options);

  if (!response.ok) {
    if (response.status === 401 && !isRetry && url !== '/auth/refresh-token') {
      if (!refreshPromise) {
        refreshPromise = sendRequest('/auth/refresh-token', {}, 'POST', true)
          .finally(() => {
            refreshPromise = null;
          });
      }

      try {
        await refreshPromise;
        return sendRequest(url, data, method, true);
      } catch (refreshError) {
        window.location.href = '/login';
        throw new Error('Session expired. Redirecting to login.');
      }
    }

    const errorBody = await response.json().catch(() => null);
    const error = new Error(errorBody?.message || `Request failed with status ${response.status}`);
    error.status = response.status;
    error.body = errorBody;
    throw error;
  }

  // 204 No Content, or any 2xx with an empty body — nothing to parse
  if (response.status === 204) {
    return null;
  }

  const text = await response.text();
  if (!text) {
    return null;
  }

  try {
    return JSON.parse(text);
  } catch {
    // response wasn't valid JSON but request succeeded — don't throw
    return null;
  }
};

export default sendRequest;