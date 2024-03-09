import { FormInput ,SubmitBtn} from "../components"
import {Form ,redirect , Link} from 'react-router-dom'
import {customFetch} from '../utils'
import {toast} from 'react-toastify'
export const action =(store)=>
    async({request})=>{
          const formData = await request.formData();
          const data = Object.fromEntries(formData);
          try{
            const response = await customFetch.post('/auth/authenticate' ,data);
            toast.success("Welcome !! You access to Dashboard")
            console.log(response.data)
            redirect("/");
            return redirect("/")

          }
          catch(err){
            const errorMessage = err?.response?.data || "Please Double check your credentials"; 
            console.log(err?.response?.data)
            toast.error(errorMessage);
            return null ;
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
