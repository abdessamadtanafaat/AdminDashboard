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
                <div  className="carousel w-full">
                {businesses.map((business, index) => (
                    <div key={index} id={`item${index + 1}`} className={`carousel-item w-full ${index === activeIndex ? 'active' : ''}`}>
                        <img src={business.coverImageUrl} class="w-full" />
            <div className="business-info">
              <p><strong>Business Name:</strong> {business.businessName}</p>
              <p><strong>Email:</strong> {business.email}</p>
              <p><strong>Phone:</strong> {business.phone}</p>
              <p><strong>Address:</strong> {business.address}</p>
              <p><strong>Type:</strong> {business.type.typeName}</p>
            </div>
                    </div>
                ))}
            </div>
            
            <div className="flex justify-center w-full py-2 gap-2">
            {businesses.map((_, index) => (
            <a key={index} href={`#item${index + 1}`} onClick={() => {handleItemClick(index); }} class="btn btn-xs">{index + 1}</a>
))}
            </div>
            </div>
    );
};

export default BusinessCarousel;
