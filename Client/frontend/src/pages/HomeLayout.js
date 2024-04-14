import { Loading, Sidebar } from "../components"
import { Outlet , redirect, useNavigation} from "react-router-dom"
import { SidebarProvider } from "../components/context"
import {toast} from 'react-toastify'

export const loader = (store) => () => {
  const admin = store.getState().adminState.admin
  console.log(admin)

  if (!admin) {
    toast.error("You must log in to access your dashboard")
    return redirect("/login")
  }
  return null;
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
