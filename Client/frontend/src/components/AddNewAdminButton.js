import { Plus, UserRoundPlus } from 'lucide-react';
import React from 'react';
import { useHistory, useNavigate } from 'react-router-dom';

const AddNewAdminButton = () => {
  const history = useNavigate();

  const handleAddNewAdmin = () => {
    history('/admin/create-admin');
  };

  return (
    <div className="mb-3 flex justify-end items-center">
      <button className="btn btn-success btn-sm" onClick={handleAddNewAdmin}><UserRoundPlus className='w-4 h-4'/> New </button>
    </div>
  );
};

export default AddNewAdminButton;
