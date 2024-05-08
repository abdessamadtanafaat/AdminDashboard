import {faces , stars} from '../utils'
const TemplatesManager = () => {
  return (
    <div className="grid place-content-center items-center">

    
        <div className="mx-auto carousel w-3/4  max-w-2xl p-4 flex justify-between gap-10 overflow-hidden ">
          <div id="template1" className="shadow-md w-full h-50 p-2 rounded-lg flex flex-col carousel-item justify-between gap-3 border border-outline border-primary">
              <div>

            </div>
            <ul className="w-11/12 flex flex-row mx-auto">
              {
              stars.map((star, index) => (
              <li
              key={index}
              className={`mr-2 text-sm p-1 border rounded-md cursor-pointer bg-white `}
              
              >
              <img src={star?.url}alt="" className="" />
            </li>
          ))
        }

        </ul>
        
          </div>
          <div id="template2" className="shadow-md w-full h-50 p-2 rounded-lg flex flex-col carousel-item justify-between gap-3 border border-outline border-primary">
            <ul className="w-11/12 flex flex-row mx-auto">
              {
              faces.map((star, index) => (
              <li
              key={index}
              className={`mr-2 text-sm p-1 border rounded-md cursor-pointer bg-white `}
              
              >
              <img src={star?.url}alt="Mal Mok" className="" />
            </li>
          ))
        }

        </ul>
        
          </div>
      

      
        </div>
        <div className=" mx-auto flex justify-center flex-wrap w-3/4  max-w-2xl  py-2 gap-2">
          <a href="#template1" className="btn btn-sm">
            Template1
          </a>
          <a href="#template2"  className="btn btn-sm">
            Template2
          </a>
  

        </div>
    </div>
  )
}

export default TemplatesManager
