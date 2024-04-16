import {RolesList ,FormInput , SubmitBtn} from '../components'
import {useState} from 'react'
import {toast} from 'react-toastify'
import {Save , Plus} from 'lucide-react'
import { customFetch } from '../utils'
import { useSelector } from 'react-redux'
import { selectAdmin } from '../features/admin/adminSlice'
import {Form} from 'react-router-dom'


const CreateAdmin = () => {
    const [email ,setEmail] = useState('')
    const [firstname ,setFirstname] = useState('');
    const [lastname , setLastname] = useState('')
    const {token} = useSelector(selectAdmin)
    const createAdmin =async (event)=>{
      event.preventDefault();
        
      try{          
          const response = await customFetch.post("/super-admin/create-admin" , {email : email , firstname : firstname , lastname : lastname} , {
          headers: { Authorization: `Bearer ${token}`} 
          } )
          console.log(response.data)

          toast.success("Admin Created Successfully")
          return response.data
        }
        catch(err){
          console.log(err)
            const errorMessage = err?.response?.data?.details[0] || err?.response?.data ||  "Data Validation Failed"
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
        <div tabIndex={0} className="w-60 h-80 bg-base-200 border-2 rounded-lg border-outline border-success">
          <RolesList/>
        </div>
        <div className="flex-col flex justify-evenly place-items-center">
          <button className="btn btn-secondary text-base-content w-20 px-3 ">Grant
          </button>
          <button className="btn btn-error text-base-content  w-20">Revoke
          </button>
          <button className="btn btn-error text-base-content  w-20">Revoke All
          </button>
          </div>
            <div className="w-60 h-80 bg-base-200 border-2 rounded-lg border-outline border-info">320×568</div>
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
