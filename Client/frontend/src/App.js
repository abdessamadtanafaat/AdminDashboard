import { RouterProvider  , createBrowserRouter } from "react-router-dom";
import { Login ,HomeLayout , Error } from "./pages";
const routes = createBrowserRouter([
  {
    path : "/",
    element : <HomeLayout/>,
    errorElement : <Error/>
  },
  {
    path :"/login",
    element : <Login/>,
    errorElement : <Error/>
  }

])
function App() {
  return <RouterProvider router={routes}/>;
}

export default App;
