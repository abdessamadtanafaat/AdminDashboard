import { Loading, Sidebar } from "../components"
import { Outlet , redirect, useLoaderData, useNavigate, useNavigation} from "react-router-dom"
import { SidebarProvider } from "../components/context"
import {toast} from 'react-toastify'
import { customFetch } from "../utils"
import { changeToken, loginAdmin, logoutAdmin } from "../features/admin/adminSlice"

export const loader = (store) => async() => {
  const admin = store.getState().adminState.admin
  
  if (!admin) {
    toast.error("You must log in to access your dashboard")
    return redirect("/login")
  }
  else{

    try{
      const body={token : admin.token}
      const response = await customFetch.post(
        "/admin/verifyToken",body)
      const responseData = response.data
      if(responseData ==='expiredToken'){        
        await toast.error("Session expired")
        store.dispatch(logoutAdmin())
        return redirect("/login")
      }
      if(responseData!=="goodToken"){
        return store.dispatch(changeToken({ token: response.data }));
      }
    }
    catch(err){
      throw Error(err?.response?.data)}
    return null  
  
  }
  
}

const HomeLayout = () => {
  
  const navigation = useNavigation();
  const isPageLoading = navigation.state==='loading'
  return (
    <div className="flex duration-2000">
      <SidebarProvider>
         <Sidebar/>
      </SidebarProvider>
      {isPageLoading ? (<Loading/>) :(

        <section className='align-element py-4'>
            <Outlet />
        </section>)}
    </div>
      
  )
}

export default HomeLayout
