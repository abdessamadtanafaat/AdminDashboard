import { Sidebar } from "../components"
import { Outlet , redirect} from "react-router-dom"
import { SidebarProvider } from "../components/context"
import {toast} from 'react-toastify'
export const loader = (store) => () => {
  const admin = store.getState().adminState.admin
  console.log(admin)

  if (!admin) {
    
    return redirect("/login")
  }
  return null;
}

const HomeLayout = () => {
  return (
    <div className="flex">
      <SidebarProvider>
         <Sidebar/>
      </SidebarProvider>
    <section className='align-element py-20'>
        <Outlet />
    </section>

    </div>
    
  )
}

export default HomeLayout
