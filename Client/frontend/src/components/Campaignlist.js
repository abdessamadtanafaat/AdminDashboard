import React, { useState, useEffect } from "react";
import { useLoaderData, Link } from "react-router-dom";
import { ChevronDownIcon } from '@heroicons/react/solid';
import { useSelector } from 'react-redux';
import { customFetch } from '../utils';

const CampaignList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const { campaigns, params } = useLoaderData();
    const [campaignsData, setCampaignsData] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedCampaigns, setSelectedCampaigns] = useState([]);

    useEffect(() => {
        fetchCampaigns();
    }, []);

    const fetchCampaigns = async () => {
        try {
            const response = await customFetch("/tables/campagnes", {
                params: {
                    searchKey: params.searchKey,
                    page: params.page
                },

                headers: { Authorization: `Bearer ${admin.token}` }
            });
            setCampaignsData(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching campaigns:', error);
        }
    };

    const handleSelectAll = () => {
        setSelectAll(!selectAll); 
        if (!selectAll) {
            setSelectedCampaigns(campaignsData.map(campaign => campaign.id));
        } else {
            setSelectedCampaigns([]);
        }
    };

    const handleSelectCampaign = (campaignId) => {
        if (selectedCampaigns.includes(campaignId)) {
            setSelectedCampaigns(selectedCampaigns.filter(id => id !== campaignId));
        } else {
            setSelectedCampaigns([...selectedCampaigns, campaignId]);
        }
    };

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
                        <th className="text-center">Business ID</th>
                        <th className="text-center">Template ID</th>
                        <th className="text-center">Action</th>
                    </tr>
                </thead>
                <tbody>
                {Array.isArray(campaignsData.data) && campaignsData.data.map((campaign) => (
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
            <td>{campaign.business.id}</td>
            <td>{campaign.template.id}</td>
            <td>
                <Link to={`/campaign/${campaign.id}`} className="btn btn-ghost btn-xs">Details</Link>
            </td>
        </tr>
    ))}
</tbody>

            </table>
        </div>
    );
};

export default CampaignList;
