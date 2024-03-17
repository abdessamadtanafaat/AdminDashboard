import { createSlice } from "@reduxjs/toolkit";

const themes = {
    emerald: "emerald" , 
    dim :"dim"
}
const getThemeFromLocalStorage = ()=>{
    const theme = localStorage.getItem('theme') || themes.emerald;
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
            const admin = {...action.payload.admin , token:action.payload.jwt};
            state.admin = admin; 
            localStorage.setItem("admin" , admin)
            
        },
        logoutAdmin : (state , action)=>{
            alert("Logout");
        },
        toggleTheme : (state)=>{
            const { dim, emerald } = themes;
            state.theme = state.theme === dim ? emerald : dim;
            document.documentElement.setAttribute('data-theme', state.theme);
            localStorage.setItem('theme', state.theme);
        }
    }
})
export const selectTheme =(state)=>state.theme
export const {loginAdmin , logoutAdmin ,toggleTheme} =adminSlice.actions;
export default adminSlice.reducer;