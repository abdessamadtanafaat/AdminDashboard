import {RolesList ,FormInput} from '../components'
import {useState} from 'react'
import {toast} from 'react-toastify'
import {Save , Plus} from 'lucide-react'
import { customFetch } from '../utils'
import { useSelector } from 'react-redux'
import { selectAdmin } from '../features/admin/adminSlice'

const CreateAdmin = () => {
    const [email ,setEmail] = useState()
    const {token} = useSelector(selectAdmin)
    const createAdmin =async ()=>{
        
        try{
            
            const response = await customFetch.post("/super-admin/create-admin" , {email : email} , {
                headers: { Authorization: `Bearer ${token}`} 
            } )
            console.log(response.data)

            return response.data
        }
        catch(err){
            const errorMessage = err?.response?.data ||
             "Error While Creating new Admin" 
            return toast.error(errorMessage) 
        }

    }
  return (
    <>
    
            <h3 className="font-bold text-lg mb-4">Create Administator</h3>
            <div className="my-5 mx-auto w-1/3"><FormInput value={email} onChange={(e)=>setEmail(e.target.value)} label="Enter the Email" type="email" name="email" placeholder="email@email.com"/></div>
            <div className="flex justify-between gap-4">
              <div className="w-60 h-80 bg-base-200">
                <RolesList/>
              </div>
              <div className="flex-col flex justify-evenly place-items-center">
                <button className="btn btn-secondary text-base-content w-20 px-3 ">Grant</button>
                <button className="btn btn-error text-base-content  w-20">Revoke</button>
                <button className="btn btn-error text-base-content  w-20">Revoke All</button>
                

              </div>
              <div className="w-60 h-80 bg-base-200">320×568</div>
            </div>
            
            
          </> 
          
  )
}

export default CreateAdmin
