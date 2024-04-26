import React, { useState, useEffect } from "react";
import { useLoaderData, Link } from "react-router-dom";
import { ChevronDownIcon } from '@heroicons/react/solid';

const BusinessList = () => {
    const { businesses, params } = useLoaderData();
    const [businessNameSort, setBusinessNameSort] = useState({ ascending: true });
    const [ownerSort, setOwnerSort] = useState({ ascending: true });
    const [createdDateSort, setCreatedDateSort] = useState({ ascending: false });

    useEffect(() => {
        // Set the default sorting when the component mounts
        setCreatedDateSort({ ascending: true });
    }, []);

    if (!businesses || businesses.length < 1) {
        return (
            <div className="font-bold mx-auto text-4xl text-center text-error">
                There are no businesses matching the search criteria.
            </div>
        );
    }

    const formatCreatedDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
    };

    const toggleBusinessNameSort = () => {
        setBusinessNameSort({ ascending: !businessNameSort.ascending });
    };

    const toggleOwnerSort = () => {
        setOwnerSort({ ascending: !ownerSort.ascending });
    };

    const toggleCreatedDateSort = () => {
        const newSortOrder = createdDateSort.ascending ? 'desc' : 'asc';
        setCreatedDateSort({ ascending: !createdDateSort.ascending });
        fetchSortedBusinesses('createdDate', newSortOrder);
    };
 
    const fetchSortedBusinesses = async (sortField, sortOrder) => {
        try {
            const response = await customFetch("tables/business", {
                params: {
                    searchKey: params.searchKey,
                    sortOrder: sortOrder,
                    sortField: sortField,
                    page: params.page
                },
                headers: { Authorization: `Bearer ${admin.token}` }
            });
    
            // Update the state with the sorted businesses
            setBusinesses(response.data.businesses);
        } catch (err) {
            console.error('Error fetching sorted businesses:', err);
            // Handle error
        }
    };


    const sortedBusinesses = () => {
        if (businesses.length === 0) return [];

        let sorted = [...businesses];

        if (businessNameSort.ascending) {
            sorted.sort((a, b) => a.businessName.localeCompare(b.businessName));
        } else {
            sorted.sort((a, b) => b.businessName.localeCompare(a.businessName));
        }

        if (ownerSort.ascending) {
            sorted.sort((a, b) => a.user.fullName.localeCompare(b.user.fullName));
        } else {
            sorted.sort((a, b) => b.user.fullName.localeCompare(a.user.fullName));
        }

        if (createdDateSort.ascending) {
            sorted.sort((a, b) => new Date(b.createdDate) - new Date(a.createdDate));
        } else {
            sorted.sort((a, b) => new Date(a.createdDate) - new Date(b.createdDate));
        }

        return sorted;
    };

    return (
        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
                {/* table header */}
                <thead>
                    <tr>
                        <th></th>
                        <th className="cursor-pointer" onClick={toggleBusinessNameSort}>
                            <div className="flex items-center">
                                Business Name
                                <ChevronDownIcon className={`w-4 h-4 ml-1 text-gray-800 dark:text-black ${businessNameSort.ascending ? '' : 'transform rotate-180'}`} />
                            </div>
                        </th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th className="cursor-pointer" onClick={toggleOwnerSort}>
                            <div className="flex items-center">
                                Owner
                                <ChevronDownIcon className={`w-4 h-4 ml-1 text-gray-800 dark:text-black ${ownerSort.ascending ? '' : 'transform rotate-180'}`} />
                            </div>
                        </th>
                        <th className="cursor-pointer">
                            <div className="flex items-center" onClick={toggleCreatedDateSort}>
                                Created Date
                                <ChevronDownIcon className={`w-4 h-4 ml-1 text-gray-800 dark:text-black ${createdDateSort.ascending ? '' : 'transform rotate-180'}`} />
                            </div>
                        </th>
                        <th className="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                    {sortedBusinesses().map((business) => {
                        const { businessName, email, phone, createdDate, id, user } = business;
                        return (
                            <tr key={id}>
                                <td>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox" className="checkbox" />
                                    </label>
                                </td>
                                <td><div className="font-bold">{businessName}</div></td>
                                <td>{email}</td>
                                <td>{phone}</td>
                                <td><div className="font-bold">{user.fullName}</div></td>
                                <td>{formatCreatedDate(createdDate)}</td>
                                <td>
                                    <Link to={`/business/${id}`} className="btn btn-ghost btn-xs">Details</Link>
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
