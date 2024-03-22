import { createSlice } from "@reduxjs/toolkit";
import {toast} from 'react-toastify'
const themes = {
    nord: "nord" , 
    dracula :"dracula"
}
const getThemeFromLocalStorage = ()=>{
    const theme = localStorage.getItem('theme') || themes.nord;
    document.documentElement.setAttribute('data-theme', theme);
    return theme
}

const getAdminFromLocalStorage=()=>{

    return JSON.parse(localStorage.getItem("admin")) || null 
}

const defaultState = {
    admin : getAdminFromLocalStorage(),
    theme :getThemeFromLocalStorage()
    
}

const adminSlice = createSlice({
    name :'admin',
    initialState : defaultState , 
    reducers : {
        loginAdmin: (state , action)=>{
            console.log("hey there")
            const admin = {...action.payload.admin , token:action.payload.jwt};
            state.admin = admin; 
            console.log(admin )
            localStorage.setItem("admin" , JSON.stringify(admin))
            
        },
        logoutAdmin : (state , action)=>{
            state.admin = null 
            localStorage.removeItem("admin")
            
            toast.success('Logged out successfully');
        },
        toggleTheme : (state)=>{
            const { dracula, nord } = themes;
            state.theme = state.theme === dracula ? nord : dracula;
            document.documentElement.setAttribute('data-theme', state.theme);
            localStorage.setItem('theme', state.theme);
        }
    }
})
export const selectTheme =(state)=>state.adminState.theme
export const selectAdmin =(state)=>state.adminState.admin
export const {loginAdmin , logoutAdmin ,toggleTheme} =adminSlice.actions;
export default adminSlice.reducer;