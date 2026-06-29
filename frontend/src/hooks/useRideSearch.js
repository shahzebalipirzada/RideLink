import { useState } from 'react';

export const useRideSearch = () => {
  const [options, setOptions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const searchRides = async (data, radius) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`/ride/search/${radius}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });

      if (!response.ok) throw new Error(`Request failed: ${response.status}`);

      const result = await response.json();
      setOptions(result);
    } catch (err) {
      setError(err.message);
      console.error("Search failed:", err);
    } finally {
      setLoading(false);
    }
  };

  return { options, loading, error, searchRides };
};