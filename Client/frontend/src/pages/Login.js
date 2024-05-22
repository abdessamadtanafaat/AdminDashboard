import { FormInput ,SubmitBtn} from "../components"
import { useForm } from 'react-hook-form';
import {Form ,redirect , Link,useNavigate} from 'react-router-dom'
import {customFetch} from '../utils'
import {toast} from 'react-toastify'
import {loginAdmin} from '../features/admin/adminSlice'
import { useDispatch } from "react-redux"
import { EyeIcon, EyeOffIcon } from "lucide-react";
import React, { useState } from 'react';

export const action =(store)=>
    async({request})=>{

          const formData = await request.formData();
          const data = Object.fromEntries(formData);
          const navigate = useNavigate();
          try{
            
            const response = await customFetch.post('/authenticate' ,data);
            //store user in localstorage
            store.dispatch(loginAdmin(response.data))
            toast.success("Welcome !! You access to Dashboard")
            console.log(response.data)
            //return redirect("/")
            navigate("/");

          }
          catch(err)
          {
            console.log(err)
            const errorMessage = err?.response?.data || "Please Double check your credentials"; 
            console.log(err?.response?.data)
            
            return toast.error(errorMessage); ;
          }
}

const Login = () => {
  const dispatch = useDispatch();
  const { register, handleSubmit, formState: { errors } } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      const response = await customFetch.post('/authenticate', data);
      dispatch(loginAdmin(response.data));
      toast.success("Welcome !! You access to Dashboard");
      console.log(response.data);
      navigate ("/")
    } catch (err) {
      const errorMessage = err?.response?.data || "Please Double check your credentials"; 
      console.log(err?.response?.data);
      toast.error(errorMessage);
    }
  };
  const [passwordVisible, setPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
      setPasswordVisible(prev => !prev);
  };

  return (
    <section className='h-screen grid place-items-center bg-base-300'>
        <Form method="POST"  onSubmit={handleSubmit(onSubmit)} className='card w-96 p-8 bg-base-100 shadow-lg flex  flex-col gap-y-4'>
            <img src="http://localhost:3000/static/media/logo.6c624ed64baccff0e3b1.png" alt="Logo"  />
            <h4 className='text-center text-3xl font-bold'>Login</h4>
            <FormInput label="Email" name="email" placeholder="email" register={register} error={errors.email}/>
        <div className="flex items-center">
          <div className="flex-grow relative">
            <FormInput 
            type={passwordVisible ? "text" : "password"}
            label="Password" name="password" placeholder="password" register={register} error={errors.password}/>
                </div>

                <div className="relative" style={{ marginTop: '50px' }}>

                    <button
                      type="button"
                      className="focus:outline-none -ml-10"
                      onClick={togglePasswordVisibility}
>

{passwordVisible ? 
        <>
            <EyeIcon  />
        </> 
        : 
        <>
            <EyeOffIcon />
        </>
    }
</button>
    </div>

</div>               


             <p className='text-center font-bold'>
              Forgot Password ?{' '}
                <Link
                 to='/forgotPassword'
                 className='ml-2 font-semibold  link link-hover link-primary capitalize'
              >
                 Reset your Password  
             </Link>
            </p>
            <div className='mt-4'>
               <SubmitBtn text='Submit' />
            </div>


        </Form>
    </section>
  )
}
export default Login
