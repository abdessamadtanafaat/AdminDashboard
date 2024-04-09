import {Link} from 'react-router-dom'
import { useGlobalContext } from './context';
import {useState} from 'react'
const NewSidebarItem = ({icon , text ,alert ,active ,children }) => {
    const {expanded ,setExpanded } = useGlobalContext();
    const [show , setShow] = useState(false)
    const showMenu =(event)=>{
        expanded ||setExpanded(true)
        setShow(!show)           

    }
    return (
        <li  onClick={()=>expanded || setExpanded(!expanded)} className={`
         relative flex justify-center items-center w-full 
            font-medium rounded-md cursor-pointer hover:bg-base-100 text-base-content
            transition-colors group
            ${active? "bg-gradient-to-tr from-indigo-200 to-indigo-100 text-indigo-800": "hover:bg-base-content text-base-content hover:text-base-100"
            }`} >
            
            <span className={`${!expanded || ""} ${show || "menu-dropdown-show"}`} onClick={showMenu}>
                {icon}
                <span
                className={`overflow-hidden transition-all ${
                expanded ? "w-52 ml-3" : "w-0"}`}>
                {text}
            </span></span>
            <ul className={`${show || "menu-dropdown-show"} ${expanded ? "p-1 menu-dropdown" : "hidden"}`}>
            {children.map(child=><li><Link
             to={child.link} className="hover:bg-base-200 hover:text-base-content" >{child.text}</Link></li>)}
        
            </ul>
            {!expanded && (
            <div
              className={`
              absolute left-full rounded-md px-2 py-1 ml-6
              bg-base-content text-base-200 text-sm
              invisible opacity-20 
              -translate-x-3 transition-all
            group-hover:visible group-hover:opacity-100 group-hover:translate-x-0
          `}>
            {text}
          </div>
          )}   
    </li>
  )
}

export default NewSidebarItem
