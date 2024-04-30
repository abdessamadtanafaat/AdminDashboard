import React, { useState, useEffect } from "react";
import { useLoaderData, useNavigate } from "react-router-dom";
import default_avatar from '../assets/default_avatar.webp';
import { ExclamationCircleIcon, LockClosedIcon, LockOpenIcon } from "@heroicons/react/solid";
import { customFetch } from '../utils';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';


const BusinessOwnerList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const navigate = useNavigate(); 
    const { businessOwners, params } = useLoaderData();
    const [selectedOwnerId, setSelectedOwnerId] = useState(null); 
    const [deactivatedOwners, setDeactivatedOwners] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedOwners, setSelectedOwners] = useState([]);

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

      const handleLockAll = async () => {
        const selectedOwnerIds = selectedOwners;
    
        console.log(selectedOwnerIds);
    
        try {
            
            console.log('Request Body:', JSON.stringify(selectedOwnerIds));

            const response = await customFetch(`/admin/deactivateAccounts`, {
                method: 'PATCH',
                headers: {Authorization: `Bearer ${admin.token}`},
                body: JSON.stringify(selectedOwnerIds)
            });
    
            console.log('Response status:', response.status);
            const responseData = await response.text();
            console.log('Response data:', responseData);

            if (response.ok) {
                const responseData = await response.text();
                toast.success("Account deactivated Successfully");
                console.log('Deactivation response:', responseData);
            } else {
                throw new Error('Network response was not ok.');
            }
        } catch (error) {
            toast.error(`${error.response.message}`);
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


    const handleLockClick = (id, isDeactivated) => {
        setSelectedOwnerId(id); 
        if (isDeactivated) {
            activateAccount(id);
        } else {
            deactivateAccount(id);
        }
    };

    const deactivateAccount = async (ownerId) => {
        try {
            const response = await customFetch(`/admin/deactivateAccount/${ownerId}`, {
                method: 'PATCH',
                headers: { Authorization: `Bearer ${admin.token}` }
            });
            toast.success("Account deactivated Successfully");
            console.log(response);
            navigate("/business-owner");
        } catch (error) {
            toast.error(`${error.response.data}`);
            console.error('Error:', error);
        }
    };

    const activateAccount = async (ownerId) => {
        try {
            const response = await customFetch(`/admin/activateAccount/${ownerId}`, {
                method: 'PATCH',
                headers: { Authorization: `Bearer ${admin.token}` }
            });
            toast.success("Account activated Successfully");
            console.log(response);
            navigate("/business-owner");
        } catch (error) {
            toast.error(`${error.response.data}`);
            console.error('Error:', error);
        }
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
                        <th>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectAll} 
                                         onChange={handleSelectAll}
                                         />
                                    </label>
                        </th>
                        <th className="text-center">Profile</th>
                        <th className="text-center">Full Name</th>
                        <th className="text-center">Status</th>
                        {selectedOwners.length > 0 && ( 
                            <th className="text-center">
                                <button onClick={handleLockAll} className="btn btn-error &&  btn-sm" 
                                >
                                                <LockClosedIcon className='w-4 h-4' />
                                                <span className="ml-1">Lock</span>
                                </button>
                            </th>
                        )}
                    </tr>
                </thead>
                <tbody>
                    {businessOwners.map((owner) => {
                        const { firstName, lastName, email, avatarUrl, username, id, _deactivated, active,fullName } = owner;
                        const dialogId = `my_modal_${id}`; 
                        return (
                            <tr key={id}>
                                <th>
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
            <div class="flex items-center">
            <div class="h-2.5 w-2.5 rounded-full bg-green-400 mr-2"></div>  ONLINE
            </div>
                    ) : (
            <div class="flex items-center">
            <div class="h-2.5 w-2.5 rounded-full bg-red-500 mr-2"></div>  OFFLINE
            </div> 
               )}
    </div>
</td>

                                <td>
                                    <button 
                                        className={`btn btn-${_deactivated ? 'success' : 'error'} btn-sm`}
                                        onClick={() => {
                                            document.getElementById(dialogId).showModal(); 
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

                                    <dialog id={dialogId} className="modal modal-bottom sm:modal-middle">
                                        <div className="modal-box flex flex-col items-center justify-center">
                                            <div className="text-red-600 flex items-center justify-center mb-4">
                                                <ExclamationCircleIcon className='w-11 h-11'/>
                                            </div>
                                            <p className="py-4 text-center text-lg font-bold">
                                                Are you sure you want to {_deactivated ? 'unlock' : 'lock'} this account?
                                            </p>
                                            <div className="modal-action">
                                                <form method="dialog">
                                                    <button 
                                                        className={`btn btn-${_deactivated ? 'success' : 'error'} btn`}
                                                        onClick={() => handleLockClick(id,_deactivated)}
                                                    >
                                                        Yes, I'm sure
                                                    </button>
                                                    <span className="mx-2"></span>
                                                    <button className="btn">No, Cancel</button>
                                                </form>
                                            </div>
                                        </div>
                                    </dialog>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default BusinessOwnerList;
