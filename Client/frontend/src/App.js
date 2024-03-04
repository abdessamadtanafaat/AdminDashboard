import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout , Error } from "./pages";
import {action as loginAction} from './pages/Login'
import {store} from './app/store'
const routes = createBrowserRouter([
  {
    path : "/",
    element : <HomeLayout/>,
    errorElement : <Error/>
  },
  {
    path :"/login",
    element : <Login/>,
    errorElement : <Error/>,
    action: loginAction(store)
  }

])
function App() {
  return <RouterProvider router={routes}/>;
}

export default App;
