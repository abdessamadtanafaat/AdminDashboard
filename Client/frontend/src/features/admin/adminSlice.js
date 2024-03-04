import { createSlice } from "@reduxjs/toolkit";
const defaultState = {
    admin : {firstname : "ilias",lastname:"rouchdi"} , 
    theme : 'winter', 
    error : null 
    
}

const adminSlice = createSlice({
    name :'admin',
    initialState : defaultState , 
    reducers : {
        loginAdmin: (state , action)=>{
            
        },
        logoutAdmin : (state , action)=>{
            alert("Logout");
        },
        toggleTheme : (state, action)=>{
            alert("Theme switeched");
        }
    }
})

export const {loginAdmin , logoutAdmin ,toggleTheme} =adminSlice.actions;
export default adminSlice.reducer;