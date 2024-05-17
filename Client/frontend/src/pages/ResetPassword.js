import {Form , redirect} from 'react-router-dom'
import { SubmitBtn , FormInput} from '../components'
import { useForm } from 'react-hook-form';
import { customFetch } from '../utils';
import { toast } from 'react-toastify';
let token = ''; 
const isPasswordValid = (password)=>{
    //5 caracters at least for example
    //

    return Boolean(password) //password not empty
}
export const action = (store)=>async({request})=>{
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    if(!isPasswordValid(data.password)){
        toast.error("Password cannot be empty")
        return null ;

    }
    if(data.password!==data.confirmed_password){
        toast.error("The two fields are not matching ")
        return null ; 
    }
    try{
        const body = {"password" : data.password , "token" : token } 
        const response = await customFetch.post('/auth/reset-password' ,body );
        console.log(response.data);
        toast.success("Your Password Is up to date")
        return redirect("/login")


    }
    catch(err){
        const errorMessage = err?.response?.data || "Cannot reset the password"
        return toast.error(errorMessage) ; 
    }
}
export const loader = async({request})=>{
    const params = Object.fromEntries([
        ...new URL(request.url).searchParams.entries(),
    ]);
    console.log(params);
    token = params?.token ; 
    console.log(token);
    try{
        const response = await customFetch(`/auth/is-token-valid?token=${token}`)
        console.log(response.data);
        return null ; 


    }
    catch(err){
        const errorMessage= err?.response?.data || "Access Denied !"
        toast.error(errorMessage)
        console.log(errorMessage);
        return redirect("/forgotPassword") ;
    }

}

const ResetPassword = () => {    
    const { register, handleSubmit } = useForm();

  return (
    <section className='h-screen grid place-items-center bg-base-300'>
       <Form method="POST"  className='card w-96 p-8 bg-base-100 shadow-lg flex  flex-col gap-y-4'>
        <h4 className='text-center text-2xl font-bold'>Enter a new password</h4>
            <FormInput type="password" label="New Password" name="password" placeholder="secret" register={register}/>
        <FormInput type="password" label="Confirm New Password" name="confirmed_password" placeholder="secret" register={register}/>
        <div className='mt-4'>
           <SubmitBtn text='Submit' />
        </div>


       </Form>
    </section>
  )
}

export default ResetPassword
