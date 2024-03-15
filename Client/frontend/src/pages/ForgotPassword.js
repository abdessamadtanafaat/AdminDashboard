import {Form ,redirect} from 'react-router-dom'
import {FormInput, SubmitBtn} from '../components'
import {store} from '../app/store';
import { customFetch } from '../utils';
import { toast } from 'react-toastify';
export const action = (store)=>
async({request})=>{
    const formData = await request.formData();
    const data = Object.fromEntries(formData);

    if (!formData.get("email")) {
      toast.error("Email cannot be empty");
      return null ;
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
  return (
    <section className='h-screen grid place-items-center'>
    <Form method="POST" className='card w-96 p-8 bg-base-100 shadow-lg flex bg--400 flex-col gap-y-4'>
        <h4 className='text-center text-2xl font-bold'>Enter Your Email to reset the password</h4>
        <FormInput type="text" label="Email" name="email" placeholder="email@email.com"/>
        <div className='mt-4'>
           <SubmitBtn text='Submit' />
        </div>


    </Form>
</section>
  )
}

export default ForgotPassword
