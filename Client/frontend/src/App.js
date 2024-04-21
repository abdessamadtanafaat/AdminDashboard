import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout ,SingleAdmin ,  Error , ForgotPassword , ResetPassword , Landing } from "./pages";
import {Profile , Tables ,BusinessOwner  , Admins, CreateAdmin , ErrorElement ,CreateRole } from './components'
import {action as loginAction} from './pages/Login'
import {action as forgotPasswordAction} from './pages/ForgotPassword'
import {action as resetPasswordAction} from './pages/ResetPassword'
import {action as createAdminAction} from './components/CreateAdmin'
import {loader as resetPasswordLoader} from './pages/ResetPassword'
import {loader as homeLayoutLoader} from './pages/HomeLayout'
import {loader as adminsLoader} from './components/Admins'
import {loader as createAdminLoader} from './components/CreateAdmin'
import {loader as singleAdminLoader} from './pages/SingleAdmin'
import {loader as createRoleLoader} from './components/CreateRole'

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
        path:"/business-owner",
        element:<BusinessOwner/>
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
      }
      /* {
        path:"/role/:roleId",
        element:<ItemsProvider>
          <SingleRole/>
          </ItemsProvider> ,
        loader:singleRoleLoader(store)
      } */

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
