import {Form , redirect} from 'react-router-dom'
import { SubmitBtn , FormInput} from '../components'
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
    if(data.password!==data.confiremed_password){
        toast.error("The two fields are not matching ")
        return null ; 
    }
    try{
        const response = await customFetch.post(`/auth/reset-password?password=${data.password}&token=${token}` );
        toast.success("Your Password Is up to date")
        return redirect("/login")


    }
    catch(err){
        const errorMessage = err?.response?.data?.message || "Cannot reset the password"
        toast.error(errorMessage)
        return null ; 
    }
    


}
export const loader = async({request})=>{
    const params = Object.fromEntries([
        ...new URL(request.url).searchParams.entries(),
    ]);
    console.log(params);
    //here we should send a request to the server to check if the token is valid and not yet expired
    token = params?.token ; 
    try{
        const response = await customFetch(`auth/reset-password?token=${token}`)
        console.log(response.data);
        return null ; 


    }
    catch(err){
        
        console.log(err);
        const errorMessage= err?.response?.data?.message || "Access Denied !"
        return redirect("/forgotPassword");

    }

}

const ResetPassword = () => {
  
    
  return (
    <section className='h-screen grid place-items-center'>
       <Form method="POST" className='card w-96 p-8 bg-base-100 shadow-lg flex bg--400 flex-col gap-y-4'>
        <h4 className='text-center text-2xl font-bold'>Enter a new password</h4>
        <FormInput type="password" label="New Password" name="password" placeholder="secret"/>
        <FormInput type="password" label="Confirm New Password" name="confirmed_password" placeholder="secret"/>
        <div className='mt-4'>
           <SubmitBtn text='Submit' />
        </div>


       </Form>
    </section>
  )
}

export default ResetPassword
