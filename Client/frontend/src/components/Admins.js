import {toast} from 'react-toastify'
import { customFetch } from "../utils"

import { AdminsList ,CreateAdmin , PaginationContainer, SearchFilter } from "."
import { useLoaderData ,redirect } from 'react-router-dom'

export const loader =(store)=> async({request})=>{
  const admin = store.getState().adminState.admin;
  try{
    const params = Object.fromEntries([
      ...new URL(request.url).searchParams.entries(),
    ]);
    console.log(params)
    const response = await customFetch("/super-admin/admins" ,{params,
      headers: { Authorization: `Bearer ${admin.token}` } 
    })
    console.log(response.data)
    return {admins: response.data.data , params , meta : response.data.meta}
    
  }
  catch(err){
    console.log(err)
    const errMessage  = err?.response?.data?.message || err?.response?.data || "Server Failed To load Admin Table"
    toast.error(errMessage)

    return redirect("/")
    
  }


}
const Admins = () => {
  
  return (
    <>
      <div className="flex w-full justify-center mb-3">
        <SearchFilter/>
      </div>
      <AdminsList/>
      <PaginationContainer/>
      
    </>
    
  )
}

export default Admins
