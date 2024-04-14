import { useState } from 'react';
import { selectAdmin , updateProfile} from '../features/admin/adminSlice';
import { useDispatch, useSelector } from 'react-redux';
import {Form } from 'react-router-dom'
import {PiPencilSimpleDuotone , PiTrashDuotone} from 'react-icons/pi'
import avatar_default from '../assets/default_avatar.webp'
import { FormInput, SubmitBtn } from '../components'
import {toast} from 'react-toastify'
import { customFetch } from '../utils';


const Profile = () => {

  const admin = useSelector(selectAdmin);
  const [selectedImage, setSelectedImage] = useState(admin.avatarUrl);
  const [firstname , setFirstname]= useState(admin.firstname)
  const [lastname , setLastname]= useState(admin.lastname)
  const [imageFile , setImageFile] = useState(null)
 

  const dispatch = useDispatch();
  const handleImageUpload = async(event) => {
    event.preventDefault();
    const file = event.target.files[0]; 
    console.log(file)

    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        
        setSelectedImage(reader.result);
        setImageFile(file)

      };
      reader.readAsDataURL(file);  
    }
    
    
  };
  const saveChanges = async(event)=>{
    event.preventDefault();
    console.log(selectedImage )
    try{
      
      
      const updatedAdmin = {firstname , lastname , email : admin.email , file : imageFile}
      console.log(updatedAdmin)
      console.log(admin.token)
      
      const response = await customFetch.patch('/admin/change-account-settings' ,updatedAdmin,
      {
        headers: { Authorization: `Bearer ${admin.token}`,'Content-Type': 'multipart/form-data' } 
      }
      )
      updatedAdmin["avatarUrl"] = selectedImage
      toast.success("Profile updated successfully")
      dispatch(updateProfile(updatedAdmin))
    }
    catch(err){
      console.log(err)
      const errMessage = err?.response?.data || "Failed to update Profile"
      toast.error(errMessage)
      
    }
  }
  const clearImage = (event)=>{
    event.preventDefault()
    setSelectedImage(null);
    setImageFile(null)

    console.log(document.querySelector('#upload-button').files[0])
  }

  return (
    <section className='mx-auto grid place-items-center'>
      <Form method="POST"  className='bg-base-200 w-full p-8 shadow-lg grid place-items-center gap-5'>
        <div className="avatar w-32 mx-auto mb-3 ">
          <div className="mx-auto mask rounded-lg">
            <img src={selectedImage ||avatar_default} />
            <label htmlFor="upload-button"      className="absolute -top-2 -right-4 text-accent-content bg-accent p-2 text-2xl rounded-full font-bold cursor-pointer">
              <PiPencilSimpleDuotone />
            </label>
              {/* Hidden file input */}
            <input
              id="upload-button"
              type="file"
              accept="image/*"
              className="hidden"
              onChange={handleImageUpload}
            />
            <button
               onClick={clearImage}
                className="absolute -bottom-2 -left-4 text-accent-content bg-accent p-2 text-2xl rounded-full font-bold cursor-pointer"
            >
              <PiTrashDuotone /> 
        </button>
            
          </div>
        </div>
        <div className="md:flex md:flex-row md:justify-between md:gap-4">
            <FormInput type="email" label="Email" name="email" value={admin.email} disabled />
            <FormInput type="text" label="First Name" name="firstname" value={firstname} onChange={(event)=>{setFirstname(event.target.value)}} />
            <FormInput type="text" label="Last Name" name="lastname" value={lastname} onChange={(event)=>{setLastname(event.target.value)}} />
        </div>
        <div onClick={saveChanges}>
          <SubmitBtn text="save changes"/>
        </div>
      </Form>
      <Form>
        
      </Form>
      
    </section>
  );
};

export default Profile;