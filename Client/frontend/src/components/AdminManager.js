import {toast} from 'react-toastify'
import { customFetch } from '../utils'
import {Plus } from 'lucide-react'
import { AdminsList ,CreateAdmin , SearchFilter } from "../components"
import { useLoaderData } from 'react-router-dom'

export const loader =(store)=> async()=>{
  const admin = store.getState().adminState.admin;
  try{
    const response = await customFetch("/super-admin/admins" ,{
      headers: { Authorization: `Bearer ${admin.token}` } 
    })
    console.log(response.data)
    return {admins: response.data}
    
  }
  catch(err){
    console.log(err)
    const errMessage  = err?.response?.data?.message || "Cannot load Table Admin"
    
    return  toast.error(errMessage)

    

  }

}
const AdminManager = () => {
  const {admins } = useLoaderData();
  console.log(admins)
  return (
    <>
      <div className="flex w-full justify-center mb-3">
        <SearchFilter/>
      
        {/* <button className="btn btn-active btn-accent" onClick={()=>document.getElementById('my_modal_1').showModal()}> <Plus />Add New Administator</button>
       <CreateAdmin/>  */}
      
      </div>
      <AdminsList/>    
    </>
    
  )
}

export default AdminManager