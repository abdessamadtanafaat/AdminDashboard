import React, { useState, useEffect } from "react";
import { useLoaderData, Link, useNavigate } from "react-router-dom";
import { useSelector } from 'react-redux';
import { customFetch } from '../utils';
import { ArrowUpDown,Languages,User } from "lucide-react";

const CampaignList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const { campaigns, params } = useLoaderData();
    const [campaignsData, setCampaignsData] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedCampaigns, setSelectedCampaigns] = useState([]);

    const history = useNavigate();
    const initialSortOrder = localStorage.getItem('sortOrder') || 'asc';
    const [createdDateSort, setCreatedDateSort] = useState({ ascending: false });


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

 

    // const fetchCampaigns = async () => {
    //     try {
    //         const response = await customFetch("/tables/campagnes", {
    //             params: {
    //                 searchKey: params.searchKey,
    //                 page: params.page
    //             },

    //             headers: { Authorization: `Bearer ${admin.token}` }
    //         });
    //         setCampaignsData(response.data);
    //         console.log(response.data);
    //     } catch (error) {
    //         console.error('Error fetching campaigns:', error);
    //     }
    // };

    const handleSelectAll = () => {
        setSelectAll(!selectAll); 
        // if (!selectAll) {
        //     setSelectedCampaigns(campaignsData.map(campaign => campaign.id));
        // } else {
        //     setSelectedCampaigns([]);
        // }
    };

    const handleLanguageClick = ()=> {



    }
    const handleSelectCampaign = (campaignId) => {
        if (selectedCampaigns.includes(campaignId)) {
            setSelectedCampaigns(selectedCampaigns.filter(id => id !== campaignId));
        } else {
            setSelectedCampaigns([...selectedCampaigns, campaignId]);
        }
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
    const toggleCreatedDateSort = async () => {
        setCreatedDateSort(prevSort => { 
            const newSort = { ascending: !prevSort.ascending };

            localStorage.setItem('sortOrder', newSort.ascending ? 'asc' : 'desc');


            const sortOrderParam = newSort.ascending ? 'asc' : 'desc';
            const queryParams = new URLSearchParams({ sortOrder: sortOrderParam });
            history(`/campaign?${queryParams.toString()}`);

            return newSort;
        });

        const response = await customFetch("/tables/campagnes", {
            params: {
                searchKey: params.searchKey,
                sortOrder: createdDateSort.ascending ? 'asc' : 'desc',
                page: params.page
            },
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
    };

    if (!campaigns || campaigns.length < 1) {
        return (
            <div className="font-bold mx-auto text-4xl text-center text-error">
                There is no match for the keyword You Typed !!!
            </div>
        );
    }

    return (
        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
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
                        <th className="text-center">Campaign Name</th>
                        <th className="text-center">Business Name</th>
                        <th className="text-center">Template</th>
                        <th style={{ cursor: 'pointer' }} onClick={toggleCreatedDateSort}>
                            <div className="flex items-center">
                                Created Date
                                <ArrowUpDown className={`w-4 h-4 ml-1 text-gray-800 dark:text-black`} />

                            </div>
                        </th>
                        {/* <th className="text-center">Services area</th> */}
                        <th className="text-center">Langues</th>
                    </tr>
                </thead>
                <tbody>
                {Array.isArray(campaigns) && campaigns.map((campaign) => (
        <tr key={campaign.id}>
            <th>
                <label className="flex justify-center gap-2">
                    <input
                        type="checkbox"
                        className="checkbox"
                        checked={selectedCampaigns.includes(campaign.id)}
                        onChange={() => handleSelectCampaign(campaign.id)}
                    />
                </label>
            </th>
            <td>{campaign.campaignName}</td>
            <td>{campaign.business.businessName}</td>
            <td>{campaign.template.templateName}</td>
            <td>{formatDateDuration(campaign.createdDate)}</td>
            <td style={{ textAlign: 'center' }}>
                                    <button className='btn btn-success btn-sm'
                                        onClick={() => {
                                            handleLanguageClick()
                                                                                }}
                                    >
                                    <Languages className='w-4 h-4' />
                                    {/* <span className="ml-1">Edit Owner</span> */}
                                    </button>
                            </td>

            {/* <td>{campaign.template.templateName}</td> */}

            {/* <td>
            <button class="btn btn-active btn-sucess && btn-sm" onClick={() => {window.location.href=`/campaign/${campaign.id}`}}>details</button>
            </td> */}
        </tr>
    ))}
</tbody>

            </table>
        </div>
    );
};

export default CampaignList;
