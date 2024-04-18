import { createContext } from "react";
import { useLoaderData } from "react-router-dom";
import { useState , useContext } from "react";
const RolesContext = createContext();
const RolesProvider = ({children})=>{
    const {systemRoles , roles} = useLoaderData() || {admin :{} , roles : [] ,systemRoles:[] }
    const [predefinedRoles , setPredefinedRoles] =useState(systemRoles);
    const [grantedRoles ,setGrantedRoles] = useState(roles);
    const [checkedPredefinedRoles ,setCheckedPredefinedRoles] =useState([])
    const [checkedGrantedRoles ,setCheckedGrantedRoles] = useState([]); 
    
    const handleGrantButtonClick = () => {
      const uncheckedPredefinedRoles = predefinedRoles.filter(role => !checkedPredefinedRoles.includes(role));
      setGrantedRoles([...grantedRoles , ...checkedPredefinedRoles])

      setPredefinedRoles(uncheckedPredefinedRoles);
      setCheckedPredefinedRoles([]);
       // Clear checked roles
    };
    const handleRevokeButtonClick=()=>{
      const uncheckedGrantedRoles = grantedRoles.filter(role => !checkedGrantedRoles.includes(role));
      

      setPredefinedRoles([...predefinedRoles , ...checkedGrantedRoles]);
      setGrantedRoles(uncheckedGrantedRoles)
      setCheckedGrantedRoles([]);
      

    }
    const handleRevokeAllButtonClick=()=>{
      setPredefinedRoles([...predefinedRoles,...grantedRoles])
      setGrantedRoles([]);
      // Clear checked granted roles
      setCheckedGrantedRoles([]);

    }
  
    return (
        <RolesContext.Provider value={{predefinedRoles , setPredefinedRoles , grantedRoles ,setGrantedRoles , checkedPredefinedRoles ,setCheckedPredefinedRoles , 
        checkedGrantedRoles , setCheckedGrantedRoles , handleGrantButtonClick , handleRevokeAllButtonClick ,handleRevokeButtonClick}}>
            {children}
        </RolesContext.Provider>
    )

}
export const useRolesContext =()=>useContext(RolesContext);
export { RolesContext, RolesProvider };