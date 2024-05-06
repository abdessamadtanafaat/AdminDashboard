import { toast } from "react-toastify";
import { customFetch } from "../utils";
import { useLoaderData } from "react-router-dom";
import {useState} from 'react'
import {Plus} from 'lucide-react'
import { FormInput ,SubmitBtn , BusinessCategory } from "../components";
import { useSelector } from "react-redux";
import { selectAdmin } from "../features/admin/adminSlice";
export const loader = (store)=>async({})=>{
    const admin = store.getState().adminState.admin ; 

    try{
        const response = await customFetch("/config/business-categories",{
            headers: { Authorization: `Bearer ${admin.token}` } 
        })
        
        return {businessCategories : response.data}

    }
    catch(err){
        const errorMessage = err?.response?.data || "Failed To load Bussiness Types"
        toast.error(errorMessage);
        return {businessCategories : []}

    }
}
const BusinessTypeManager = () => {
    const {businessCategories} = useLoaderData();
    const {token} = useSelector(selectAdmin)
    const [categoryName , setCategoryName] = useState(null);
    const [categoryDesc ,setCategoryDesc] = useState(null)
    const [activeCategory, setActiveCategory] = useState(!businessCategories.length < 1 ? businessCategories[0].id : null);

    const toggleActive = (id) => {
        setActiveCategory(activeCategory === id ? null : id);
    };
    const createBusinessCategory = async()=>{
        try{
            const response = await customFetch.post('/config/business-category',{
                categoryName : categoryName , categoryDesc : categoryDesc
            },  {
                headers: { Authorization: `Bearer ${token}`} 
            } )
            setCategoryDesc('')
            setCategoryName('')
            businessCategories.push(response.data)
            document.getElementById('my_modal_10').close();
            return toast.success("Service Category Created")
        }
        catch(err){
            console.log(err)
            const errorMessage =err?.response?.data?.message|| err?.response?.data || "Failed to Create Business Category"
            document.getElementById('my_modal_10').close();
            return toast.error(errorMessage) 

        }

    }
    
  return (
    <div className="grid place-content-center items-center">
        <div className="flex  mx-auto mb-10 justify-between gap-2">
            <div>
                <button className="btn btn-info text-base-content" onClick={()=>document.getElementById('my_modal_10').showModal()}><Plus/> Business Category</button>
                <dialog id="my_modal_10" className="modal">
            <div className="modal-box">
            <form method="dialog grid place-content-center">
      
            <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={(event)=>{event.preventDefault(); document.getElementById('my_modal_10').close()}} >✕</button>
            </form>
            <h3 className="font-bold text-lg">Enter Informations for Business Category</h3>
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
            <div className='mt-4 w-1/3 mx-auto' onClick={createBusinessCategory}>
                <SubmitBtn text="Create"/>
            </div>
            </div>
                </dialog>
            </div>
         
        </div>
        <div className="mx-auto carousel w-3/4  max-w-2xl p-4 flex justify-between gap-10 overflow-hidden ">
            {
                !businessCategories.length< 1 ? (
                businessCategories.map((category)=>{
                    const {id , categoryName , photoUrl , categoryDesc, businessTypes} = category
                    return <BusinessCategory key={id} categoryId={id} name={categoryName}
                    description={categoryDesc} businessTypes={businessTypes} />


                })) 
                : 
                (<h1 className="text-center font-bold text-3xl">
                No Service Category Available , Create New One
                
                </h1>)
            }

        </div>

        <div className=" mx-auto flex justify-center flex-wrap w-3/4  max-w-2xl  py-2 gap-2">
            {businessCategories.map((category)=>{
                const {id , categoryName } =category
            
            return <a 
            className={`btn btn-sm ${activeCategory === id ? 'btn-accent' : ''}`}
            href={`#businessCategory_${id}`}
            onClick={() => toggleActive(id)}>
            {categoryName}</a>
                
              
            })}
  
        </div>
      
    </div>
  )
}

export default BusinessTypeManager
