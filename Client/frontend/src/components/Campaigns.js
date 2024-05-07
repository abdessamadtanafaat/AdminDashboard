import React from 'react';
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { CampaignList, PaginationContainer, SearchFilter } from ".";

import { useLoaderData, redirect } from 'react-router-dom';

export const loader = (store) => async ({ request }) => {
    const admin = store.getState().adminState.admin;
    try {
        const params = Object.fromEntries([
            ...new URL(request.url).searchParams.entries(),
        ]);
        console.log(params);
        const response = await customFetch("/tables/campagnes", { 
            params,
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
        return { campaigns: response.data.data, params, meta: response.data.meta }; 
    } catch (err) {
        console.log(err);
        const errMessage = err?.response?.data?.message || err?.response?.data || "Server Failed To load Campaigns Table";
        toast.error(errMessage);

        return redirect("/");

    }
}

const Campaigns = () => {
    return (
        <>
            <div className="flex w-full justify-center mb-3">
            <SearchFilter/>

            </div>
            
            {/* Render the CampaignList component passing the campaigns data */}
            <CampaignList/>

            <div className="flex w-full justify-center mb-3">
            <PaginationContainer/>
            </div>


        </>

    );
}

export default Campaigns;