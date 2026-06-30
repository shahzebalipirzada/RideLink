import { useState } from "react";
import { searchRideGroups } from "../services/rideService";

/**
 * useRideSearch
 * Encapsulates all state and logic for the ride group search flow.
 * Keeps pages and components free of business logic.
 */
export function useRideSearch() {
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [sourceData, setSourceData] = useState(null);
  const [destinationData, setDestinationData] = useState(null);
  const [radius, setRadius] = useState(15);
  const [hasVehicle, setHasVehicle] = useState(false);
  const [options, setOptions] = useState([]);
  const [showResults, setShowResults] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);


  const buildPayload = () => ({
    role: hasVehicle ? "DRIVER" : "PASSENGER",
    origin: {
      name: sourceData?.name,
      type: "Point",
      coordinates: [sourceData?.longitude, sourceData?.latitude],
    },
    destination: {
      name: destinationData?.name,
      type: "Point",
      coordinates: [destinationData?.longitude, destinationData?.latitude],
    },
    "departureTime": new Date(`${date}T${time}`).toISOString(),
  });

  const validate = () => {
    if (!sourceData) return "Please select a source location.";
    if (!destinationData) return "Please select a destination.";
    if (!date) return "Please select a date.";
    if (!time) return "Please select a time.";
    return null; // all good
  };

  const handleFindGroups = async () => {
    const validationError = validate();
    if (validationError) {
      setError(validationError); // reuse your existing error state
      return; // stop here, don't call the API
    }

    setError(null);
    setIsLoading(true);
    setShowResults(true);

    try {
      const payload = buildPayload();
      const results = await searchRideGroups(payload, radius);
      setOptions(results);
    } catch (err) {
      console.error("Error searching groups:", err);
      setError("Failed to load groups. Please try again.");
      setOptions([]);
    } finally {
      setIsLoading(false);
    }
  };

  const resetSearch = () => {
    setShowResults(false);
    setOptions([]);
    setError(null);
  };

  return {
    // Form state
    date,
    setDate,
    time,
    setTime,
    sourceData,
    setSourceData,
    destinationData,
    setDestinationData,
    radius,
    setRadius,
    hasVehicle,
    setHasVehicle,
    // Results state
    options,
    showResults,
    isLoading,
    error,
    // Actions
    handleFindGroups,
    resetSearch,
  };
}
