import {RolesList ,FormInput , SubmitBtn , GrantedRolesList} from '../components'
import {useState} from 'react'
import {toast} from 'react-toastify'
import {Save , Plus} from 'lucide-react'
import { customFetch } from '../utils'
import { useSelector } from 'react-redux'
import { selectAdmin } from '../features/admin/adminSlice'
import {Form} from 'react-router-dom'
import { useRolesContext } from './RolesContext'
export const loader =(store)=>async()=>{
  const admin = store.getState().adminState.admin;
  try{

    const response = await customFetch("/super-admin/roles" ,{
      headers: { Authorization: `Bearer ${admin.token}` } 
    })
    
    return {roles: response.data}

  }
  catch(err){
    const errorMessage = err?.response?.data || "Cannot Load Roles ! Please retry Again"
    return toast.error(errorMessage);
  }

}

const CreateAdmin = () => {
    const [email ,setEmail] = useState('')
    const [firstname ,setFirstname] = useState('');
    const [lastname , setLastname] = useState('')
    const {predefinedRoles ,grantedRoles ,setPredefinedRoles , setGrantedRoles ,checkedPredefinedRoles  , setCheckedPredefinedRoles , checkedGrantedRoles , setCheckedGrantedRoles }=useRolesContext();
    const {token} = useSelector(selectAdmin)

    const handleGrantButtonClick = () => {
      const uncheckedPredefinedRoles = predefinedRoles.filter(role => !checkedPredefinedRoles.includes(role));
      setGrantedRoles([...grantedRoles , ...checkedPredefinedRoles])

      setPredefinedRoles(uncheckedPredefinedRoles);
      setCheckedPredefinedRoles([]);
       // Clear checked roles
    };
    const handleRevokeButtonClick=()=>{
      const uncheckedGrantedRoles = grantedRoles.filter(role => !checkedGrantedRoles.includes(role));
      

      setPredefinedRoles([...predefinedRoles , ...checkedGrantedRoles]);
      setGrantedRoles(uncheckedGrantedRoles)
      setCheckedGrantedRoles([]);
      

    }
    const handleRevokeAllButtonClick=()=>{
      setPredefinedRoles([...predefinedRoles,...grantedRoles])
      setGrantedRoles([]);
      // Clear checked granted roles
      setCheckedGrantedRoles([]);

    }

    const createAdmin =async (event)=>{
      event.preventDefault();
        
      try{          
          const response = await customFetch.post("/super-admin/create-admin" , {email : email , firstname : firstname , lastname : lastname , roles : grantedRoles} , {
          headers: { Authorization: `Bearer ${token}`} 
          } )
          console.log(response.data)

          toast.success("Admin Created Successfully")
          return response.data
        }
        catch(err){
          console.log(err)
            const errorMessage =err?.response?.data ||  "Data Validation Failed"
            toast.error(errorMessage) 
        }

    }
  return (
  
    <div className="grid place-content-center  gap-3 ">
      <div className="my-5 mx-auto flex justify-center md:flex-row  gap-3">
        <FormInput value={email} onChange={(e)=>setEmail(e.target.value)} label="Email" type="email" name="email" placeholder="Enter Email"/>
        <FormInput value={firstname} onChange={(e)=>setFirstname(e.target.value)} label="First Name" type="text" name="firstname" placeholder="Jhon"/>
        <FormInput value={lastname} onChange={(e)=>setLastname(e.target.value)} label="Last Name" type="text" name="lastname" placeholder="Smith"/>
      </div>
      <div className="flex justify-between gap-4 w-7/8 max-w-4xl max-h-1/3 overflow-y-auto">
        <div className={`w-60 h-80 bg-base-200 border-2 rounded-lg border-outline border-success ${!predefinedRoles.length <1 || "grid place-content-center text-center"}`}>{!predefinedRoles.length < 1 ? <RolesList /> :<p className="text-success">No Roles Available</p> }</div>
        
        <div className="flex-col flex justify-evenly place-items-center">
          <button className="btn btn-secondary text-base-content w-20 px-3 " onClick={handleGrantButtonClick}>Grant
          </button>
          <button className="btn btn-error text-base-content  w-20" onClick={handleRevokeButtonClick}>Revoke
          </button>
          <button className="btn btn-error text-base-content  w-20" onClick={handleRevokeAllButtonClick}>Revoke All
          </button>
          </div>
            <div className={`w-60 h-80 bg-base-200 border-2 rounded-lg border-outline border-info ${!grantedRoles.length <1 || "grid place-content-center text-center"}`}>{!grantedRoles.length < 1 ? <GrantedRolesList /> :<p className="text-info">No Roles Granted </p> }</div>
      </div>
      <div className="mx-auto flex justify-center gap-3">
        
        <div onClick={createAdmin} className='mt-4'>
            <SubmitBtn text="Create"/>
        </div>
        
      </div>

    </div> 
          
  )
}

export default CreateAdmin
