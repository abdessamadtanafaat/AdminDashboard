import { useLoaderData } from "react-router-dom";
import { customFetch } from "../utils";
import {toast} from 'react-toastify'

export const loader =(store)=> async()=>{
  const admin = store.getState().adminState.admin ; 

  try{
    const response= await customFetch("/config/languages",
    {  headers: {
      Authorization: `Bearer ${admin.token}`} 
    }) ;
    return {
      languages : response.data
    }
    


  }
  catch(err){
    console.log(err)
    const errMessage = err?.response?.data || "Failed To Load The Languages"
    return toast.error(errMessage)

  }
  
}
const LanguesManager = () => {
  const  {languages} =useLoaderData();
  return (
    <div>
      {JSON.stringify(languages)}
      
    </div>
  )
}

export default LanguesManager
