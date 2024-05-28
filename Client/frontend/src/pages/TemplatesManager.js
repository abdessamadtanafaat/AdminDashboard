import { faces, stars } from '../utils';
import {logoLight , logoDark} from '../assets';
import { selectTheme } from '../features/admin/adminSlice';
import { useSelector } from 'react-redux';

const TemplatesManager = () => {
  const theme = useSelector(selectTheme)
  return (
    <div className="grid place-content-center items-center">
      <div className="mx-auto carousel w-5/6 max-w-2xl p-4 flex justify-between gap-10 overflow-hidden">
        <div id="template1" className="shadow-md w-full h-full p-2 rounded-lg flex carousel-item justify-between gap-3 border border-outline border-primary bg-accent bg-opacity-25">
          <div className="grid w-2/6 place-content-center mx-auto items-center">
            <img src={theme =="nord"?logoDark : logoLight} alt="" />
            <p className='text-align font-semibold italic'>A dynamic and colorful layout showcasing various star images. Perfect for creative projects.</p>
          </div>
          <ul className="w-4/6 h-full p-2 flex justify-between items-center flex-wrap gap-3">
            {
              stars.map((star, index) => (
                <li
                  key={index}
                  className="mr-2 text-sm p-1 rounded-md cursor-pointer mx-auto grid place-content-center items-center"
                >
                  <img src={star?.url} alt="" className="object-fit w-20 h-30" />
                </li>
              ))
            }
          </ul>
        </div>
        <div id="template2" className="shadow-md w-full h-full p-2 rounded-lg flex carousel-item justify-between gap-3 border border-outline border-primary bg-accent bg-opacity-25">
          <div className="grid w-2/6 place-content-center mx-auto items-center">
            <img src={theme==="nord"?logoDark: logoLight} alt="" />
            <p className='text-align font-semibold italic'>A sleek and minimalistic design focused on displaying various face images. Ideal for professional use.</p>
          </div>
          <ul className="w-4/6 h-full p-2 flex justify-between items-center flex-wrap gap-6">
            {
              faces.map((face, index) => (
                <li
                  key={index}
                  className="mr-2 text-sm p-1 rounded-md cursor-pointer mx-auto grid place-content-center items-center"
                >
                  <img src={face?.url} alt="" className="object-fit w-14" />
                </li>
              ))
            }
          </ul>
        </div>
      </div>
      <div className="mx-auto flex justify-center flex-wrap w-3/4 max-w-2xl py-2 gap-2">
        <a href="#template1" className="btn btn-sm">
          Template1
        </a>
        <a href="#template2" className="btn btn-sm">
          Template2
        </a>
      </div>
    </div>
  );
};

export default TemplatesManager;
