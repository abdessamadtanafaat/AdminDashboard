import { Sidebar } from "../components"
import { Outlet } from "react-router-dom"
import { SidebarProvider } from "../components/context"

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
