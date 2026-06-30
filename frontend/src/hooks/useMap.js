import { useEffect, useRef } from 'react';
import mapboxgl from 'mapbox-gl';

const TOKEN = "pk.eyJ1Ijoic2hhaHplYmFsaSIsImEiOiJjbXE5Y2wzYWgwMXg1MnNzYzluMzh0eDgyIn0.x3OfkUHnSq_FuB9_R0RkLA";

export const useMap = (containerRef) => {
  const map = useRef(null);

  useEffect(() => {
    if (map.current) return;

    mapboxgl.accessToken = TOKEN;
    map.current = new mapboxgl.Map({
      container: containerRef.current,
      style: 'mapbox://styles/mapbox/streets-v12',
      center: [68.8191347, 27.7267609],
      zoom: 13,
      pitch: 45,
    });

    map.current.on('load', () => map.current.resize());

    return () => {
      map.current?.remove();
      map.current = null;
    };
  }, []);

  return map;
};