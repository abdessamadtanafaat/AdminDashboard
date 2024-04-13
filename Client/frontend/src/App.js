import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout , Error , ForgotPassword , ResetPassword , Landing } from "./pages";
import {Profile , Tables ,BusinessOwner  , AdminManager} from './components'
import {action as loginAction} from './pages/Login'
import {action as forgotPasswordAction} from './pages/ForgotPassword'
import {action as resetPasswordAction} from './pages/ResetPassword'
import {loader as resetPasswordLoader} from './pages/ResetPassword'
import {loader as homeLayoutLoader} from './pages/HomeLayout'
import {loader as adminManagerLoader} from './components/AdminManager'
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
        path:"/admin-manager",
        element :<AdminManager/>,
        loader :adminManagerLoader(store)
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
