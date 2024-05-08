import React, { useState, useEffect } from "react";
import { useLoaderData, Link } from "react-router-dom";
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
                        <th className="text-center">Business Name</th>
                        <th className="text-center">Template</th>
                        <th className="text-center">Created Date</th>
                        <th className="text-center">Services area</th>
                        <th className="text-center">Langues</th>
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
            <td>{campaign.business.businessName}</td>
            <td>{campaign.template.templateName}</td>
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
