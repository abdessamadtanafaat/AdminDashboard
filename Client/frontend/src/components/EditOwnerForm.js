import React, { useState,useEffect,navigate  } from "react";
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux'; 
import axios from 'axios'
import { Lock, PencilIcon, Save } from "lucide-react";
import { useNavigate } from "react-router-dom";
import {PiPencilSimpleDuotone , PiTrashDuotone} from 'react-icons/pi'
import avatar_default from '../assets/default_avatar.webp'

const EditOwnerForm = React.memo(({ownerId, onClose }) => {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
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
        
        if (ownerId) {
                    customFetch (`/tables/owner?ownerId=${ownerId}`, {
                    headers: { Authorization: `Bearer ${admin.token}` }
                })
                    .then(res => {
                        const { firstName, lastName, email, username, avatarUrl } = res.data;
                        console.log('Owner Info:', res.data);
                        setFirstName(firstName);
                        setLastName(lastName);
                        setEmail(email);
                        setUsername(username);
                        setSelectedImage(avatarUrl);
                    })
                    .catch(err => console.log(err))
                }
    }, [ownerId, admin.token]) 


    
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
    
    const handleSubmit = async () => {
        
        setLoading(true); 
        try {
            const response =  customFetch(`tables/editOwner/${ownerId}?firstName=${firstName}&lastName=${lastName}&email=${email}&password=${newPassword}&username=${username}`, {
                method: 'PATCH',
                headers: {
                    Authorization: `Bearer ${admin.token}`
                },

            });
            toast.success(`Account ${firstName} successfully updated`);
            console.log(response);
            navigate("/business-owner");
            onClose();
    
        } catch (error) {
            // Handle errors here
            toast.error(error);
            console.error('Error:', error);
        } finally {
            setLoading(false);
          }
    };
    
    
    const generateRandomPassword = () => {
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
        const passwordLength = 10;
        let newPassword = '';
        for (let i = 0; i < passwordLength; i++) {
            const randomIndex = Math.floor(Math.random() * characters.length);
            newPassword += characters[randomIndex];
        }

        setNewPassword(newPassword);
        console.log(newPassword)
        setShowSubmitButton(true); 
        toast.success("Password generated successfully");


    };
    
    const [selectedImage, setSelectedImage] = useState(admin.avatarUrl);
    const [imageFile , setImageFile] = useState(null)
    const [showSubmitButton, setShowSubmitButton] = useState(false);
    const [loading, setLoading] = useState(false); 

    return (
        <div className="modal-box flex flex-col items-center justify-center z-50">
            <form onSubmit={handleSubmit} className="grid place-content-center">
                <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={handleClose}>✕</button>
                <div className="avatar w-32 mx-auto mb-3 ">
                    <div className="mx-auto mask rounded-lg">
                        <img src={selectedImage || avatar_default} alt="User Avatar" />
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
                    <div className="flex gap-6">
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
                    <div className="flex gap-4 items-center justify-center">
                        <div className="form-control flex flex-col items-center">
                            <label className="label">
                                <span className="label-text capitalize font-semibold justify-center">Generate New Password</span>
                            </label>
                            <button
                                type="button"
                                className="btn btn-success h-8 w-24"
                                onClick={generateRandomPassword}
                            >
                                <Lock className='w-4 h-4 justify-center' />
                            </button>
                            <p className="text-xs text-gray-500">Click to generate a new password</p>
                        </div>
                        {showSubmitButton && (
                                                    <div className="form-control flex flex-col items-center">

                                                        <label className="label">
                                                        <span className="label-text capitalize font-semibold">Unlock Account</span>
                                                    </label>
                            <button type="submit"
                                    className="capitalize tracking-wide btn btn-accent btn-block h-8 w-24"
                                    disabled={loading}
                                    >
        {loading ? (
          <span className="loading loading-spinner loading-sm"></span>
        ) : (
          <Save className='w-4 h-4' />
        )}
      </button>
                            <p className="text-xs text-gray-500">Click to unlock the user's account</p>

                            </div>

                        )}
                    </div>
                </div>
            </form>
        </div>
    );
    
});

export default EditOwnerForm;
