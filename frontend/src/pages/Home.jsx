import React, { useRef } from 'react';
import { AnimatePresence } from 'framer-motion';
import mapboxgl from 'mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';

import { useMap } from '../hooks/useMap';
import { useRideSearch } from '../hooks/useRideSearch';

import Navbar from '../components/Navbar';
import SearchForm from '../components/SearchForm';
import ResultsPanel from '../components/ResultsPanel';
import InfoCards from '../components/InfoCards';

import '../styles/Home.css';

mapboxgl.accessToken = import.meta.env.VITE_MAPBOX_TOKEN;

/**
 * Home
 * Layout-only page. Composes the map, navbar, search form, and result panels.
 * All state lives in useRideSearch; all API calls live in rideService.
 */
const Home = () => {
  const mapContainer = useRef(null);
  useMap(mapContainer);

  const {
    date, setDate,
    time, setTime,
    sourceData, setSourceData,
    destinationData, setDestinationData,
    radius, setRadius,
    hasVehicle, setHasVehicle,
    options,
    showResults,
    isLoading,
    error,
    handleFindGroups,
    resetSearch,
  } = useRideSearch();

  return (
    <div className="home-wrapper">
      <div ref={mapContainer} className="map-container" />
      <div className="map-overlay" />

      <div className="ui-container">
        <Navbar onLogoClick={resetSearch} />

        <main className={`main-content ${showResults ? 'results-layout' : 'explore-layout'}`}>
          <SearchForm
            showResults={showResults}
            date={date} setDate={setDate}
            time={time} setTime={setTime}
            hasVehicle={hasVehicle} setHasVehicle={setHasVehicle}
            radius={radius} setRadius={setRadius}
            onSourceSelect={setSourceData}
            onDestinationSelect={setDestinationData}
            onFindGroups={handleFindGroups}
          />

          <AnimatePresence>
            {showResults && (
              <ResultsPanel options={options} isLoading={isLoading} error={error} />
            )}
          </AnimatePresence>

          <AnimatePresence>
            {!showResults && <InfoCards />}
          </AnimatePresence>
        </main>
      </div>
    </div>
  );
};

export default Home;