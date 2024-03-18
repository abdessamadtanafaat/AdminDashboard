import {BsSunFill ,  BsMoonFill } from "react-icons/bs";

import {ChevronFirst, ChevronLast , LogOut} from 
'lucide-react'
import SidebarItem from './SidebarItem';
import { LayoutDashboard, Home, StickyNote, Layers, Flag, Calendar, LifeBuoy, Settings, MoreVertical } from "lucide-react";
import { useGlobalContext } from './context';
import {  useDispatch, useSelector } from "react-redux";
import {useNavigate} from 'react-router-dom'
import {toggleTheme ,selectTheme , logoutAdmin} from '../features/admin/adminSlice'
import logo from '../assets/logo.png'
const items = [
  { icon: <Home size={20} />, text: "Home", alert: true },
  { icon: <LayoutDashboard size={20} />, text: "Dashboard", active: true },
  { icon: <StickyNote size={20} />, text: "Projects", alert: true },
  { icon: <Calendar size={20} />, text: "Calendar" },
  { icon: <Layers size={20} />, text: "Tasks" },
  { icon: <LifeBuoy size={20} />, text: "Help" }
]


const Sidebar = () => {
  const navigate = useNavigate();

  const {expanded ,setExpanded}= useGlobalContext();
  const dispatch = useDispatch();
  const handleTheme = ()=>{
    dispatch(toggleTheme())
  }
  const handleLogout = ()=>{
    navigate("/login")
    dispatch(logoutAdmin())
    
  }
  return(
    <aside className="h-screen"  >
      <nav className="h-full flex flex-col bg-base-300 border-r shadow-sm">
        <div className={`p-4 pb-2 flex justify-between items-center mb-4`}
        >
          <img src={logo} className={`h-16
          overflow-hidden transition-all  ${expanded ? "w-auto" : "w-0"}`} />
          <button
              onClick={() => setExpanded((curr) => !curr)}
            className={`p-1.5 rounded-lg bg-gray-50 hover:bg-orange-100`}>
              {expanded ? <ChevronFirst /> : <ChevronLast />}
          </button>
          

        </div>
        
        <ul className="flex-1 px-3">
            {items.map((item , index)=>{
              const {icon , text  } = item;
              
              return(<SidebarItem key={index} icon={icon} text={text}   />)
            })}

          </ul>
          <div className="border-t flex p-3">
            <div class="dropdown dropdown-end     dropdown-right dropdown-top dropdown-hover">
              <div tabindex="0" role="button" class="btn btn-ghost btn-circle avatar">
                <div class="w-13 rounded-full">
                  <img alt="profile image" src="https://daisyui.com/images/stock/photo-1534528741775-53994a69daeb.jpg" />
                </div>
              </div>
              <ul tabindex="0" className=" mt-5 z-[1] p-2 shadow menu menu-sm dropdown-content bg-base-200 rounded-box w-52">
                <li><a class="btn btn-active  bg-base-200 border-none hover:bg-base-100  justify-center">
                    Profile</a>
                </li>
                <li><button onClick={handleLogout} className="h-5 flex justify-evenly btn btn-active  bg-base-200 border-none hover:bg-base-100 hover:text-error">
                  LogOut 
                  <LogOut/>
                </button></li>
                <li>
                <label className="flex btn btn-active  bg-base-200 border-none hover:bg-base-100 cursor-pointer gap-2 mx-auto  w-full justify-center h-full">
                    <BsSunFill className='swap-on h-4 w-4' />
                    <input type="checkbox" onChange={handleTheme}  className="toggle theme-controller"/>
                    <BsMoonFill className='swap-off h-4 w-4' />

                </label>


                </li>

                
                
                
              </ul>
            </div>  
          
            <div
              className={`
                flex justify-between items-center
                overflow-hidden transition-all ${expanded ? "w-52 ml-3" : "w-0"}
              `}>
                <div className="leading-4">
                  <h4 className="font-semibold text-base-content">John Doe</h4>
                    <span className="text-xs text-base-content">johndoe@gmail.com</span>
                </div>
            
            </div>
          </div>
      </nav>
    </aside>
  )
}

export default Sidebar
