import {Form , Link} from 'react-router-dom'
import {FormInput , SubmitBtn} from '../components'
import {useState} from 'react'
import avatar_default from '../assets/default_avatar.webp'
import { useSelector } from 'react-redux';
import { selectAdmin } from '../features/admin/adminSlice'
import { PiPencilSimpleDuotone } from "react-icons/pi"
const Profile = () => {
  const {firstname ,lastname , email , avatarUrl} = useSelector(selectAdmin);
  const [selectedImage ,setSelectedImage]=useState(null);
  const handleImageUpload = (event)=>{
    event.preventDefault();
    const file = event.target.files[0];
    if(file){
      const reader = new FileReader();
      reader.onloadend = ()=>{
        setSelectedImage(reader.result)
        console.log(selectedImage)

      }    
      reader.readAsDataURL(file); 
    }

  }
  return (
    
    <Form method="POST" className='w-full p-8 shadow-lg'>
        <div className="relative  w-1/3 mx-auto">
          <img src={selectedImage || avatarUrl || avatar_default} alt="Profile Image" className="rounded-lg w-full mx-auto" />
          <label htmlFor="upload-button" className="absolute -top-2 -right-4 text-accent-content bg-accent p-2 text-2xl rounded-full font-bold cursor-pointer">
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
        </div>
        <FormInput type="text" label="fistname" name="fistname" value={firstname}/>
        <FormInput type="text" label="lastname" name="lastname" value={lastname}/>
        <FormInput type="text" label="Email" name="email" value={email} disabled/>
        <div className="mt-4">
          <SubmitBtn text="save changes"/>
        </div>
    </Form>
    
  )
}

export default Profile
