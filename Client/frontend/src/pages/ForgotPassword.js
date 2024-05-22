import {Form ,redirect,useNavigate} from 'react-router-dom'
import {FormInput, SubmitBtn} from '../components'
import { useForm } from 'react-hook-form';
import { useDispatch } from "react-redux";
import {store} from '../app/store';
import { customFetch } from '../utils';
import { toast } from 'react-toastify';
import { useState } from 'react';

export const action = (store)=>
  async({request})=>{
    const formData = await request.formData();
    const data = Object.fromEntries(formData);    
    if (!formData.get("email")) {
      return toast.error("Email cannot be empty");
      
    }
    
    try{
       const response = await customFetch.get(`/auth/password-request?email=${data.email}`); 
       console.log(response.data);
       toast.success("Please check your mail to reset your password!")
       return {data : response.data}
      
    }
    catch(err){
        console.log(err);
        const errorMessage = err?.response?.data || "Error Occured !!!";
        
        toast.error(errorMessage);
        return null ;       
    }
}
const ForgotPassword = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (data) => {

    setLoading(true); 

  try{
    const response = await customFetch.get(`/auth/password-request?email=${data.email}`); 
    console.log(response.data);
    toast.success("Please check your mail to reset your password!")
    navigate('/login');
    return {data : response.data}
   
 }
 catch(err){
     console.log(err);
     const errorMessage = err?.response?.data || "Error Occured !!!";
     
     toast.error(errorMessage);
     return null ;       
 } finally {
  setLoading(false);
}
  };

  return (
    <section className='h-screen grid place-items-center bg-base-300'>
                  {loading}
    <Form method="POST" onSubmit={handleSubmit(onSubmit)} className='card w-96 p-8 bg-base-100 shadow-lg flex bg--400 flex-col gap-y-4'>
        <h4 className='text-center text-2xl font-bold'>Enter Your Email to reset the password</h4>
        <FormInput type="text" label="Email" name="email" placeholder="email" register={register} error={errors.email}/>
        <div className='mt-4'>
          
        <SubmitBtn text={loading ? <span className="loading loading-spinner loading-sm"></span> : 'Submit'} disabled={loading} />
        </div>


    </Form>
</section>
  )
}

export default ForgotPassword
