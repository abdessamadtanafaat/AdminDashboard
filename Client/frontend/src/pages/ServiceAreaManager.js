import { useLoaderData } from "react-router-dom";
import { customFetch } from "../utils";
import {Plus} from 'lucide-react'
import ServiceAreaLogo from '../assets/32658b2f-c72d-43a2-9a63-0813f3c339da.png'
import {FormInput ,ServiceCategory , FormSelect,SubmitBtn} from '../components'
import { useSelector } from "react-redux";
import { selectAdmin } from "../features/admin/adminSlice";
import {toast} from 'react-toastify'
import {useState} from 'react'
import {redirect } from 'react-router-dom'

export const loader = (store)=>async({})=>{
    const admin = store.getState().adminState.admin ; 
    try{
        const response = await customFetch('/config/service-categories',{
            headers: { Authorization: `Bearer ${admin.token}` } 
        })
        console.log(response.data);
        return {serviceCategories :response.data}
    }
    catch(err){
        const errorMessage = err?.response?.data ||"Failed to load the Service Categories"
        //toast.error(errorMessage)
        //return redirect("pages/ErrorElement")
        //return {serviceCategories :[]}
        if(errorMessage){
            const accessDeniedMessage = "Sorry, You don't have permission to access this page.";
            throw Error(accessDeniedMessage);    
        }


    }

}
const ServiceAreaManager = () => {
  const {serviceCategories} = useLoaderData();
  const {token} = useSelector(selectAdmin)
  const [categoryName, setCategoryName] = useState(null)
  const [categoryDesc , setCategoryDesc] = useState(null)
  const [activeCategory, setActiveCategory] = useState(!serviceCategories.length < 1 ? serviceCategories[0].id : null);

    const toggleActive = (id) => {
        setActiveCategory(activeCategory === id ? null : id);
    };
  const createServiceCategory = async()=>{
    try{
        const response = await customFetch.post('/config/service-category',{name :categoryName , description :  categoryDesc},  {
            headers: { Authorization: `Bearer ${token}`} 
        })
        setCategoryDesc('')
        setCategoryName('')

        setCategoryDesc(null)
        setCategoryName(null)
        console.log(response.data)

        serviceCategories.push(response.data)
        document.getElementById('my_modal_3').close();
        return toast.success("Service Category Created")

    }
    catch(err){
        const errorMessage =err?.response?.data?.message || err?.response?.data || "Failed to Create Service Category"
        document.getElementById('my_modal_3').close();
        return toast.error(errorMessage) 


    }
  }
  return (
    <div className="grid place-content-center items-center">
        <div className="flex  mx-auto mb-10 justify-between gap-2">
            <div>
                <button className="btn btn-info text-base-content" onClick={()=>document.getElementById('my_modal_3').showModal()}><Plus/>Service Area Category</button>
                <dialog id="my_modal_3" className="modal">
            <div className="modal-box">
            <form method="dialog grid place-content-center">
      
            <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={(event)=>{event.preventDefault(); document.getElementById('my_modal_3').close()}} >✕</button>
            </form>
            <h3 className="font-bold text-lg">Enter Information about category: </h3>
            <div className="my-5 mx-auto flex flex-col justify-center   gap-3">
                <div className="w-2/3">
                    <FormInput label="Name" type="text" name="name" value={categoryName} onChange={(e)=>setCategoryName(e.target.value)} placeholder="Enter Name for category"/>

                </div>
                
        
                <div className='form-control '>
                    <label className='label'>
                        <span className='label-text capitalize font-semibold'>Description</span>
                    </label>
                    <textarea  className="textarea textarea-bordered textarea-xs w-full max-w-xs  textarea-accent" value={categoryDesc} onChange={(e)=>setCategoryDesc(e.target.value)}  placeholder="Bio"></textarea>
      
                </div>
            </div>
            <div className='mt-4 w-1/3 mx-auto' onClick={createServiceCategory}>
                <SubmitBtn text="Create"/>
            </div>
            </div>
                </dialog>
            </div>
         
        </div>

        <div className="mx-auto carousel w-3/4  max-w-2xl p-4 flex justify-between gap-10 overflow-hidden ">
                
            
                {
                serviceCategories.length < 1 ? (<h1 className="text-center font-bold text-3xl">
                    No Service Category Available , Create New One
                </h1>) : (
                    serviceCategories.map((serviceCategory)=>{
                        const {id , name , description , serviceAreas } = serviceCategory
                        return  <ServiceCategory key={`serviceCategory_${id}` } id={id} name ={name} description={description} serviceAreas={serviceAreas} custom ={name=="custom"}/>
                        
                        
                    })
                )
            }
        </div> 
        <div className=" mx-auto flex justify-center flex-wrap w-3/4  max-w-2xl  py-2 gap-2">
            {serviceCategories.map((category)=>{
            
            return   category.name=="custom" ?
            <a className="btn btn-info btn-sm" href=  {`#serviceCategory_${category.id}`}>{category.name}</a> :<a className={`btn btn-sm ${activeCategory === category.id ? 'btn-accent' : ''}`}
            href={`#serviceCategory_${category.id}`}
            onClick={() => toggleActive(category.id)}>
            {category.name}</a>
                
              
        })}
  
        </div>
    </div>
  )
 
}

export default ServiceAreaManager
