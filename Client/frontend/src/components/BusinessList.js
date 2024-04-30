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

    const formatCreatedDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
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
                        <th className="text-center">Business Name</th>
                        <th className="text-center">Owner</th>
                        <th className="cursor-pointer" onClick={toggleCreatedDateSort}>
                            <div className="flex items-center">
                                Created Date
                                <ChevronDownIcon className={`w-4 h-4 ml-1 text-gray-800 dark:text-black ${!createdDateSort.ascending ? '' : 'transform rotate-180'}`} />
                            </div>
                        </th>
                        <th className="text-center">Action</th>
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
                            <td>{formatCreatedDate(business.createdDate)}</td>
                            <td>
                                <Link to={`/business/${business.id}`} className="btn btn-ghost btn-xs">Details</Link>
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
