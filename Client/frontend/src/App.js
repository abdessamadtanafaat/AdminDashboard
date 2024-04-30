import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout ,SingleAdmin ,  Error , ForgotPassword , ResetPassword , Landing, ServiceAreaManager } from "./pages";
import {Profile , Tables , BusinessOwners  ,Business, Admins, CreateAdmin , ErrorElement ,CreateRole, Campaigns } from './components'

import {action as loginAction} from './pages/Login'
import {action as forgotPasswordAction} from './pages/ForgotPassword'
import {action as resetPasswordAction} from './pages/ResetPassword'
import {action as createAdminAction} from './components/CreateAdmin'
import {loader as resetPasswordLoader} from './pages/ResetPassword'
import {loader as homeLayoutLoader} from './pages/HomeLayout'
import {loader as adminsLoader} from './components/Admins'
import {loader as businessOwnerLoader} from './components/BusinessOwners'; 
import {loader as businessLoader} from './components/Business'; 
import {loader as campaignLoader} from './components/Campaigns'; 


import {loader as createAdminLoader} from './components/CreateAdmin'
import {loader as singleAdminLoader} from './pages/SingleAdmin'
import {loader as createRoleLoader} from './components/CreateRole'
import {loader as serviceAreaManagerLoader} from './pages/ServiceAreaManager'

import {store} from './app/store'
import { ItemsProvider } from "./components/ItemContext";

const routes = createBrowserRouter([
  {
    path : "/",
    element : <HomeLayout/>,
    errorElement : <Error/>,
    loader : homeLayoutLoader(store),
    children:[
      {
          index :true ,
        element : <Landing/>

      },
      {
        path:"/profile",
        element:<Profile/>
      },
      {
        path: "/business-owner",
        element: <BusinessOwners/>,
        loader: businessOwnerLoader(store),
        errorElement: <ErrorElement />
      },
      
      {
        path: "/business",
        element: <Business/>,
        loader: businessLoader(store),
        errorElement: <ErrorElement />
      },
      {
        path: "/campaign",
        element: <Campaigns/>,
        loader: campaignLoader(store),
        errorElement: <ErrorElement />
      },


      {
        path:"/admins",
        element: <Admins/>,
        loader :adminsLoader(store),
        errorElement:<ErrorElement/>
      },
      {
        path:"/admin/create-admin",
        element:<ItemsProvider>
          <CreateAdmin/>
        </ItemsProvider>,
        loader:createAdminLoader(store),
        errorElement:<ErrorElement/>
      },
    
      {
        path:"/admin/:adminId",
        element:<ItemsProvider>
        <SingleAdmin/>
      </ItemsProvider>,
        loader:singleAdminLoader(store),
        errorElement:<ErrorElement/>
      },
      {
        path:"/role/create-role",
        element:<ItemsProvider>
          <CreateRole/>
        </ItemsProvider>,
        loader : createRoleLoader(store)
      },
      {
        path:"/service-area",
        element:<ServiceAreaManager/>, 
        loader: serviceAreaManagerLoader(store)
      }

    ]
  },
  {
    path :"/login",
    element : <Login/>,
    errorElement : <Error/>,
    action: loginAction(store)
  },
  {
    path :"/forgotPassword",
    element : <ForgotPassword/>,
    errorElement : <Error/>,
    action: forgotPasswordAction(store)
  }
  ,
  {
    path : "/reset-password",
    element : <ResetPassword/>,
    errorElement:<Error/>,
    action : resetPasswordAction(store),
    loader : resetPasswordLoader
 }
  

])
function App() {
  return <RouterProvider router={routes}/>;
}

export default App;
