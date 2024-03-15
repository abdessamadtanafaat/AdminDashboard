import {useState , createContext} from 'react'
import {ChevronFirst, ChevronLast} from 
'lucide-react'
import SidebarItem from './SidebarItem';
import { LayoutDashboard, Home, StickyNote, Layers, Flag, Calendar, LifeBuoy, Settings } from "lucide-react";
import { useGlobalContext } from './context';



const items = [
  { icon: <Home size={20} />, text: "Home", alert: true },
  { icon: <LayoutDashboard size={20} />, text: "Dashboard", active: true },
  { icon: <StickyNote size={20} />, text: "Projects", alert: true },
  { icon: <Calendar size={20} />, text: "Calendar" },
  { icon: <Layers size={20} />, text: "Tasks" },
  { icon: <Flag size={20} />, text: "Reporting" },
  { icon: <Settings size={20} />, text: "Settings" },
  { icon: <LifeBuoy size={20} />, text: "Help" }
]
const SidebarContext = createContext();
const Sidebar = () => {
  const {expanded ,setExpanded}= useGlobalContext();
  return(
    <aside className="h-screen">
      <nav className="h-full flex flex-col bg-white border-r shadow-sm">
        <div className={`p-4 pb-2 flex justify-between items-center `}
        >
      
          <button
              onClick={() => setExpanded((curr) => !curr)}
            className={`p-1.5 rounded-lg bg-gray-50 hover:bg-orange-100`}>
              {expanded ? <ChevronFirst /> : <ChevronLast />}
          </button>

        </div>
        
        <ul className="flex-1 px-3">
            {items.map((item , index)=>{
              const {icon , text ,alert , active } = item;
              
              return(<SidebarItem key={index} icon={icon} text={text}   />)
            })}

          </ul>
      </nav>
    </aside>
  )
}

export default Sidebar
