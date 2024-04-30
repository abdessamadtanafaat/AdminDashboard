import React, { createContext, useState } from 'react';

export const SortContext = createContext();

export const SortProvider = ({ children }) => {
  const [sortOrder, setSortOrder] = useState('asc');

  const toggleSortOrder = () => {
    const newSortOrder = sortOrder === 'asc' ? 'desc' : 'asc';
    setSortOrder(newSortOrder);
  };

  return (
    <SortContext.Provider value={{ sortOrder, toggleSortOrder }}>
      {children}
    </SortContext.Provider>
  );
};