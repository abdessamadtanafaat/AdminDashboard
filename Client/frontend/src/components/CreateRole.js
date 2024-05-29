import { customFetch } from "../utils";
import {toast} from 'react-toastify'
import {redirect, useNavigate} from 'react-router-dom'

import { FormInput , SubmitBtn , PrivilegesList , GrantedPrivilegesList} from '../components'
import { useSelector } from "react-redux";
import { selectAdmin } from "../features/admin/adminSlice";
import { useState } from "react";
import { useItemsContext } from "./ItemContext";
import {MoveRight , MoveLeft } from 'lucide-react'
export const loader =(store)=>async({})=>{
    const admin = store.getState().adminState.admin;
    try{

        const response = await customFetch("/super-admin/privileges" ,{
        headers: { Authorization: `Bearer ${admin.token}` } 
        
        })
        console.log(response.data)
    
        return {systemItems: response.data ,items:[] }

  }
  catch(err){
    const errMessage  = err?.response?.data?.message || err?.response?.data || "Server Failed To load Admin Table"
    //toast.error(errMessage);
    console.log(errMessage);
    //return redirect("/ErrorElement")
    if(errMessage){
      const accessDeniedMessage = "Sorry, You don't have permission to access this page.";
      throw Error(accessDeniedMessage);    
  }

  }

}
const CreateRole = () => {
    const navigate = useNavigate();
    const [name ,setName] = useState('');
    const [desc , setDesc] = useState('')
    const [privilegeName, setPrivilegeName]= useState('')
    const [privilegeDesc, setPrivilegeDesc]= useState('')
   
    const {predefinedItems ,setPredefinedItems, setGrantedItems  , grantedItems , handleGrantButtonClick , handleRevokeAllButtonClick , handleRevokeButtonClick} = useItemsContext()
    const {token} = useSelector(selectAdmin)
    
    const createPrivilege = async(event)=>{
        
        try{          
            const response = await customFetch.post("/super-admin/create-privilege"  ,{name :privilegeName , description :  privilegeDesc},  {
              
            headers: { Authorization: `Bearer ${token}`} 
            } )
            console.log(response.data)
            document.getElementById('my_modal_3').close();
            setPrivilegeDesc('');
            setPrivilegeName('')
            setPredefinedItems([...predefinedItems , response.data])
            
  
            return toast.success("Privilege Created Successfully")
          }
          catch(err){
            console.log(err)
            const errorMessage =err?.response?.data ||  "Server Failed To Create Role"
            document.getElementById('my_modal_3').close();
            return toast.error(errorMessage) 
          }
    }

    const createRole =async (event)=>{
      event.preventDefault();
        
      try{          
          const response = await customFetch.post("/super-admin/create-role"  ,{name  , description : desc  , privileges : grantedItems},  {
          headers: { Authorization: `Bearer ${token}`} 
          } )
          console.log(response.data)

          toast.success("Role Created Successfully")
          return navigate(`/admin/create-admin`)
        }
        catch(err){
          console.log(err)
          const errorMessage =err?.response?.data ||  "Server Failed To Create Role"
          
          return toast.error(errorMessage) 
        }

    }
  return (
  
    <div className="grid relative place-content-center  gap-3 ">
       <div className="absolute -top-10 right-0">
            <button className="btn btn-info text-base-content" onClick={()=>document.getElementById('my_modal_3').showModal()}>Create Privilege</button>
            <dialog id="my_modal_3" className="modal">
            <div className="modal-box">
            <form method="dialog grid place-content-center">
      
            <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={(event)=>{event.preventDefault(); document.getElementById('my_modal_3').close()}}>✕</button>
            </form>
            <h3 className="font-bold text-lg">New Privilege</h3>
            <div className="my-5 mx-auto flex flex-col justify-center   gap-3">
                <FormInput value={privilegeName} onChange={(e)=>setPrivilegeName(e.target.value)} label="Name" type="text" name="name" placeholder="Enter Name for Privilege"/>
        
                <div className='form-control '>
                    <label className='label'>
                        <span className='label-text capitalize font-semibold'>Description</span>
                    </label>
                    <textarea  className="textarea textarea-bordered textarea-xs w-full max-w-xs  textarea-accent"  value={privilegeDesc} onChange={e=>setPrivilegeDesc(e.target.value)} placeholder="Bio"></textarea>
      
                </div>
            </div>
            <div  onClick={createPrivilege} className='mt-4'>
                <SubmitBtn text="Create Privilege"/>
            </div>
            </div>
            </dialog>


            </div>
      <div className="my-5 mx-auto flex justify-center md:flex-row  gap-3">
        <FormInput value={name} onChange={(e)=>setName(e.target.value)} label="Name" type="text" name="name" placeholder="Enter Name for Role"/>
        
        <div className='form-control '>
            <label className='label'>
                <span className='label-text capitalize font-semibold'>Description</span>
            </label>
            <textarea  className="textarea textarea-bordered textarea-xs w-full max-w-xs  textarea-accent"  value={desc} onChange={e=>setDesc(e.target.value)} placeholder="Bio"></textarea>
      
        </div>
       
      </div>
      <div className="flex justify-between gap-4 w-7/8 max-w-4xl max-h-1/3 overflow-y-auto">
        <div className={` h-80 bg-base-200 border-2 rounded-lg 
        scrollbar-track-base-100
        scrollbar-thin
        scrollbar-thumb-base-content
        border-outline border-success overflow-y-auto overflow-x-hidden ${!predefinedItems.length <1 || "grid place-content-center text-center"}`}>{!predefinedItems.length < 1 ? <PrivilegesList /> :<p className="text-success">No Privileges Available</p> }</div>
        
        <div className="flex-col  flex justify-evenly place-items-center">
          <button className="btn btn-secondary text-base-content w-full px-3 flex  justify-evenly items-center flex-nowrap" onClick={handleGrantButtonClick}><span className="">Grant</span><MoveRight />
          </button>
          <button className="btn btn-error text-base-content  w-full  px-3 flex  justify-evenly items-center flex-nowrap text-md" onClick={handleRevokeButtonClick}><MoveLeft/><span>Revoke</span> 
          </button>
          <button className="btn btn-error text-base-content  w-full flex  justify-evenly text-xs items-center flex-nowrap" onClick={handleRevokeAllButtonClick}><MoveLeft/><span>Revoke All</span> 
          </button>
        </div>
            <div className={` h-80 bg-base-200 border-2 rounded-lg 
            scrollbar-track-base-100
            scrollbar-thin
            scrollbar-thumb-base-content
            border-outline border-info overflow-y-auto overflow-x-hidden ${!grantedItems.length <1 || "grid place-content-center text-center"}`}>{!grantedItems.length < 1 ? <GrantedPrivilegesList /> :<p className="text-info">No Privileges Granted </p> }</div>
      </div>
      <div className="mx-auto flex justify-center items-center gap-3">
            
        
        <div onClick={createRole} className='mt-4'>
            <SubmitBtn text="Create"/>
        </div>
        
        
      </div>

    </div> 
  )

          
}
export default CreateRole