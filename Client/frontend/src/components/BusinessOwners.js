import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { BusinessOwnerList, PaginationContainer, SearchFilter } from ".";

import { useLoaderData, redirect } from 'react-router-dom';

export const loader = (store) => async ({ request }) => {
    const admin = store.getState().adminState.admin;
    try {
        const params = Object.fromEntries([
            ...new URL(request.url).searchParams.entries(),
        ]);
        console.log(params);
        const response = await customFetch("/tables/owners", {
            params,
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
        return { businessOwners: response.data.data, params, meta: response.data.meta };

    } catch (err) {
        console.log(err);
        const errMessage = err?.response?.data?.message || err?.response?.data || "Server Failed To load Business Owners Table";
        toast.error(errMessage);

        return redirect("/");

    }
}

const BusinessOwners = () => {

    return (
        <>
            <div className="flex w-full justify-center mb-3">
                <SearchFilter/>
            </div>
            
            <BusinessOwnerList/>
            <div className="flex w-full justify-center mb-3">
                <PaginationContainer/>
            </div>
        </>

    );
}

export default BusinessOwners;
