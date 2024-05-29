import React from 'react';
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { CampaignList, PaginationContainer, SearchFilter } from ".";

import { useLoaderData, redirect } from 'react-router-dom';

export const loader = (store) => async ({ request}) => {
    const admin = store.getState().adminState.admin;
try {
    const params = Object.fromEntries([
        ...new URL(request.url).searchParams.entries(),
    ]);
    console.log(params);
    
    const { sortOrder, searchKey, page } = params;

    const response = await customFetch("/tables/campagnes", {
        params: {
            searchKey: searchKey,
            sortOrder: sortOrder,
            page: page 

        },
        headers: { Authorization: `Bearer ${admin.token}` }
    });

    console.log(response.data.meta);
    
    return { campaigns: response.data.data, params, meta: response.data.meta };

} catch (err) {
    console.log(err);
    const errMessage = err?.response?.data?.message || err?.response?.data || "Server Failed To load Business Owners Table";
    toast.error(errMessage);

    return redirect("/");
}
}

const Campaigns = () => {
    return (
        <>
            <div className="flex w-full justify-center mb-8">
            <SearchFilter/>

            </div>
            
            <CampaignList/>

            <div className="flex w-full justify-center mb-3">
            <PaginationContainer/>
            </div>


        </>

    );
}

export default Campaigns;
