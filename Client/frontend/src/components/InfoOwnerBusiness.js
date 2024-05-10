import React, { useState,useEffect,navigate  } from "react";
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux'; 
import axios from 'axios'
import { PencilIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import {PiPencilSimpleDuotone , PiTrashDuotone} from 'react-icons/pi'
import avatar_default from '../assets/default_avatar.webp'

const EditOwnerForm = React.memo(({businessId, onClose }) => {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [phone, setPhone] = useState('');
    const [newPassword, setNewPassword] = useState('');

    const navigate = useNavigate();

    //console.log(ownerId)
    const [ownerInfo, setOwnerInfo] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        newPassword: ''
    });


    const admin = useSelector(state => state.adminState.admin);

    useEffect(() => {
        if (businessId) {
            customFetch(`/tables/business/${businessId}/users`, {
                headers: { Authorization: `Bearer ${admin.token}` }
            })
            .then(res => {
                if (res.data.length > 0) {
                    const userData = res.data[0]; // Suppose que vous traitez seulement le premier utilisateur
                    const { firstName, lastName, email, username, avatarUrl,phone } = userData;
                    console.log('Owner Info:', userData);
                    setFirstName(firstName);
                    setLastName(lastName);
                    setEmail(email);
                    setUsername(username);
                    setPhone(phone);
                    setSelectedImage(avatarUrl);
                } else {
                    console.error('No user data found');
                }
            })
            .catch(err => console.log(err))
        }
    }, [businessId, admin.token]);

    
    console.log(firstName);

    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setOwnerInfo(prevOwnerInfo => ({
            ...prevOwnerInfo,
            [name]: value
        }));
    };


    const handleClose = (e) => {
        e.preventDefault(); 
        onClose();
    };
    
    // const handleSubmit = async () => {
        
    //     try {
    //         const response =  customFetch(`tables/editOwner/${businessId}?firstName=${firstName}&lastName=${lastName}&email=${email}&password=${newPassword}&username=${username}`, {
    //             method: 'PATCH',
    //             headers: {
    //                 Authorization: `Bearer ${admin.token}`
    //             },

    //         });
    //         toast.success(`Account ${firstName} successfully updated`);
    //         console.log(response);
    //         navigate("/business-owner");
    //         onClose();
    
    //     } catch (error) {
    //         // Handle errors here
    //         toast.error(error);
    //         console.error('Error:', error);
    //     }
    // };
    
    
    const [selectedImage, setSelectedImage] = useState(admin.avatarUrl);
    const [imageFile , setImageFile] = useState(null)


    return (

        <div className="modal-box flex flex-col items-center justify-center z-50">
            <form className="grid place-content-center">
                <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={handleClose}>✕</button>
                <div className="avatar w-32 mx-auto mb-3 ">
          <div className="mx-auto mask rounded-lg">
            <img src={selectedImage ||avatar_default} />
          </div>
        </div>
                <div className="my-5 mx-auto flex flex-col justify-center gap-3">
                    <div className="flex gap-4">
                        <div className="form-control">
                            <label className="label">
                                <span className="label-text capitalize font-semibold">First Name</span>
                            </label>
                            <input
                                type="text"
                                name="firstName"
                                placeholder="Enter First Name"
                                className="input input-bordered input-accent"
                                value={firstName}
                                disabled 
                                onChange={handleChange}
                                autoComplete="given-name" 
                            />
                        </div>
                        <div className="form-control">
                            <label className="label">
                                <span className="label-text capitalize font-semibold">Last Name</span>
                            </label>
                            <input
                                type="text"
                                name="lastName"
                                placeholder="Enter Last Name"
                                className="input input-bordered input-accent"
                                value={lastName}
                                disabled 
                                onChange={handleChange}
                                autoComplete="given-name"
                            />
                        </div>
                    </div>
                    <div className="flex gap-4">
                        <div className="form-control">
                            <label className="label">
                                <span className="label-text capitalize font-semibold">Email</span>
                            </label>
                            <input
                                type="email"
                                name="email"
                                placeholder="Enter Email"
                                className="input input-bordered input-accent"
                                value={email}
                                disabled 
                                onChange={handleChange}
                                autoComplete="given-name" 
                            />
                        </div>
                        <div className="form-control">
                            <label className="label">
                                <span className="label-text capitalize font-semibold">Username</span>
                            </label>
                            <input
                                type="text"
                                name="username"
                                placeholder="Enter Username"
                                className="input input-bordered input-accent"
                                value={username}
                                disabled
                                onChange={handleChange}
                                autoComplete="given-name" 
                            />
                        </div>
                    </div>

                    <div className="form-control">
                            <label className="label">
                                <span className="label-text capitalize font-semibold">Phone</span>
                            </label>
                            <input
                                type="text"
                                name="lastName"
                                placeholder="Enter Last Name"
                                className="input input-bordered input-accent"
                                value={phone}
                                disabled 
                                onChange={handleChange}
                                autoComplete="given-name"
                            />
                        </div>

                    <div className="flex gap-4 items-center">
                    <div className="form-control">
                    </div>
                    </div>
                </div>
            </form>
        </div>
    );
});

export default EditOwnerForm;
