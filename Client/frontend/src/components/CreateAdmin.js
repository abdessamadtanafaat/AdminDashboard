import {RolesList ,FormInput} from '../components'
import {Save} from 'lucide-react'
const CreateAdmin = () => {
  return (
    <dialog id="my_modal_1" className="modal">
          <div className="modal-box min-h-14 w-3/4 max-w-4xl">
            <h3 className="font-bold text-lg mb-4">Create Administator</h3>
            <div className="my-5 mx-auto w-1/3"><FormInput label="Enter the Email" type="email" name="email" placeholder="email@email.com"/></div>
            <div className="flex justify-between gap-4">
              <div className="w-60 h-80 bg-base-200">
                <RolesList/>
              </div>
              <div className="flex-col flex justify-evenly place-items-center">
                <button className="btn btn-secondary text-base-content w-20 px-3 ">Grant</button>
                <button className="btn btn-error text-base-content  w-20">Revoke</button>
                <button className="btn btn-error text-base-content  w-20">Revoke All</button>
                

              </div>
              <div className="w-60 h-80 bg-base-200">320×568</div>
            </div>
            
            
            <div className="modal-action">
              <form method="dialog">
                {/* if there is a button, it will close the modal */}
                <button className="btn btn-error text-base-content ">Cancel</button>
              </form>
              <button className="btn btn-primary text-base-content  "> <Save />Create</button>
            </div>
          </div>
        </dialog>
  )
}

export default CreateAdmin
