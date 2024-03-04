import { FormInput ,SubmitBtn} from "../components"
import {Form} from 'react-router-dom'

export const action =(state)=>async({request})=>{
    
}

const Login = () => {
  return (
    <section className='h-screen grid place-items-center'>
        <Form method="POST" className='card w-96 p-8 bg-base-100 shadow-lg flex bg--400 flex-col gap-y-4'>
            <h4 className='text-center text-3xl font-bold'>Login</h4>
            <FormInput type="text" label="Email" name="Email" placeholder="email@email.com"/>
            <FormInput type="password" label="Password" name="password" placeholder="secret"/>
            <div className='mt-4'>
               <SubmitBtn text='Submit' />
            </div>

        </Form>
    </section>
  )
}
export default Login
