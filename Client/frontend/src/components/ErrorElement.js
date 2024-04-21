import { useRouteError } from 'react-router-dom';
const ErrorElement = () => {
  const error = useRouteError();
  console.log(error);

  return(
  <>
   <h4 className='font-bold text-6xl text-base-content text-center'>{error.message}</h4>
   <span className="text-center text-error">{error.status}</span>
  </>);
};
export default ErrorElement;