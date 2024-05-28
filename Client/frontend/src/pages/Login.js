import { FormInput ,SubmitBtn} from "../components"
import { useForm } from 'react-hook-form';
import {Form ,redirect , Link,useNavigate} from 'react-router-dom'
import {customFetch} from '../utils'
import {toast} from 'react-toastify'
import {loginAdmin, selectTheme} from '../features/admin/adminSlice'
import { useDispatch, useSelector } from "react-redux"
import { EyeIcon, EyeOffIcon } from "lucide-react";
import { useState } from 'react';

import {logoDark , logoLight} from '../assets'
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
  const theme = useSelector(selectTheme)

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
  

  return (
    <section className='h-screen grid place-items-center bg-base-300'>
        <Form method="POST"  onSubmit={handleSubmit(onSubmit)} className='card w-96 p-8 bg-base-100 shadow-lg flex  flex-col gap-y-4'>
          <div className="flex flex-col justify-start">
            <img src={theme=="nord" ?logoDark :logoLight} alt="Logo"  />
            <h4 className='text-center text-3xl font-bold'>Login</h4>

          </div>
            
            <FormInput label="Email" name="email" placeholder="email@email.com" register={register} error={errors.email}/>
            <FormInput type="password" label="Password" name="password" placeholder="secret" register={register} error={errors.password}/>
             
        <p className='text-center font-bold'>
          Forgot Password ?{' '}
            <Link
              to='/forgotPassword'
              className='ml-2 font-semibold  link link-hover link-primary capitalize'
            >Reset your Password  
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
