<<<<<<< HEAD
import { FormInput ,SubmitBtn} from "../components"
import {Form ,redirect , Link} from 'react-router-dom'
import {customFetch} from '../utils'
import {toast} from 'react-toastify'
export const action =(store)=>
    async({request})=>{
          const formData = await request.formData();
          const data = Object.fromEntries(formData);
          try{
            const response = await customFetch.post('/api/v1/auth/authenticate' ,data);
            return redirect("/")
            
          }
          catch(err){
            console.log(err);
            toast.success('Item added to cart');
          }
          
          

    
}

const Login = () => {
  return (
    <section className='h-screen grid place-items-center'>
        <Form method="POST" className='card w-96 p-8 bg-base-100 shadow-lg flex bg--400 flex-col gap-y-4'>
            <h4 className='text-center text-3xl font-bold'>Login</h4>
            <FormInput type="text" label="Email" name="email" placeholder="email@email.com"/>
            <FormInput type="password" label="Password" name="password" placeholder="secret"/>
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
=======
import React, { useState } from 'react';
import { FormInput, SubmitBtn } from '../components';
import axios from 'axios';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
      e.preventDefault();
      try {
          const response = await axios.post('http://localhost:8080/api/v1/auth/authenticate', {
            email: email,
            password: password
          });
          console.log('Authenticated successfully:', response.data);

      } catch (error) {
          console.error('Authentication error:', error);
          setError(error.response.data.message);
      }
  };

  return (
      <section className='h-screen grid place-items-center'>
          <form onSubmit={handleLogin} className='card w-96 p-8 bg-base-100 shadow-lg flex bg--400 flex-col gap-y-4'>
              <h4 className='text-center text-3xl font-bold'>Login</h4>
              <FormInput type="text" label="Email" name="email" placeholder="email@email.com" value={email} onChange={(e) => setEmail(e.target.value)} />
              <FormInput type="password" label="Password" name="password" placeholder="secret" value={password} onChange={(e) => setPassword(e.target.value)} />
              <div className='mt-4'>
                  <SubmitBtn text='Submit' />
              </div>
          </form>
      </section>
  );
};

export default Login;
>>>>>>> f3653c2854693f81065c0977c5aa923ef518cf36
