import {useState} from 'react'
import { FormInput , SubmitBtn ,RolesList ,GrantedRolesList } from "../components"
import {toast} from 'react-toastify'
import { customFetch } from '../utils'
import { useLoaderData , redirect, useNavigate } from 'react-router-dom'
import { selectAdmin } from '../features/admin/adminSlice'
import { useSelector } from 'react-redux'
import { useItemsContext } from '../components/ItemContext'
export const loader =(store)=>async({params})=>{

    const admin = store.getState().adminState.admin;

    try{
        const response = await customFetch(`/super-admin/admin-details`,{params : params, 
            headers: { Authorization: `Bearer ${admin.token}` } 
        })
        const adminData = response.data.admin ; 
        console.log(adminData)
        const roles= adminData.roles ;
        const systemRoles = response.data.predefinedRoles
        return {entity : adminData , items  :roles ,systemItems :systemRoles }

    }
    catch(err){
        console.log(err);
        const errorMessage= err?.response?.data || "Cannot Load Admin with "
        toast.error(errorMessage)
        
        return {admin :{} , roles : [] ,systemRoles:[] }
        
    }
}
const SingleAdmin = () => {
    const navigate = useNavigate()
    const {predefinedItems ,grantedItems ,setPredefinedItems , setGrantedItems ,checkedPredefinedItems  , setCheckedPredefinedItems , checkedGrantedItems , setCheckedGrantedItems , handleGrantButtonClick ,handleRevokeAllButtonClick ,handleRevokeButtonClick }=useItemsContext();
    const {entity} = useLoaderData();
    const {token} = useSelector(selectAdmin)
    if(!entity ){
        return (<div className="font-bold mx-auto  text-4xl text-center text-error">There is no match for the keyword You Typed !!! </div>)

    } 
  const {id , firstname , lastname , email } = entity ;
 

  const updateAdmin =async (event)=>{
    event.preventDefault();
    entity.roles = grantedItems;
    try{          
        const response = await customFetch.patch("/super-admin/update-admin" , {adminId : entity.id , roles: grantedItems} , {
        headers: { Authorization: `Bearer ${token}`} 
        } )
        console.log(response.data)

        toast.success("Admin Updated Successfully")
        return navigate(`/admins?searchKey=${response.data.firstname}`)
      }
      catch(err){
        console.log(err)
          const errorMessage =err?.response?.data ||  "Server Failed To Update Admin"
          return toast.error(errorMessage) 
      }

  }


  return (
    <div className="grid place-content-center  gap-3 ">
      <div className="my-5 mx-auto flex justify-center md:flex-row  gap-3">
        <FormInput value={email}  label="Email" type="email" name="email" placeholder="Enter Email"/>
        <FormInput value={firstname}  label="First Name" type="text" name="firstname" placeholder="Jhon"/>
        <FormInput value={lastname}  label="Last Name" type="text" name="lastname" placeholder="Smith"/>
      </div>
      <div className="flex justify-between gap-4 w-7/8 max-w-4xl max-h-1/3 overflow-y-auto">
        <div className={`w-60 h-80 bg-base-200 border-2 rounded-lg border-outline border-success ${!predefinedItems.length <1 || "grid place-content-center text-center"}`}>{!predefinedItems.length < 1 ? <RolesList /> :<p className="text-success">No Roles Available</p> }</div>
        
        <div className="flex-col flex justify-evenly place-items-center">
          <button className="btn btn-secondary text-base-content w-20 px-3 " onClick={handleGrantButtonClick}>Grant
          </button>
          <button className="btn btn-error text-base-content  w-20" onClick={handleRevokeButtonClick}>Revoke
          </button>
          <button className="btn btn-error text-base-content  w-20" onClick={handleRevokeAllButtonClick}>Revoke All
          </button>
          </div>
            <div className={`w-60 h-80 bg-base-200 border-2 rounded-lg border-outline border-info ${!grantedItems.length <1 || "grid place-content-center text-center"}`}>{!grantedItems.length < 1 ? <GrantedRolesList /> :<p className="text-info">No Roles Granted </p> }</div>
      </div>
      <div className="mx-auto flex justify-center gap-3">
        
        <div onClick={updateAdmin}  className='mt-4'>
            <SubmitBtn text="Save Changes"/>
        </div>
        
      </div>

    </div> 
    
  )
}

export default SingleAdmin
