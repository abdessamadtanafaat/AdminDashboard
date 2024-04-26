import ServiceAreaLogo from '../assets/32658b2f-c72d-43a2-9a63-0813f3c339da.png'
import {useState} from 'react'
import {Plus , EllipsisVertical ,  ChevronUp ,ChevronDown } from 'lucide-react'
import {FormInput ,SubmitBtn} from './index';
import { useSelector } from 'react-redux';
import { selectAdmin } from '../features/admin/adminSlice';
import { customFetch } from '../utils';
import {toast} from 'react-toastify'
const ServiceCategory = ({ id ,name , description , serviceAreas}) => {
    const [expandedList ,setExpandedList] = useState(false);
    const [serviceName , setServiceName] = useState(null)
    const [services,setServices] =useState(serviceAreas)
    const [checkedServices , setCheckedServices] = useState([])
    const {token} = useSelector(selectAdmin)
    
      const handleCheckboxChange = (event , serviceId) => {
        if (!checkedServices.includes(serviceId)) {
          setCheckedServices(checkedServices.filter((checkedService) => checkedService!== serviceId))
        } else {
          setCheckedServices([...checkedServices,serviceId])
        }
        console.log(checkedServices)
      };
    function checkName(name) {
        if (serviceName  && serviceName.trim() !== '' ) {

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
            setServiceName(null)
            setServices([response.data , ...services])
            return toast.success("Service Area Added") 

        }
        catch(err){
            console.log(err)
            const errorMessage=err?.response?.data || "Failed To add Service Area"
            return toast.error(errorMessage) 

        }
    }
    return (
        <div id={`serviceCategory_${id}`} className="shadow-md w-full h-50 p-2 rounded-lg flex flex-col carousel-item justify-between gap-3 border border-outline border-primary">
            <div className="flex justify-between p-2">
                <div className="">
                    <h2 className="font-bold">{name}</h2>
                    <p className="text-slate-500">{description}</p>
               </div>    
            </div>
            <div className="join w-full">
                <input type="text"  class="input input-bordered input-primary w-5/6 max-w-xs join-item" placeholder="Type Service Name to add" value={serviceName} onChange={(e)=>setServiceName(e.target.value)} />
                <button onClick={createServiceArea}className="btn-primary btn"><Plus/></button>

            </div>
            
            <div className={`w-full h-40 overflow-y-auto border-primary scrollbar-track-base-content
            scrollbar-thumb-base-100 p-1
            scrollbar-thin flex flex-col gap-2 ${!services.length<1 || "grid place-items-center"}  `}>
                {!services.length<1 || (
                    <h3 className="text-center font-bold text-bg-info text-xl">No Service Area Created Yet </h3>
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
                            defaultChecked={true} 
                            checked={checkedServices.includes(serviceId)} onChange={(event) => handleCheckboxChange(event, serviceId)} />

                        </div>
                    )

                })}
            </div>
        </div>
    )

}

export default ServiceCategory
