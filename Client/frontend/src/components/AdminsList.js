import { useLoaderData  , Link} from "react-router-dom"
import default_avatar from '../assets/default_avatar.webp'
import { CogIcon } from "@heroicons/react/solid";
const AdminsList = () => {
    const {admins , params} = useLoaderData();
     
    if(!admins || admins.length <1){
      return (<div className="font-bold mx-auto  text-4xl text-center text-error">There is no match for the keyword You Typed !!! </div>)

    }
    return (
    <div className="overflow-x-auto">
        <table className="table table-zebra-zebra">
          {/* head */}
            <thead>
                <tr>
                    
                    <th>        </th>
                    <th className="text-center" >Profile</th>
                    <th className="text-center" >Full Name</th>   
                    <th className="text-center" >Role</th>   
                    <th className="text-center" >Status</th>   

                                     
                </tr>
            </thead>
            <tbody>
              {admins.map((admin)=>{
                const {fullname,firstname , lastname ,email ,avatarUrl , username , id, roles,active} = admin;  
                return (
                    <tr key={id}>
                        <th>
                            <label className="flex justify-center gap-2">
                              <input type="checkbox" className="checkbox" />
                            </label>
                        </th>
                        <td>
                         <div className="flex items-center gap-3">
                            <div className="avatar">
                                <div className="mask mask-squircle w-12 h-12">
                                <img alt="profile image" src={avatarUrl ? `${avatarUrl}`: default_avatar} />
                                </div>
                            </div>
                        <div>
                    </div>
                     </div>
                    </td>
                    <td>
                      <div className="font-bold">{fullname}</div>
                      <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{email}
                      </div>
                      </td>
                      <td>
    <div className="font-bold">
        {active ? (
            <div class="flex items-center">
            <div class="h-2.5 w-2.5 rounded-full bg-green-400 mr-2"></div>  ONLINE
            </div>
                    ) : (
            <div class="flex items-center">
            <div class="h-2.5 w-2.5 rounded-full bg-red-500 mr-2"></div>  OFFLINE
            </div> 
               )}
    </div>
</td>
                      <td>
                      <div className="font-bold">
    {roles.map((role, index) => (
        <span key={role.id}>
            {index === 0 && role.name === 'ROLE_ADMIN' && 'ADMIN'}
            {index === 0 && role.name === 'ROLE_SUPER_ADMIN' && 'SUPER ADMIN'}
        </span>
    ))}
</div>

            </td>
                      <th>
    <button className="btn btn-active btn-sucess && btn-sm" onClick={() => {window.location.href=`/admin/${id}`}}>
        <CogIcon className='w-4 h-4'/>
        <span >Details</span>
    </button>
</th>

            </tr>
                )
              })}
    </tbody>
    
  </table>
</div>
  
  )
}

export default AdminsList
