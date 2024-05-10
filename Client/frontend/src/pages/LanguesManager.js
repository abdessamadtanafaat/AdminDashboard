import { useLoaderData  , redirect} from "react-router-dom";
import { customFetch } from "../utils";
import {toast} from 'react-toastify'
import { Language } from "../components";

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
    
    toast.error(errMessage)
    return redirect("/")

  }
  
}
const LanguesManager = () => {
  const  {languages} =useLoaderData() || {};
  console.log(languages)
  return (
    <div className="grid place-content-center items-center">
      <div className="mx-auto carousel w-3/4  max-w-2xl p-4 flex justify-between gap-10 overflow-hidden ">
      {
                languages.length < 1 ? (<h1 className="text-center font-bold text-3xl">
                    No Service Category Available , Create New One
                </h1>) : (
                        languages.map((language)=>{
                        const {id , name , compagnes } = language
                        return <Language key={id} name={name} id={id} compagnes={compagnes}/>
                        
                        
                    })
                )
            }

      </div>
      <div className=" mx-auto flex justify-center flex-wrap w-3/4  max-w-2xl  py-2 gap-2">
            {languages.map((language)=>{
              return <a href={`#language_${language.id}`} className="btn btn-sm">{language.name}</a>
              
        })}
  
        </div>
      
    </div>
  )
}

export default LanguesManager
