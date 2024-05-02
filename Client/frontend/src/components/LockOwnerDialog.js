import React, { useState } from "react";
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux'; 
import { useLoaderData, useNavigate } from "react-router-dom";
import { ExclamationCircleIcon, LockClosedIcon, LockOpenIcon, PencilAltIcon } from "@heroicons/react/solid";

const LockOwnerDialog = ({ownerId, isDeactivated ,onClose }) => {


    const admin = useSelector(state => state.adminState.admin);
    const navigate = useNavigate();

    const LockUnlockAccount = async () => {
        try {
            const response = await customFetch(`/admin/${isDeactivated ? 'activate' : 'deactivate'}Account/${ownerId}`, {
                method: 'PATCH',
                headers: { Authorization: `Bearer ${admin.token}` }
            });
            toast.success(`Account ${isDeactivated ? 'activated' : 'deactivated'} successfully`);
            console.log(response);
            navigate("/business-owner");
            onClose();
        } catch (error) {
            toast.error(`${error.response.data}`);
            console.error('Error:', error);
        }
    };


    return (
        <div className="modal-box flex flex-col items-center justify-center z-50">
            <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={onClose}>✕</button>
            <div className="text-red-600 flex items-center justify-center mb-4">
                <ExclamationCircleIcon className='w-11 h-11 text-red-600 flex items-center justify-center mb-4' />
            </div>
            <p className="py-4 text-center text-lg font-bold" >
                Are you sure you want to {isDeactivated ? 'unlock' : 'lock'} this account?
            </p>
            <div className="modal-action">
                <form onSubmit={(e) => e.preventDefault()}>
                    <button 
                        className={`${isDeactivated ? 'btn btn-success' : 'btn btn-error'}`}
                        onClick={LockUnlockAccount}
                    >
                        Yes, I'm sure
                    </button>
                    <span className="mx-2"></span>
                    <button
                        className="btn"
                        onClick={onClose}
                    >
                        No, Cancel
                    </button>
                </form>
            </div>
        </div>
    );
    
    
    };
    
    export default LockOwnerDialog;
    