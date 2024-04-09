import { useGlobalContext } from "./context";
import {Link} from 'react-router-dom'
const SidebarItem = ({icon , text ,alert ,active , link ,children }) => {
  const {expanded ,setExpanded } = useGlobalContext();
  
  return (
    <li 
      onClick={()=>expanded || setExpanded(!expanded)}>
        <Link to={link} className={`
            relative flex justify-center items-center w-full py-2 px-3 my-1
            font-medium rounded-md cursor-pointer hover:bg-base-100 text-base-content
            transition-colors group
            ${active? "bg-gradient-to-tr from-indigo-200 to-indigo-100 text-indigo-800": "hover:bg-base-content text-base-content hover:text-base-100 "
        }`}>
          {icon}
          <span
            className={`overflow-hidden transition-all ${
            expanded ? "w-52 ml-3" : "w-0"}`}>
            {text}
          </span>
          {alert && (
            <div
              className={`absolute right-2 w-2 h-2 rounded bg-indigo-400 ${
              expanded ? "" : "top-2"
            }`}
            />
          )}
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
        </Link>
    </li>
  )
}

export default SidebarItem
