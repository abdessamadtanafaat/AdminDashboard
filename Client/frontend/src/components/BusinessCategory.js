import {useState} from 'react'
import {Trash , Pen , Plus ,X} from 'lucide-react'
import {FormInput} from '../components'
import ServiceAreaLogo from '../assets/32658b2f-c72d-43a2-9a63-0813f3c339da.png'
import { customFetch } from '../utils'
import { useSelector } from 'react-redux'
import { selectAdmin } from '../features/admin/adminSlice'
import {toast} from 'react-toastify'
const BusinessCategory = ({categoryId , name  ,description  , businessTypes}) => {
  const {token}= useSelector(selectAdmin)
  const [show , setShow] = useState(false);
  const [businessTypeName , setBusinessTypeName] = useState(null); 
  const [checkedBusiness , setCheckedBusiness] = useState([])
  const [business ,setBusiness] = useState(businessTypes || [])
  const [categoryName , setCategoryName] = useState(name);
  const [categoryDesc , setCategoryDesc] = useState(description)
  const [updatedName , setUpdatedName] =useState(categoryName)
  const [updatedDesc , setUpdatedDesc] =useState(categoryDesc)
  const deleteBusinessType = async()=>{
    try{
      const response = await customFetch.delete(`/config/business-type?businessIds=${checkedBusiness.join(',')}`,{  headers: {
        Authorization: `Bearer ${token}`} 
      })
      setBusiness([...business.filter(busines=>!checkedBusiness.includes(busines.id))])
      document.getElementById(`delete_modal_${categoryId}`).close()
      setCheckedBusiness([])
      
      console.log(response)
      return toast.success("Busineses Types Deleted ")

    }
    catch(err){
      console.log(err);
      const errorMessage = err?.response?.data || "Failed To delete Business Types !!!"
      document.getElementById(`delete_modal_${categoryId}`).close()
      return toast.error(errorMessage)

    }

  }
  const updateBusinessType = async()=>{
    
    try{
      const response = await customFetch.patch('/config/business-category',{
        categoryId :categoryId , 
        name:updatedName , 
        description :updatedDesc
  
      } ,{
        headers: { Authorization: `Bearer ${token}`} 
      })
      setCategoryName(updatedName)
      
      setCategoryDesc(updatedDesc)
      document.getElementById(`update_modal_${categoryId}`).close();
      return toast.success("Service Category Updated")

    }
    catch(err){
      console.log(err);
      const errorMessage = err?.response?.data?.message || err?.response?.data ||"Failed to update Service Category"
      document.getElementById(`update_modal_${categoryId}`).close();
      return toast.error(errorMessage)

    }

  }
  const createBusinessType = async()=>{
    try{
      const response = await customFetch.post("/config/business-type",
      {name  :businessTypeName , businessCategoryId : categoryId},
      {
        headers: { Authorization: `Bearer ${token}`} 
      })
      console.log(response.data)
      setBusinessTypeName('')
      setBusiness([response.data , ...business])
      setShow(false)
      return toast.success("Business Type Added") 

    }
    catch(err){
      console.log(err)
      setBusinessTypeName('')
      const errorMessage= err?.response?.data?.message || err?.response?.data || "Failed To add Business Type"
      return toast.error(errorMessage) 

    }

  }
  const handleCheckboxChange = (typeId) => {
    
    if (checkedBusiness.includes(typeId)) {
      setCheckedBusiness([...checkedBusiness.filter((item) => item!== typeId)])
    } else {
      setCheckedBusiness([...checkedBusiness,typeId])
    }
   
  };
  return (
    <div id={`businessCategory_${categoryId}`} className="shadow-md w-full h-50 p-2 rounded-lg flex flex-col carousel-item justify-between gap-3 border border-outline border-primary">
      <div className="flex justify-between gap-2 p-2">
        <div className="">
          <h2 className="font-bold">{categoryName}</h2>
          <p className="text-slate-500">{categoryDesc}</p>
        </div>
        <div className={`flex justify-center`}>
                  
                <button
                    onClick={()=>document.getElementById(!checkedBusiness.length <1 ?`delete_modal_${categoryId}` :`update_modal_${categoryId}`).showModal()} className={`${!checkedBusiness.length < 1 ? "btn-error" : "btn-success"} btn  btn-square  btn-sm  `}>
                        {!checkedBusiness.length<1 ? <Trash/> : <Pen />}
                    </button>
                    <dialog id={`delete_modal_${categoryId}`} className="modal">
                    <div className="modal-box">
                    <form method="dialog">
                    {/* if there is a button in form, it will close the modal */}
                    <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={()=>document.getElementById(`delete_modal_${categoryId}`).close()}>✕</button>
                    </form>
                    <h3 className="font-bold text-lg">Are you Sure ?</h3>
                    <div className="">
                        <p>You have selected {checkedBusiness.length} Services Area to be deleted</p>
                    </div>
                    <div className="mt-4 flex justify-center mx-auto" onClick={deleteBusinessType}>
                        <button className="btn-error btn" >
                            Delete
                        </button>
                    </div>
                    </div>
                    </dialog>           
                    <dialog id={`update_modal_${categoryId}`} className="modal">
                    <div className="modal-box">
                    <form method="dialog">
                    {/* if there is a button in form, it will close the modal */}
                    <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={()=>document.getElementById(`update_modal_${categoryId}`).close()}>✕</button>
                    </form>
                    <h3 className="font-bold text-lg">Update Service Category</h3>
                    <div className="my-5 mx-auto flex flex-col justify-center   gap-3">
                        <div className="w-2/3">
                            <FormInput label="Name" type="text" name="name" value={updatedName} onChange={(e)=>setUpdatedName(e.target.value)} placeholder="Enter Name for Privilege"/>

                        </div>
                        <div className='form-control '>
                            <label className='label'>
                            <span className='label-text capitalize font-semibold'>Description</span>
                            </label>
                            <textarea  className="textarea textarea-bordered textarea-xs text-md w-full max-w-xs  textarea-accent" value={updatedDesc} onChange={(e)=>setUpdatedDesc(e.target.value)}  placeholder="Bio"></textarea>
      
                        </div>
                
        
                        {/* <div className='form-control '>
                            <label className='label'>
                            <span className='label-text capitalize font-semibold'>Description</span>
                            </label>
                            <textarea  className="textarea textarea-bordered textarea-xs text-md w-full max-w-xs  textarea-accent" value={updatedDesc} onChange={(e)=>setUpdatedDesc(e.target.value)}  placeholder="Bio"></textarea>
      
                        </div> */}
                    </div>
                    <div className="mt-4 flex justify-center mx-auto">
                        <button onClick={updateBusinessType} className="btn-success btn" >
                            Save Changes
                        </button>
                    </div>
                    </div>
                    </dialog> 

               </div>

      </div>
      <div className={`w-full pr-3 h-40 overflow-y-auto border-primary scrollbar-track-base-content
      scrollbar-thumb-base-100 p-1
      scrollbar-thin flex flex-col gap-2 ${!business.length<1 || ""} `}>
        {!business.length<1 || (
                    <h3 className="text-center font-bold text-bg-info text-xl">No Business Type In This Category </h3>
        )}
        {
          !business || business.map((type)=>{
            const {id , typeName } = type
            return <div key={id}className="w-full p-2 bg-base-200   rounded-md shadow-md
            flex justify-between  gap-5 ">
              <img src={ServiceAreaLogo} alt="" className="w-7 rounded-full" />
              <h3 className="font-semibold capitalize ">{typeName}</h3>
              <input 
                type="checkbox"  class="checkbox checkbox-md"
                checked={checkedBusiness.includes(id)} onChange={(event) => handleCheckboxChange(id)} />
              
            </div>


          })
        }
        


      </div>
      <div className={`w-full`}>
                { show ? (
                    <input type="text"  class="input input-bordered input-primary  input-md w-2/3 max-w-xs join-item" placeholder="Type Business Type to add" value={businessTypeName} onChange={(e)=>setBusinessTypeName(e.target.value)} />

                ): (<button onClick={(event)=>setShow(!show)} className="btn btn-primary  btn-sm w-auto">
                <Plus/>Business Type
                </button>)}
                    
                    

      </div>
      <div className={show ||`hidden`}>
        <div className="flex justify-start items-center gap-3">
                    <button onClick={createBusinessType}className="btn-primary btn btn-square btn-sm"><Plus/></button>
                    <button onClick={(event)=>{setShow(false);setBusinessTypeName(null)}}  className="btn btn-circle btn-sm ">
                        <X />
                    </button>

        </div>
                    
                    
                    
      </div>
      
      
      
    </div>
  )
}

export default BusinessCategory
