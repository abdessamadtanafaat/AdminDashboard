import React, { useState, useEffect } from 'react';
import { customFetch } from '../utils'; // Import customFetch function to make API requests
import { selectAdmin , updateProfile} from '../features/admin/adminSlice';
import { useDispatch, useSelector } from 'react-redux';

const BusinessOwner = () => {
  const admin = useSelector(selectAdmin);
  const [selectedImage, setSelectedImage] = useState(admin.avatarUrl);
  const [firstname , setFirstname]= useState(admin.firstname)
  const [lastname , setLastname]= useState(admin.lastname)
  const [imageFile , setImageFile] = useState(null)
 
  const [businessOwners, setBusinessOwners] = useState([]);

  useEffect(() => {
    const fetchBusinessOwners = async () => {
      try {
        
        
        const updatedAdmin = {firstname , lastname , email : admin.email , file : imageFile}
        console.log(updatedAdmin)
        console.log(admin.token)

        const sortBy = '';
        const searchKey = '';
  
        const params = {
          headers: {
            Authorization: `Bearer ${admin.token}`,
          },
          params: {
            searchKey: '',
          },
        };
  
        if (sortBy) {
          params.params.sortBy = sortBy;
        }
  
        if (searchKey) {
          params.params.searchKey = searchKey;
        }


        const response = await customFetch.get('/admin/owners', params);
        setBusinessOwners(response.data);
      } catch (error) {
        console.error('Error fetching business owners:', error);
      }
    };

    fetchBusinessOwners();
  }, []); 

return (
  <div className="overflow-x-auto">
    <table className="table table-zebra">
      <thead>
        <tr>
          <th></th>
          <th>Name</th>
          <th>Email</th>
          <th>Avatar</th>
          <th>Last Logout</th>
          <th>Role</th>
          <th>Last Login</th>
          <th>Active</th>
        </tr>
      </thead>
      <tbody>
        {businessOwners.map((owner, index) => (
          <tr key={owner.id}>
            <th>{index + 1}</th>
            <td>{`${owner.firstname} ${owner.lastname}`}</td>
            <td>{owner.email}</td>
            <td>
              <img src={owner.avatarUrl} alt="Avatar" className="avatar" />
            </td>
            <td>{owner.lastLogout}</td>
            <td>{owner.role}</td>
            <td>{owner.lastLogin}</td>
            <td>{owner.isActive ? 'Yes' : 'No'}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);
};

export default BusinessOwner;
