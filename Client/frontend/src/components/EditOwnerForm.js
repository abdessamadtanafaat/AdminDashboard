import React, { useState,useEffect,navigate  } from "react";
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux'; 
import axios from 'axios'
import { PencilIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";

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
                        const { firstName, lastName, email, username } = res.data;
                        console.log('Owner Info:', res.data);
                        setFirstName(firstName);
                        setLastName(lastName);
                        setEmail(email);
                        setUsername(username);
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
        }
    };
    
    
    



    const generateRandomPassword = () => {
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+{}|:<>?';
        const passwordLength = 10;
        let newPassword = '';
        for (let i = 0; i < passwordLength; i++) {
            const randomIndex = Math.floor(Math.random() * characters.length);
            newPassword += characters[randomIndex];
        }

        setNewPassword(newPassword);
        console.log(newPassword)
    };
    
    return (

        <div className="modal-box flex flex-col items-center justify-center z-50">
            <form onSubmit={handleSubmit} className="grid place-content-center">
                <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={handleClose}>✕</button>
                <h3 className="font-bold text-lg">Edit Owner</h3>
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
                    <div className="flex gap-4 items-center">
                    <div className="form-control flex-1">
                        <label className="label">
                            <span className="label-text capitalize font-semibold">New Password</span>
                        </label>
                        <input
                            type="password"
                            name="newPassword"
                            placeholder="Enter New Password"
                            className="input input-bordered input-accent"
                            value={newPassword}
                            onChange={handleChange}
                            autoComplete="new-password"
                        />
                    </div>
                    <div className="form-control">
                    <label className="label">
                            <span className="label-text capitalize font-semibold">Generate</span>
                        </label>
                        
                        <button
                            type="button"
                            className="btn btn-outline"
                            onClick={generateRandomPassword} // Appel de la fonction pour générer un mot de passe
                        >
            <PencilIcon className='w-4 h-4' />
                        </button>
                    </div>
                    </div>
                </div>
                <div className="mt-4">
                    <button type="submit" className="capitalize tracking-wide btn btn-accent btn-block">Save Changes</button>
                </div>
            </form>
        </div>
    );
});

export default EditOwnerForm;
