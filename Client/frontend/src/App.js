import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout ,SingleAdmin ,  Error , ForgotPassword , ResetPassword , Landing } from "./pages";
import {Profile , Tables ,BusinessOwner  , Admins, CreateAdmin , ErrorElement} from './components'
import {action as loginAction} from './pages/Login'
import { RolesProvider } from "./components/RolesContext";
import {action as forgotPasswordAction} from './pages/ForgotPassword'
import {action as resetPasswordAction} from './pages/ResetPassword'
import {action as createAdminAction} from './components/CreateAdmin'
import {loader as resetPasswordLoader} from './pages/ResetPassword'
import {loader as homeLayoutLoader} from './pages/HomeLayout'
import {loader as adminsLoader} from './components/Admins'
import {loader as createAdminLoader} from './components/CreateAdmin'
import {loader as singleAdminLoader} from './pages/SingleAdmin'
import {store} from './app/store'

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
        element:<RolesProvider>
          <CreateAdmin/>
        </RolesProvider>,
        loader:createAdminLoader(store),
        errorElement:<ErrorElement/>
      },
    
      {
        path:"/admin/:adminId",
        element:<RolesProvider>
        <SingleAdmin/>
      </RolesProvider>,
        loader:singleAdminLoader(store),
        errorElement:<ErrorElement/>
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
