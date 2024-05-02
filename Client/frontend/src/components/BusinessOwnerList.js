import React, { useState, useEffect } from "react";
import { useLoaderData, useNavigate } from "react-router-dom";
import default_avatar from '../assets/default_avatar.webp';
import { ExclamationCircleIcon, LockClosedIcon, LockOpenIcon, PencilAltIcon } from "@heroicons/react/solid";
import { customFetch } from '../utils';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import { BsPencilSquare } from "react-icons/bs";
import { LampWallDown } from "lucide-react";
import {EditOwnerForm} from "."
import {LockOwnerDialog} from "."


const BusinessOwnerList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const navigate = useNavigate(); 
    const { businessOwners, params } = useLoaderData();
    const [selectedOwnerId, setSelectedOwnerId] = useState(null); 
    const [deactivatedOwners, setDeactivatedOwners] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedOwners, setSelectedOwners] = useState([]);
    const [DeactivatedState, setDeactivatedState] = useState(null); 
    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [lockDialogOpen, setLockDialogOpen] = useState(false);

        const handleSelectAll = () => {
            setSelectAll(!selectAll); 
            if (!selectAll) {
                setSelectedOwners(businessOwners.map(owner => owner.id));
            } else {
                setSelectedOwners([]);
            }
};
    const handleSelectOwner = (ownerId) => {
        if (selectedOwners.includes(ownerId)) {
            setSelectedOwners(selectedOwners.filter(id => id !== ownerId));
        } else {
            setSelectedOwners([...selectedOwners, ownerId]);
        }
};

      const handleLockAll = async (selectedOwners) => {
        console.log(selectedOwners);
        try {
            
            console.log(JSON.stringify(selectedOwners));

            const response = await customFetch(`/admin/deactivateAccounts`, {
                method: 'PATCH',
                headers: {Authorization: `Bearer ${admin.token}`},
                body: JSON.stringify(selectedOwners)
            });
            console.log(response.selectedOwners);

            console.log('Response status:', response.status);
            //const responseData = await response.text();
            const responseData = await response.text();
            toast.success("Account deactivated Successfully");
            console.log('Deactivation response:', responseData);

        } catch (error) {
            toast.error(`${error.message}`);
            console.error('Error during deactivation:', error);
            console.log(error.response.body);

        }
};
    
     
    useEffect(() => {
        if (businessOwners) {
            const deactivatedIds = businessOwners.filter(owner => owner._deactivated).map(owner => owner.id);
            setDeactivatedOwners(deactivatedIds);
        }
    }, [businessOwners]);


    const handleLockClick = (id,isDeactivated) => {
        setLockDialogOpen(true);
        setSelectedOwnerId(id);
        setDeactivatedState(isDeactivated);
    };

    const getSignedUpText = (createdAt) => {
        const currentDate = new Date();
        const signUpDate = new Date(createdAt);
        const timeDifference = currentDate.getTime() - signUpDate.getTime();
        const secondsInMs = 1000;
        const minutesInMs = secondsInMs * 60;
        const hoursInMs = minutesInMs * 60;
        const daysInMs = hoursInMs * 24;
        const weeksInMs = daysInMs * 7;
        const monthsInMs = daysInMs * 30;
        const yearsInMs = daysInMs * 365;
    
        if (timeDifference >= yearsInMs) {
            const years = Math.floor(timeDifference / yearsInMs);
            return `${years} year${years > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= monthsInMs) {
            const months = Math.floor(timeDifference / monthsInMs);
            return `${months} month${months > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= weeksInMs) {
            const weeks = Math.floor(timeDifference / weeksInMs);
            return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= daysInMs) {
            const days = Math.floor(timeDifference / daysInMs);
            return `${days} day${days > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= hoursInMs) {
            const hours = Math.floor(timeDifference / hoursInMs);
            return `${hours} hour${hours > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= minutesInMs) {
            const minutes = Math.floor(timeDifference / minutesInMs);
            return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
        } else {
            const seconds = Math.floor(timeDifference / secondsInMs);
            return `${seconds} second${seconds > 1 ? 's' : ''} ago`;
        }
    };
    
    const handleEditOwnerClick = (ownerId) => {
        setSelectedOwnerId(ownerId);
        setEditDialogOpen(true);

    };

    if (!businessOwners || businessOwners.length < 1) {
        return (
            <div className="font-bold mx-auto text-4xl text-center text-error">
                There is no match for the keyword You Typed !!!
            </div>
        );
    }

    return (
        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
                {/* head */}
                <thead>
                    <tr>
                        <th key="checkbox">
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectAll} 
                                         onChange={handleSelectAll}
                                         />
                                    </label>
                        </th>
                    <th key="profile" className="text-center">Profile</th>
                    <th key="fullName" className="text-center">Full Name</th>
                    <th key="status" className="text-center">Status</th>
                    <th key="signedUp" className="text-center">Signed Up</th>
                    <th key="edit" className="text-center"></th>
                    <th key="lockButton">
                        
                        {selectedOwners.length > 0 && ( 
                            <button
                            onClick={() => handleLockAll(selectedOwners)}
                                 className="btn btn-error btn-sm flex justify-end items" 
                                >
                                                <LockClosedIcon className='w-4 h-4' />
                                                <span className="ml-1">Lock &nbsp;&nbsp;&nbsp;</span>
                                </button>
                        )}
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {businessOwners.map((owner) => {
                        const { firstName, lastName,createdAt, email, avatarUrl, username, id, _deactivated, active,fullName } = owner;
                        return (
                            <tr key={id}>
                                <th key="checkbox">
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectedOwners.includes(id)} 
                                         onChange={() => handleSelectOwner(id)} 
                                          />
                                    </label>
                                </th>
                                <td>
                                    <div className="flex items-center gap-3">
                                        <div className="avatar">
                                            <div className="mask mask-squircle w-12 h-12">
                                                <img alt="profile image" src={avatarUrl ? `${avatarUrl}` : default_avatar} />
                                            </div>
                                        </div>
                                        <div></div>
                                    </div>
                                </td>
                                <td>
                                <div className="font-bold">{fullName}</div>
                                <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{email}</div>
                                </td>
                                <td>
    <div className="font-bold">
        {active ? (
            <div className="flex items-center">
            <div className="h-2.5 w-2.5 rounded-full bg-green-400 mr-2"></div>  ONLINE
            </div>
                    ) : (
            <div className="flex items-center">
            <div className="h-2.5 w-2.5 rounded-full bg-red-500 mr-2"></div>  OFFLINE
            </div> 
               )}
    </div>
</td>
                    <td>
                    <div>{getSignedUpText(createdAt)}</div>
                    </td>
                    <td>
                                    <button className='btn btn-accent btn-sm'
                                        onClick={() => {
                                            handleEditOwnerClick(id)
                                                                                }}
                                    >
                                    <PencilAltIcon className='w-4 h-4' />
                                    <span className="ml-1">Edit Owner</span>
                                    </button>
                    </td>


                                <td>
                                    <button 
                                        className={`btn btn-${_deactivated ? 'success' : 'error'} btn-sm`}
                                        onClick={() => {
                                            handleLockClick(id, _deactivated); 
                                        }}
                                    >
                                            {_deactivated ? 
        <>
            <LockOpenIcon className='w-4 h-4' />
            <span className="ml-1">Unlock</span>
        </> 
        : 
        <>
            <LockClosedIcon className='w-4 h-4' />
            <span className="ml-1">Lock &nbsp;&nbsp;&nbsp;</span>
        </>
    }
                                    </button>
    </td>
                        </tr>
                        );
                    })}
                </tbody>
            </table>


             {/* Lock Dialog */}
             {businessOwners.map((owner) => {
                const {id, _deactivated} = owner;
                return (

                    <dialog
                    id={`lock-dialog-${id}`}
                    className="modal modal-bottom sm:modal-middle"
                    open={lockDialogOpen}
                    onClose={() => setLockDialogOpen(false)}
                            >
                        <div className="fixed inset-0 z-50 bg-black opacity-10"></div> 
                        <LockOwnerDialog
                            ownerId={selectedOwnerId}
                            isDeactivated = {DeactivatedState}
                            onClose={() => setLockDialogOpen(false)}
                        />
                    </dialog>
                );
            })}

             {/* Edit Dialog */}
             {businessOwners.map((owner) => {
                const { id } = owner;
                return (
                    <dialog
                        key={id}
                        id={`edit-dialog-${id}`}
                        className="modal modal-bottom sm:modal-middle"
                        open={editDialogOpen && selectedOwnerId === id}
                        onClose={() => setEditDialogOpen(false)}
                    >
                        <div className="fixed inset-0 z-50 bg-black opacity-50"></div> 
                        <EditOwnerForm
                            ownerId={selectedOwnerId}
                            onClose={() => setEditDialogOpen(false)}
                        />
                    </dialog>
                );
            })}
        </div>
    );
};

export default BusinessOwnerList;
