import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout , Error , ForgotPassword } from "./pages";
import {action as loginAction} from './pages/Login'
import {action as forgotPasswordAction} from './pages/ForgotPassword'
import {store} from './app/store'
const routes = createBrowserRouter([
  {
    path : "/",
    element : <HomeLayout/>,
    errorElement : <Error/>,
    children:[

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

])
function App() {
  return <RouterProvider router={routes}/>;
}

export default App;
