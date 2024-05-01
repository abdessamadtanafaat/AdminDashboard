import ServiceAreaLogo from '../assets/32658b2f-c72d-43a2-9a63-0813f3c339da.png'
import {useState} from 'react'
import {Plus , Trash , Pen ,X } from 'lucide-react'
import {FormInput ,SubmitBtn} from './index';
import { useSelector } from 'react-redux';
import { selectAdmin } from '../features/admin/adminSlice';
import { customFetch } from '../utils';
import {toast} from 'react-toastify'
const ServiceCategory = ({ id ,name , description , serviceAreas , custom}) => {
    const [show ,setShow] = useState(false);
    const [serviceName , setServiceName] = useState(null)
    const [services,setServices] =useState(serviceAreas || [])
    const [categoryName , setCategoryName]  = useState(name);
    const [categoryDesc , setCategoryDesc] = useState(description)
    
    const [checkedServices , setCheckedServices] = useState([])
    const [updatedName  ,setUpdatedName] = useState(categoryName);
    const [updatedDesc , setUpdatedDesc]= useState(categoryDesc)
    
    const {token} = useSelector(selectAdmin)
    const updateServiceCategory= async()=>{
        if(!checkNameAndDescription(updatedName , updatedDesc)){
            document.getElementById(`update_modal_${id}`).close();
            return toast.error("Name Or Description Cannot be empty")

        }
        try{
            const response = await customFetch.put('/config/service-category' ,{
                name : updatedName , 
                description : updatedDesc , 
                serviceCategoryId : id
            },{  headers: {
                Authorization: `Bearer ${token}`} 
              } 
            );
            console.log(response.data);
            setCategoryName(updatedName)
            setCategoryDesc(updatedDesc)
            
            
            document.getElementById(`update_modal_${id}`).close();
            return toast.success("Service Category Updated")
        }
        catch(err){
            console.log(err);
            const errorMessage = err?.response?.data|| "Failed to update Service Category"
            document.getElementById(`update_modal_${id}`).close();
            return toast.error(errorMessage)
            
        }

    }
    const deleteServiceAreas =async()=>{
        try{
            const response = await customFetch.delete(`/config/service-area?serviceIds=${checkedServices.join(',')}`,{  headers: {
              Authorization: `Bearer ${token}`} 
            })
            
            setServices([...services.filter(service=>!checkedServices.includes(service.id))])
            document.getElementById(`delete_modal_${id}`).close()
            setCheckedServices([])
            
            console.log(response)
            return toast.success("Service Areas Deleted ")
        

        }
        catch(err){
            console.log(err);
            const errorMessage = err?.response?.data || "Failed To delete ServiceArea !!!"
            document.getElementById(`delete_modal_${id}`).close()
            return toast.error(errorMessage)


        }
    }
    const handleCheckboxChange = (serviceId) => {
    
        if (checkedServices.includes(serviceId)) {
          setCheckedServices([...checkedServices.filter((checkedService) => checkedService!== serviceId)])
        } else {
          setCheckedServices([...checkedServices,serviceId])
        }
       
    };
    function checkName() {
        if (serviceName  && serviceName.trim() !== '' ) {

            return true;
        } else {
            return false;
        }
    }
    function checkNameAndDescription(name ,desc) {
        if (name  && name.trim() !== '' && desc  && desc.trim() !== '') {

            return true;
        } else {
            return false;
        }
    }
    const createServiceArea = async(event)=>{
        if(!checkName(serviceName)){
            
            return toast.error("Service Name cannot be empty")

        }
        
        
        try{
            const response = await customFetch.post(`/config/service-area` ,{name :serviceName, serviceCategoryId : id},
            
            {  headers: { Authorization: `Bearer ${token}`} 
            })
            console.log(response.data)
            setServiceName('')
            
            setServices([response.data , ...services])
            setShow(false)
            
            return toast.success("Service Area Added") 

        }
        catch(err){
            console.log(err)
            setServiceName('')
            const errorMessage= err?.response?.data?.message || err?.response?.data || "Failed To add Service Area"
            return toast.error(errorMessage) 

        }
    }
    return (
        <div id={`serviceCategory_${id}`} className="shadow-md w-full h-50 p-2 rounded-lg flex flex-col carousel-item justify-between gap-3 border border-outline border-primary">
            <div className="flex justify-between gap-2 p-2">
                <div className="">
                    <h2 className="font-bold">{categoryName}</h2>
                    <p className="text-slate-500">{categoryDesc}</p>
                </div>    
                <div className={`flex justify-center ${!custom || "hidden"} `}>
                  
                    <button
                    onClick={()=>document.getElementById(!checkedServices.length <1 ?`delete_modal_${id}` :`update_modal_${id}`).showModal()} className={`${!checkedServices.length < 1 ? "btn-error" : "btn-success"} btn  btn-square  btn-sm  `}>
                        {!checkedServices.length<1 ? <Trash/> : <Pen />}
                    </button>
                    <dialog id={`delete_modal_${id}`} className="modal">
                    <div className="modal-box">
                    <form method="dialog">
                    {/* if there is a button in form, it will close the modal */}
                    <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={()=>document.getElementById(`delete_modal_${id}`).close()}>✕</button>
                    </form>
                    <h3 className="font-bold text-lg">Are you Sure ?</h3>
                    <div className="">
                        <p>You have selected {checkedServices.length} Services Area to be deleted</p>
                    </div>
                    <div className="mt-4 flex justify-center mx-auto" onClick={deleteServiceAreas}>
                        <button className="btn-error btn" >
                            Delete
                        </button>
                    </div>
                    </div>
                    </dialog>           
                    <dialog id={`update_modal_${id}`} className="modal">
                    <div className="modal-box">
                    <form method="dialog">
                    {/* if there is a button in form, it will close the modal */}
                    <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={()=>document.getElementById(`update_modal_${id}`).close()}>✕</button>
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
                    </div>
                    <div className="mt-4 flex justify-center mx-auto">
                        <button onClick={updateServiceCategory} className="btn-success btn" >
                            Save Changes
                        </button>
                    </div>
                    </div>
                    </dialog> 

               </div>
            </div>
            <div className={`w-full pr-3 h-40 overflow-y-auto border-primary scrollbar-track-base-content
            scrollbar-thumb-base-100 p-1
            scrollbar-thin flex flex-col gap-2 ${!services.length<1 || ""}  `}>
                {!services.length<1 || (
                    <h3 className="text-center font-bold text-bg-info text-xl">{!custom ?"No Service Area Created "  :"No Customized Service Area Created By Clients"}Yet </h3>
                )}

                {!services || services.map((serviceArea)=>{
                    const { name } = serviceArea
                    const serviceId= serviceArea.id
                    return (
                        <div key={serviceId}className="w-full p-2 bg-base-200   rounded-md shadow-md
                        flex justify-between  gap-5 ">
                            <img src={ServiceAreaLogo} alt="" className="w-7 rounded-full" />
                            <h3 className="font-semibold capitalize ">{name}</h3>
                            <input 
                            type="checkbox"  class="checkbox checkbox-md"
                            
                            checked={checkedServices.includes(serviceId)} onChange={(event) => handleCheckboxChange( serviceId)} />

                        </div>
                    )

                })}
                
                
            </div>
            <div className={`w-full ${!custom || "hidden" }`}>
                { show ? (
                    <input type="text"  class="input input-bordered input-primary  input-md w-2/3 max-w-xs join-item" placeholder="Type Service Name to add" value={serviceName} onChange={(e)=>setServiceName(e.target.value)} />

                ): (<button onClick={(event)=>setShow(!show)} className="btn btn-primary  btn-sm w-auto">
                <Plus/>Service Area
                </button>)}
                    
                    

            </div>
            <div className={show ||`hidden`}>
                    
                    <div className="flex justify-start items-center gap-3">
                    <button onClick={createServiceArea}className="btn-primary btn btn-square btn-sm"><Plus/></button>
                    <button onClick={(event)=>{setShow(false);setServiceName(null)}}  className="btn btn-circle btn-sm ">
                        <X />
                    </button>

                    </div>
                    
                    
                    
            </div>
            

        </div>
    )

}

export default ServiceCategory
