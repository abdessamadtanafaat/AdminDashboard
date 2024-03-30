import {BsSunFill ,  BsMoonFill } from "react-icons/bs";

import {ChevronFirst, ChevronLast , LogOut} from 
'lucide-react'
import SidebarItem from './SidebarItem';
import { LayoutDashboard, Home, StickyNote, Layers, Calendar, LifeBuoy } from "lucide-react";
import { useGlobalContext } from './context';
import {  useDispatch, useSelector } from "react-redux";
import {useNavigate ,Link} from 'react-router-dom'
import {toggleTheme  , logoutAdmin, selectAdmin, selectTheme} from '../features/admin/adminSlice'
import logo from '../assets/logo.png'
import default_avatar from '../assets/default_avatar.webp'
import { customFetch } from "../utils";
import { toast } from "react-toastify";

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
 
  const admin = useSelector(selectAdmin)
  const {firstname , lastname ,email , avatarUrl , token} = admin


  const handleTheme = ()=>{
    dispatch(toggleTheme())
  }
  const handleLogout = async()=>{
    try {
      const response = await customFetch.post("/auth/logout", {data : email} ,{
          headers: { Authorization: `Bearer ${token}` } 
      });
      navigate("/login")
      dispatch(logoutAdmin())
      
    } catch (err) {
      const errorMessage = err?.response?.data;
      console.log(errorMessage);
      toast.error(errorMessage);
    } 
  }
  
  return(
    <aside className="h-screen fixed z-30"  >
      <nav className="h-full flex flex-col bg-base-300 border-r shadow-sm">
        <div className={`p-4 pb-2 flex justify-between items-center mb-4`}
        >
          <img src={logo} className={`h-16
          overflow-hidden transition-all  ${expanded ? "w-auto" : "w-0"}`} />
          <button
              onClick={() => setExpanded((curr) => !curr)}
            className={`p-1.5 rounded-lg bg-base-content text-base-200 hover:bg-accent`}>
              {expanded ? <ChevronFirst /> : <ChevronLast />}
          </button>
        </div>
        
        <ul className="flex-1 px-3 ">
            {items.map((item , index)=>{
              const {icon , text  } = item;
              
              return(<SidebarItem key={index} icon={icon} text={text}   />)
            })}

          </ul>
          <div className="border-t flex p-3">
            <div className="dropdown  dropdown-end dropdown-right dropdown-top dropdown-hover ">
              <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
                <div className="w-13 rounded-full">
                  <img alt="profile image" src={avatarUrl ? `${avatarUrl}`: default_avatar} />
                </div>
              </div>
              <ul tabIndex={0} className="mt-5 z-[1] p-0 shadow menu menu-sm dropdown-content bg-base-200 rounded-box mr-[5rem] w-52">
                <li><Link to="/profile" className="btn btn-active  bg-base-200 border-none shadow-none hover:bg-base-100 rounded-md   justify-center">
                    Profile</Link>
                </li>
                <li><button onClick={async()=>await handleLogout()} className="h-5 flex justify-center btn btn-active rounded-md bg-base-200 border-none shadow-none hover:bg-base-100 hover:text-error">
                  LogOut 
                  <LogOut/>
                </button>
                </li>
                <li>
                <label className="flex btn btn-active  bg-base-200 border-none hover:bg-base-100 cursor-pointer gap-2  shadow-none mx-auto  w-full justify-center h-full rounded-md ">
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
                overflow-hidden transition-all -mt-2 ${expanded ? "w-52 ml-3" : "w-0"}
              `}>
                <div className="leading-4">
                  <h4 className="font-semibold text-base-content capitalize">{`${firstname} ${lastname}`}</h4>
                    <span className="text-xs text-base-content ">{email}</span>
                </div>
            
            </div>
          </div>
      </nav>
    </aside>
  )
}

export default Sidebar
