import React, { useState, useEffect } from "react";
import { useLoaderData, Link, useNavigate } from "react-router-dom";
import { ChevronDownIcon } from '@heroicons/react/solid';
import { useSelector } from 'react-redux';
import { customFetch } from '../utils';

const BusinessList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const { businesses, params } = useLoaderData();
    const history = useNavigate();

    const initialSortOrder = localStorage.getItem('sortOrder') || 'asc';
    const [createdDateSort, setCreatedDateSort] = useState({ ascending: false });

    const [selectAll, setSelectAll] = useState(false);
    const [selectedBusinessOwners, setSelectedBusinessOwners] = useState([]);

    useEffect(() => {
        setCreatedDateSort({ ascending: initialSortOrder === 'asc' });

        const sortOrder = localStorage.getItem('sortOrder');
        if (sortOrder) {
            setCreatedDateSort({ ascending: sortOrder === 'asc' });
        } else {
            setCreatedDateSort({ ascending: false });
            localStorage.setItem('sortOrder', 'desc');
        }

    }, [initialSortOrder]);

    const toggleCreatedDateSort = async () => {
        setCreatedDateSort(prevSort => { 
            const newSort = { ascending: !prevSort.ascending };

            localStorage.setItem('sortOrder', newSort.ascending ? 'asc' : 'desc');


            const sortOrderParam = newSort.ascending ? 'asc' : 'desc';
            const queryParams = new URLSearchParams({ sortOrder: sortOrderParam });
            history(`/business?${queryParams.toString()}`);

            return newSort;
        });

        const response = await customFetch("/tables/business", {
            params: {
                searchKey: params.searchKey,
                sortOrder: createdDateSort.ascending ? 'asc' : 'desc',
                page: params.page
            },
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
    };

    const formatDateDuration = (createdDate) => {
        const currentDate = new Date();
        const startDate = new Date(createdDate);
        const millisecondsPerSecond = 1000;
        const millisecondsPerMinute = millisecondsPerSecond * 60;
        const millisecondsPerHour = millisecondsPerMinute * 60;
        const millisecondsPerDay = millisecondsPerHour * 24;
        const millisecondsPerWeek = millisecondsPerDay * 7;
        const millisecondsPerMonth = millisecondsPerDay * 30;
        const millisecondsPerYear = millisecondsPerDay * 365;
    
        const elapsedMilliseconds = currentDate - startDate;
    
        if (elapsedMilliseconds < millisecondsPerMinute) {
            return 'Just now';
        } else if (elapsedMilliseconds < millisecondsPerHour) {
            const minutes = Math.floor(elapsedMilliseconds / millisecondsPerMinute);
            return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerDay) {
            const hours = Math.floor(elapsedMilliseconds / millisecondsPerHour);
            return `${hours} hour${hours > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerWeek) {
            const days = Math.floor(elapsedMilliseconds / millisecondsPerDay);
            return `${days} day${days > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerMonth) {
            const weeks = Math.floor(elapsedMilliseconds / millisecondsPerWeek);
            return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerYear) {
            const months = Math.floor(elapsedMilliseconds / millisecondsPerMonth);
            return `${months} month${months > 1 ? 's' : ''} ago`;
        } else {
            const years = Math.floor(elapsedMilliseconds / millisecondsPerYear);
            return `${years} year${years > 1 ? 's' : ''} ago`;
        }
    };
    

    const handleSelectAll = () => {
        setSelectAll(!selectAll); 
        if (!selectAll) {
            setSelectedBusinessOwners(businesses.map(owner => owner.id));
        } else {
            setSelectedBusinessOwners([]);
        }
    };

    const handleSelectOwner = (businessOwnerId) => {
        if (selectedBusinessOwners.includes(businessOwnerId)) {
            setSelectedBusinessOwners(selectedBusinessOwners.filter(id => id !== businessOwnerId));
        } else {
            setSelectedBusinessOwners([...selectedBusinessOwners, businessOwnerId]);
        }
    };

    return (
        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
                {/* table header */}
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
                        <th>Business Name</th>
                        <th>Owner</th>
                        <th style={{ cursor: 'pointer' }} onClick={toggleCreatedDateSort}>
                            <div className="flex items-center">
                                Created Date
                                <ChevronDownIcon className={`w-4 h-4 ml-1 text-gray-800 dark:text-black ${!createdDateSort.ascending ? '' : 'transform rotate-180'}`} />
                            </div>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {businesses.map((business) => {
                    const { businessName, lastName, email, avatarUrl, username, id, _deactivated, active,fullName } = business;
                    return (
                        <tr key={business.id}>
                                <th>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectedBusinessOwners.includes(id)} 
                                         onChange={() => handleSelectOwner(id)} 
                                          />
                                    </label>
                                </th>
                            <td>
                                <div className="font-bold">{businessName}</div>
                                <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{email}</div>
                                </td>
                            <td>
                                <div className="font-bold">{business.user.fullName}</div>
                                <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{business.user.email}</div>
                            
                            </td>
                            <td>{formatDateDuration(business.createdDate)}</td>
                            <td>
                            <button class="btn btn-active btn-sucess && btn-sm" onClick={() => {window.location.href=`/business/${business.id}`}}>details</button>
                            </td>
                        </tr>
                    );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default BusinessList;
