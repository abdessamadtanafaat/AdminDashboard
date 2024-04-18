import { createContext } from "react";
import { useLoaderData } from "react-router-dom";
import { useState , useContext } from "react";
const RolesContext = createContext();
const RolesProvider = ({children})=>{
    const {roles} = useLoaderData() || []
    const [predefinedRoles , setPredefinedRoles] =useState(roles);
    const [grantedRoles ,setGrantedRoles] = useState([]);
    const [checkedPredefinedRoles ,setCheckedPredefinedRoles] =useState([])
    const [checkedGrantedRoles ,setCheckedGrantedRoles] = useState([]); 
    return (
        <RolesContext.Provider value={{predefinedRoles , setPredefinedRoles , grantedRoles ,setGrantedRoles , checkedPredefinedRoles ,setCheckedPredefinedRoles , 
        checkedGrantedRoles , setCheckedGrantedRoles}}>
            {children}
        </RolesContext.Provider>
    )

}
export const useRolesContext =()=>useContext(RolesContext);
export { RolesContext, RolesProvider };