import { SearchBox } from '@mapbox/search-js-react';
import { useState } from 'react';
import '../styles/SearchBoxComponent.css';

const SearchBoxComponent = (props) => {
  const [value, setValue] = useState('');

  const handleRetrieve = (result) => {
    const feature = result.features[0];
    const data = {
      type: props.type,
      name: feature.properties.full_address,
      longitude: feature.geometry.coordinates[0],
      latitude: feature.geometry.coordinates[1],
    };
    props.onLocationSelect(data);
    setValue(feature.properties.full_address); // force the input to reflect the pick immediately
  };

  return (
    <div className="mapbox-input-container">
      <SearchBox
        accessToken="pk.eyJ1Ijoic2hhaHplYmFsaSIsImEiOiJjbXE5Y2wzYWgwMXg1MnNzYzluMzh0eDgyIn0.x3OfkUHnSq_FuB9_R0RkLA"
        value={value}
        onChange={(d) => setValue(d)}
        options={{ language: 'en', country: 'PK' }}
        onRetrieve={handleRetrieve}
        theme={{
          variables: {
            fontFamily: 'Inter, sans-serif',
            unit: '1rem',
            padding: '0',
            boxShadow: 'none',
            border: 'none',
            borderRadius: '20px',
            colorBackground: 'white',
            colorText: '#334155',
          },
        }}
      />
    </div>
  );
};

export default SearchBoxComponent;