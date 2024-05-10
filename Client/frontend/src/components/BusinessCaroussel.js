import { ShieldCloseIcon } from 'lucide-react';
import { toast } from 'react-toastify';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux'; 
import React, { useState,useEffect,navigate  } from "react";


function  BusinessCarousel ({ ownerId ,isOpen, onClose }) {

    const [businesses, setBusinesses] = useState([]);
    const [activeIndex, setActiveIndex] = useState(0);

    const admin = useSelector(state => state.adminState.admin);


    const handleClose = () => {
        onClose();
    };
    

    const handleItemClick = (index, e) => {
        setActiveIndex(index);
        console.log(index);
    };
    

    const handleNextClick = () => {
        setActiveIndex((prevIndex) => (prevIndex === businesses.length - 1 ? 0 : prevIndex + 1));
    };

    const handleOutsideClick = (e) => {
        if (!e.target.closest('.modal-box')) {
            handleClose();
        }
    };

    useEffect(() => {
        
        if (ownerId) {

                    customFetch (`/tables/owner/${ownerId}/businesses`, {
                    headers: { Authorization: `Bearer ${admin.token}` }
            })
                    .then(res => {
                        setBusinesses(res.data);
                        console.log(res.data);
                    })
                    .catch(err => console.log(err))
                }

                if (isOpen) {
                    const handleClickOutside = (e) => {
                        if (!e.target.closest('.modal-box')) {
                            onClose();
                        }
                    };

                    document.addEventListener('mousedown', handleOutsideClick);
                    return () => {
                        document.removeEventListener('mousedown', handleOutsideClick);
                    };
                }


    }, [ownerId,isOpen, admin.token,onClose]) 


    return (   
        <div className="modal-box flex flex-col items-center justify-center z-10">
            <button className="close-dialog btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={handleClose}>✕</button>
            <div className="carousel w-full">
                {businesses.map((business, index) => (
                    <div key={index} id={`item${index + 1}`} className={`carousel-item w-full ${index === activeIndex ? 'active' : ''}`}>
                        <div className="flex flex-col items-center"> {/* Added flex and items-center */}
                            <div className="image-container">
                                <img src={business.coverImageUrl} className="w-full" />
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
                                value={business.businessName}
                                disabled 
                                //onChange={handleChange}
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
                                value={business.type.typeName}
                                disabled 
                                //onChange={handleChange}
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
                                value={business.email}
                                disabled 
                                //onChange={handleChange}
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
                                value={business.phone}
                                disabled
                                //onChange={handleChange}
                                autoComplete="given-name" 
                            />
                        </div>
                    </div>

                </div>


                            {/* <div className="business-info mt-4">
                                <div className="field">
                                    <label><strong>Business Name:</strong></label>
                                    <span>{business.businessName}</span>
                                </div>
                                <div className="field">
                                    <label><strong>Email:</strong></label>
                                    <span>{business.email}</span>
                                </div>
                                <div className="field">
                                    <label><strong>Phone:</strong></label>
                                    <span>{business.phone}</span>
                                </div>
                                <div className="field">
                                    <label><strong>Address:</strong></label>
                                    <span>{business.address}</span>
                                </div>
                                <div className="field">
                                    <label><strong>Type:</strong></label>
                                    <span>{business.type.typeName}</span>
                                </div>
                            </div> */}
                        </div>
                    </div>
                ))}
            </div>
            
            <div className="flex justify-center w-full py-2 gap-2">
                {businesses.map((_, index) => (
                    <a key={index} href={`#item${index + 1}`} onClick={() => {handleItemClick(index); }} className="btn btn-xs">{index + 1}</a>
                ))}
            </div>
        </div>
    );
    
    
    
};

export default BusinessCarousel;
