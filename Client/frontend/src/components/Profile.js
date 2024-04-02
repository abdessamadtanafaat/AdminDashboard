import { useState } from 'react';
import { selectAdmin } from '../features/admin/adminSlice';
import { useSelector } from 'react-redux';
import {Form } from 'react-router-dom'
import {PiPencilSimpleDuotone} from 'react-icons/pi'
import avatar_default from '../assets/default_avatar.webp'
import { FormInput, SubmitBtn } from '../components'
const Profile = () => {
  const admin = useSelector(selectAdmin);
  const [selectedImage, setSelectedImage] = useState(admin.avatarUrl);
  const [firstname , setFirstname]= useState(admin.firstname)
  const [lastname , setLastname]= useState(admin.lastname)

  const handleImageUpload = (event) => {
    event.preventDefault();
    const file = event.target.files[0]; 

    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        
        setSelectedImage(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <section className='mx-auto grid place-items-center'>
      <Form method="POST"  className='bg-base-200 w-full p-8 shadow-lg grid place-items-center gap-5'>
        <div className="avatar w-32 mx-auto mb-3 ">
          <div class="mx-auto mask rounded-lg">
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
          </div>
        </div>
        <div className="md:flex md:flex-row md:justify-between md:gap-4">
            <FormInput type="email" label="Email" name="email" value={admin.email} disabled />
            <FormInput type="text" label="First Name" name="firstname" value={firstname} onChange={()=>{setFirstname(firstname)}} />
            <FormInput type="text" label="Last Name" name="lastname" value={lastname} onChange={()=>{setLastname(lastname)}} />
        </div>
        <div>
          <SubmitBtn text="save changes"/>
        </div>
      </Form>
      <Form>
        
      </Form>
      
    </section>
  );
};

export default Profile;
