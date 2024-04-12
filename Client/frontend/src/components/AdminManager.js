import {toast} from 'react-toastify'
import { customFetch } from '../utils'
import {Plus , Save} from 'lucide-react'
export const loader = async()=>{
  try{
    const response = await customFetch("/super-admin/admins" )
    console.log(response.data)
    
  }
  catch(err){
    console.log(err)
    const errMessage  = err?.response?.data || "Cannot load Table Admin"
    toast.error(errMessage)
  

  }


}
const AdminManager = () => {
  return (
    <>
    <div className="flex w-full justify-between mb-3">
        <h1>Administration</h1> 
        <button className="btn btn-active btn-accent" onClick={()=>document.getElementById('my_modal_1').showModal()}> <Plus />Add New Administator</button>
        <dialog id="my_modal_1" className="modal">
          <div className="modal-box min-h-14 w-3/4 max-w-4xl">
            <h3 className="font-bold text-lg mb-4">Create Administator</h3>
            <div className="flex"></div>
            <div className="flex justify-between gap-4">
              <div className="w-60 h-80 bg-base-200">Roles</div>
              <div className="flex-col flex justify-evenly place-items-center">
                <button className="btn btn-accent w-20 px-3">Grant</button>
                <button className="btn btn-error w-20">Revoke</button>
                <button className="btn btn-error w-20">Revoke All</button>
                

              </div>
              <div className="w-60 h-80 bg-base-200">320×568</div>
            </div>
            
            
            <div className="modal-action">
              <form method="dialog">
                {/* if there is a button, it will close the modal */}
                <button className="btn btn-error">Close</button>
              </form>
              <button className="btn btn-primary text-black "> <Save />Create</button>
            </div>
          </div>
        </dialog>
      
       </div>
    
    <div className="overflow-x-auto">
        <table className="table">
          {/* head */}
        <thead>
        <tr>
        <th>
          <label>
            <input type="checkbox" className="checkbox" />
          </label>
        </th>
        <th>Name</th>
        <th>Job</th>
        <th>Favorite Color</th>
        <th></th>
        </tr>
      </thead>
      <tbody>
      {/* row 1 */}
      <tr>
        <th>
          <label>
            <input type="checkbox" className="checkbox" />
          </label>
        </th>
        <td>
          <div className="flex items-center gap-3">
            <div className="avatar">
              <div className="mask mask-squircle w-12 h-12">
                <img src="/tailwind-css-component-profile-2@56w.png" alt="Avatar Tailwind CSS Component" />
              </div>
            </div>
            <div>
              <div className="font-bold">Hart Hagerty</div>
              <div className="text-sm opacity-50">United States</div>
            </div>
          </div>
        </td>
        <td>
          Zemlak, Daniel and Leannon
          <br/>
          <span className="badge badge-ghost badge-sm">Desktop Support Technician</span>
        </td>
        <td>Purple</td>
        <th>
          <button className="btn btn-ghost btn-xs">details</button>
        </th>
      </tr>
      {/* row 2 */}
      <tr>
        <th>
          <label>
            <input type="checkbox" className="checkbox" />
          </label>
        </th>
        <td>
          <div className="flex items-center gap-3">
            <div className="avatar">
              <div className="mask mask-squircle w-12 h-12">
                <img src="/tailwind-css-component-profile-3@56w.png" alt="Avatar Tailwind CSS Component" />
              </div>
            </div>
            <div>
              <div className="font-bold">Brice Swyre</div>
              <div className="text-sm opacity-50">China</div>
            </div>
          </div>
        </td>
        <td>
          Carroll Group
          <br/>
          <span className="badge badge-ghost badge-sm">Tax Accountant</span>
        </td>
        <td>Red</td>
        <th>
          <button className="btn btn-ghost btn-xs">details</button>
        </th>
      </tr>
      {/* row 3 */}
      <tr>
        <th>
          <label>
            <input type="checkbox" className="checkbox" />
          </label>
        </th>
        <td>
          <div className="flex items-center gap-3">
            <div className="avatar">
              <div className="mask mask-squircle w-12 h-12">
                <img src="/tailwind-css-component-profile-4@56w.png" alt="Avatar Tailwind CSS Component" />
              </div>
            </div>
            <div>
              <div className="font-bold">Marjy Ferencz</div>
              <div className="text-sm opacity-50">Russia</div>
            </div>
          </div>
        </td>
        <td>
          Rowe-Schoen
          <br/>
          <span className="badge badge-ghost badge-sm">Office Assistant I</span>
        </td>
        <td>Crimson</td>
        <th>
          <button className="btn btn-ghost btn-xs">details</button>
        </th>
      </tr>
      {/* row 4 */}
      <tr>
        <th>
          <label>
            <input type="checkbox" className="checkbox" />
          </label>
        </th>
        <td>
          <div className="flex items-center gap-3">
            <div className="avatar">
              <div className="mask mask-squircle w-12 h-12">
                <img src="/tailwind-css-component-profile-5@56w.png" alt="Avatar Tailwind CSS Component" />
              </div>
            </div>
            <div>
              <div className="font-bold">Yancy Tear</div>
              <div className="text-sm opacity-50">Brazil</div>
            </div>
          </div>
        </td>
        <td>
          Wyman-Ledner
          <br/>
          <span className="badge badge-ghost badge-sm">Community Outreach Specialist</span>
        </td>
        <td>Indigo</td>
        <th>
          <button className="btn btn-ghost btn-xs">details</button>
        </th>
      </tr>
    </tbody>
    {/* foot */}
    <tfoot>
      <tr>
        <th></th>
        <th>Name</th>
        <th>Job</th>
        <th>Favorite Color</th>
        <th></th>
      </tr>
    </tfoot>
    
  </table>
</div>
  
    
    </>
    
  )
}

export default AdminManager
