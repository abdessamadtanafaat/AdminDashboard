import React from 'react';
import { useHistory, useNavigate } from 'react-router-dom';

const AddNewAdminButton = () => {
  const history = useNavigate();

  const handleAddNewAdmin = () => {
    history('/admin/create-admin');
  };

  return (
    <div className="mb-3 flex justify-end items-center">
      <button className="btn btn-active btn-ghost btn-xs" onClick={handleAddNewAdmin}>Add New Admin</button>
    </div>
  );
};

export default AddNewAdminButton;
