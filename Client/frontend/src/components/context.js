import {createContext, useContext , useState} from 'react'
const SidebarContext = createContext();

const SidebarProvider =({children})=>{
    const [expanded ,setExpanded]=useState(false);


    return (
        <SidebarContext.Provider value={{expanded ,setExpanded}}>
            {children}

        </SidebarContext.Provider>
    )
}

export const useGlobalContext =()=>useContext(SidebarContext);
export { SidebarContext, SidebarProvider };