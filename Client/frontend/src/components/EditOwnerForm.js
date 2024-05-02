import React, { useState } from "react";
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux'; 

const EditOwnerForm = ({OwnerId, onClose }) => {

    
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        username: "",
        newPassword: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const admin = useSelector(state => state.adminState.admin);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await customFetch(`/admin/editOwner/${OwnerId}`, {
                method: 'PATCH',
                headers: { Authorization: `Bearer ${admin.token}` },
                body: JSON.stringify(formData)
            });
            toast.success("Owner information updated successfully");
            console.log(response);
            onClose();
        } catch (error) {
            toast.error(`${error.response.data}`);
            console.error('Error:', error);
        }
    };

    return (

        <div className="modal-box flex flex-col items-center justify-center z-50">
            <form onSubmit={handleSubmit} className="grid place-content-center">
                <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={onClose}>✕</button>
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
                                value="{formData.firstName}"
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
                                value="{formData.lastName}"
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
                                value="{formData.email}"
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
                                value="{formData.username}"
                                onChange={handleChange}
                                autoComplete="given-name" 
                            />
                        </div>
                    </div>
                    <div className="form-control">
                        <label className="label">
                            <span className="label-text capitalize font-semibold">New Password</span>
                        </label>
                        <input
                            type="password"
                            name="newPassword"
                            placeholder="Enter New Password"
                            className="input input-bordered input-accent"
                            value="{formData.newPassword}"
                            onChange={handleChange}
                            autoComplete="given-name"
                        />
                    </div>
                </div>
                <div className="mt-4">
                    <button type="submit" className="capitalize tracking-wide btn btn-accent btn-block">Save Changes</button>
                </div>
            </form>
        </div>
    );
};

export default EditOwnerForm;
