import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { BusinessList, PaginationContainer, SearchFilter } from ".";

import { useLoaderData, redirect } from 'react-router-dom';

export const loader = (store) => async ({ request }) => {
    const admin = store.getState().adminState.admin;
try {
    const params = Object.fromEntries([
        ...new URL(request.url).searchParams.entries(),
    ]);
    console.log(params);
    
    // Include sortOrder and page parameters in the request
    const { sortOrder, page, ...otherParams } = params;

    const response = await customFetch("tables/business", {
        params: {
            searchKey: otherParams.searchKey,
            sortOrder: sortOrder,
            page: page
        },
        headers: { Authorization: `Bearer ${admin.token}` }
    });

    console.log(response.data);
    
    // Return the response data along with params and meta
    return { businesses: response.data.data, params, meta: response.data.meta };

} catch (err) {
    console.log(err);
    const errMessage = err?.response?.data?.message || err?.response?.data || "Server Failed To load Business Owners Table";
    toast.error(errMessage);

    return redirect("/");
}
}

const Business = () => {
    return (
        <>
            <div className="flex w-full justify-center mb-3">
                <SearchFilter/>
            </div>
            <BusinessList/>
            <div className="flex w-full justify-center mb-3">
                <PaginationContainer/>
                </div>
        </>

    );
}

export default Business;
