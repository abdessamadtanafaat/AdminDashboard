import { useLoaderData  , Link} from "react-router-dom"
import default_avatar from '../assets/default_avatar.webp'
import { UserRoundCog, UserRoundCogIcon } from "lucide-react";
import { selectAdmin } from "../features/admin/adminSlice";
import { useInsertionEffect } from "react";
import { useSelector } from "react-redux";
const AdminsList = () => {

  const loggedInAdmin = useSelector(selectAdmin);

    const {admins , params} = useLoaderData();
     
    if(!admins || admins.length <1){
      return (<div className="font-bold mx-auto  text-4xl text-center text-error">There is no match for the keyword You Typed !!! </div>)

    }

    const formatDateDuration = (createdDate) => {
      const currentDate = new Date();
      const startDate = new Date(createdDate);
      const millisecondsPerSecond = 1000;
      const millisecondsPerMinute = millisecondsPerSecond * 60;
      const millisecondsPerHour = millisecondsPerMinute * 60;
      const millisecondsPerDay = millisecondsPerHour * 24;
      const millisecondsPerWeek = millisecondsPerDay * 7;
      const millisecondsPerMonth = millisecondsPerDay * 30;
      const millisecondsPerYear = millisecondsPerDay * 365;
  
      const elapsedMilliseconds = currentDate - startDate;
  
      if (elapsedMilliseconds < millisecondsPerMinute) {
          return 'Just now';
      } else if (elapsedMilliseconds < millisecondsPerHour) {
          const minutes = Math.floor(elapsedMilliseconds / millisecondsPerMinute);
          return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
      } else if (elapsedMilliseconds < millisecondsPerDay) {
          const hours = Math.floor(elapsedMilliseconds / millisecondsPerHour);
          return `${hours} hour${hours > 1 ? 's' : ''} ago`;
      } else if (elapsedMilliseconds < millisecondsPerWeek) {
          const days = Math.floor(elapsedMilliseconds / millisecondsPerDay);
          return `${days} day${days > 1 ? 's' : ''} ago`;
      } else if (elapsedMilliseconds < millisecondsPerMonth) {
          const weeks = Math.floor(elapsedMilliseconds / millisecondsPerWeek);
          return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
      } else if (elapsedMilliseconds < millisecondsPerYear) {
          const months = Math.floor(elapsedMilliseconds / millisecondsPerMonth);
          return `${months} month${months > 1 ? 's' : ''} ago`;
      } else {
          const years = Math.floor(elapsedMilliseconds / millisecondsPerYear);
          return `${years} year${years > 1 ? 's' : ''} ago`;
      }
  };

  const formatDate = (createdDate) => {
    const date = new Date(createdDate);
    const year = date.getFullYear();
    const month = date.getMonth() + 1; 
    const day = date.getDate();

    const formattedDate = `${month}/${day}/${year}`;
    return formattedDate;
};

const formatTime = (createdDate) => {
    const date = new Date(createdDate);

    let hours = date.getHours();
    let minutes = date.getMinutes();

    const amOrPm = hours >= 12 ? 'PM' : 'AM';

    hours = hours % 12;
    hours = hours ? hours : 12;

    minutes = minutes < 10 ? '0' + minutes : minutes;

    return `${hours}:${minutes}`+` `+`${amOrPm}`;
};
const formatDuration = (createdDate) => {
    const date = new Date(createdDate);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${month}/${day}/${year}`;
};

const sortedAdmins = admins.sort((a, b) => {
  return new Date(b.lastLogin) - new Date(a.lastLogin);
});
    return (
    <div className="overflow-x-auto">
        <table className="table table-zebra-zebra">
          {/* head */}
            <thead>
                <tr>
                    
                    <th>        </th>
                    <th className="text-center" >Profile</th>
                    <th className="text-center" >Full Name</th> 
                    <th className="text-center" >Last LogIn</th>     
                    <th className="text-center" >Joined in</th> 
    
                    {/* <th className="text-center" >Role</th>    */}
                    {/* <th className="text-center" >Status</th>    */}

                                     
                </tr>
            </thead>
            <tbody>
              {sortedAdmins.map((admin)=>{
                const {fullname,firstname , lastname ,email ,avatarUrl , username , id, roles,active,lastLogout,lastLogin,joined_in} = admin;  
                return (
                    <tr key={id}>
                        <th>
                            <label className="flex justify-center gap-2">
                              <input type="checkbox" className="checkbox" />
                            </label>
                        </th>
                        <td>
                         <div className="flex items-center gap-3">

                            <div className="avatar relative">
        <div className="mask mask-squircle w-12 h-12">
            <img alt="profile image" src={avatarUrl ? `${avatarUrl}` : default_avatar} />
        </div>
        {active ? (
            <div className="absolute -top-0 -right-0 flex items-center justify-center w-2 h-2 rounded-full bg-green-400 text-white">
            </div>
        ): (
            <div className="absolute -top-0 -right-0 flex items-center justify-center w-2 h-2 rounded-full bg-red-500 text-white">
            </div>
        )}
    </div>

                        <div>
                    </div>
                     </div>
                    </td>
                    <td>
                      <div className="font-bold">{firstname} {lastname}</div>

                      <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{email}
                      </div>
                      </td>
                      <td>
  {lastLogin ? (
    <>
      <div>{formatDateDuration(lastLogin)}</div>
      <div className="text-sm font-normal text-gray-500 dark:text-gray-400" style={{ fontSize: '0.8em' }}>
        {formatDuration(lastLogin)}
      </div>
    </>
  ) : (
    '--'
  )}
</td>
<td>
  {joined_in ? (
    <>
      <div>{formatDate(joined_in)}</div>
      <div className="text-sm font-normal text-gray-500 dark:text-gray-400" style={{ fontSize: '0.8em' }}>
        {formatTime(joined_in)}
      </div>
    </>
  ) : (
    '--'
  )}
</td>

                      
                      {/* <td>
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
</td> */}
                      <td>
                      {/* <div className="font-bold">
    {roles.map((role, index) => (
        <span key={role.id}>
            {index === 0 && role.name === 'ROLE_ADMIN' && 'ADMIN'}
            {index === 0 && role.name === 'ROLE_SUPER_ADMIN' && 'SUPER ADMIN'}
        </span>
    ))}
</div> */}

            </td>
                      <th>
                      {loggedInAdmin.id !== admin.id ? (
                <button 
                  className="btn btn-active btn-success btn-sm" 
                  onClick={() => { window.location.href = `/admin/${admin.id}` }}
                >
                  <UserRoundCogIcon className='w-5 h-5' />
                </button>
              ) : (
                <button 
                  className="btn btn-disabled btn-success btn-sm" 
                  disabled
                >
                  <UserRoundCogIcon className='w-5 h-5' />
                </button>
              )}

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