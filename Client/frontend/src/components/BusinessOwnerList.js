import React, { useState, useEffect,useRef } from "react";
import { Form, useLoaderData, useNavigate } from "react-router-dom";
import default_avatar from '../assets/default_avatar.webp';
import { customFetch } from '../utils';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import { ArrowUpDown,Download, BriefcaseBusinessIcon, Circle, LampWallDown, Lock, LockOpen, PencilIcon, SortAscIcon, SortDescIcon } from "lucide-react";
import {EditOwnerForm} from ".";
import {BusinessCarousel} from ".";
import {LockOwnerDialog} from ".";
import jsPDF from 'jspdf';
import { mkConfig, generateCsv, download } from 'export-to-csv';
import 'jspdf-autotable';
import logo from '../assets/logoDark.png';


const BusinessOwnerList = () => {
    const admin = useSelector(state => state.adminState.admin);

    const history = useNavigate();
    const { businessOwners, params } = useLoaderData();
    const [selectedOwnerId, setSelectedOwnerId] = useState(null); 
    const [deactivatedOwners, setDeactivatedOwners] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedOwners, setSelectedOwners] = useState([]);
    const [DeactivatedState, setDeactivatedState] = useState(null); 
    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [lockDialogOpen, setLockDialogOpen] = useState(false);
    const [selectedOwnerIdForBusiness, setOwnerIdForBusiness] = useState(null); 
    const [selectedOwnerIdForEdit, setOwnerIdForEdit] = useState(null); 


        const handleSelectAll = () => {
            setSelectAll(!selectAll); 
            if (!selectAll) {
                setSelectedOwners(businessOwners.map(owner => owner.id));
            } else {
                setSelectedOwners([]);
            }
};
    const handleSelectOwner = (ownerId) => {
        if (selectedOwners.includes(ownerId)) {
            setSelectedOwners(selectedOwners.filter(id => id !== ownerId));
        } else {
            setSelectedOwners([...selectedOwners, ownerId]);
        }
};

      const handleLockAll = async (selectedOwners) => {
        console.log(selectedOwners);
        try {
            
            console.log(JSON.stringify(selectedOwners));

            const response = await customFetch(`tables/deactivateAccounts/${selectedOwners.join(',')}`,{
                method: 'PATCH',
                headers: {Authorization: `Bearer ${admin.token}`},
            });

            //return response.data;
            console.log(response.data);
            history(`/business-owner`);
            toast.success("Accounts deactivated Successfully");

        } catch (error) {
            toast.error(`${error.message}`);
            console.error('Error during deactivation:', error);
            console.log(error.response.body);

        }
};
    
const initialSortOrder = localStorage.getItem('sortOrder') || 'asc';
const [createdDateSort, setCreatedDateSort] = useState({ ascending: false });

    useEffect(() => {
    if (businessOwners) {
        const deactivatedIds = businessOwners.filter(owner => owner._deactivated).map(owner => owner.id);
        setDeactivatedOwners(deactivatedIds);
    }

    setCreatedDateSort({ ascending: initialSortOrder === 'asc' });

    const sortOrder = localStorage.getItem('sortOrder');
    if (sortOrder) {
        setCreatedDateSort({ ascending: sortOrder === 'asc' });
    } else {
        setCreatedDateSort({ ascending: false });
        localStorage.setItem('sortOrder', 'desc');
    }
    }, [businessOwners, initialSortOrder]);

    const handleLockClick = (id,isDeactivated) => {
        setLockDialogOpen(true);
        setSelectedOwnerId(id);
        setDeactivatedState(isDeactivated);
    };
    const [isGenerating, setIsGenerating] = useState(false);
    const conponentPDF= useRef();

    const getSignedUpText = (createdAt) => {
        const currentDate = new Date();
        const signUpDate = new Date(createdAt);
        const timeDifference = currentDate.getTime() - signUpDate.getTime();
        const secondsInMs = 1000;
        const minutesInMs = secondsInMs * 60;
        const hoursInMs = minutesInMs * 60;
        const daysInMs = hoursInMs * 24;
        const weeksInMs = daysInMs * 7;
        const monthsInMs = daysInMs * 30;
        const yearsInMs = daysInMs * 365;
    
        if (timeDifference >= yearsInMs) {
            const years = Math.floor(timeDifference / yearsInMs);
            return `${years} year${years > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= monthsInMs) {
            const months = Math.floor(timeDifference / monthsInMs);
            return `${months} month${months > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= weeksInMs) {
            const weeks = Math.floor(timeDifference / weeksInMs);
            return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= daysInMs) {
            const days = Math.floor(timeDifference / daysInMs);
            return `${days} day${days > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= hoursInMs) {
            const hours = Math.floor(timeDifference / hoursInMs);
            return `${hours} hour${hours > 1 ? 's' : ''} ago`;
        } else if (timeDifference >= minutesInMs) {
            const minutes = Math.floor(timeDifference / minutesInMs);
            return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
        } else {
            const seconds = Math.floor(timeDifference / secondsInMs);
            return `${seconds} second${seconds > 1 ? 's' : ''} ago`;
        }
    };

    const handleEditOwnerClick = async (id) => {
        setOwnerIdForEdit(id)
        console.log(selectedOwnerId)
        setEditDialogOpen(true);
    };

    const toggleCreatedDateSort = async () => {
        setCreatedDateSort(prevSort => { 
            const newSort = { ascending: !prevSort.ascending };

            localStorage.setItem('sortOrder', newSort.ascending ? 'asc' : 'desc');


            const sortOrderParam = newSort.ascending ? 'asc' : 'desc';
            const queryParams = new URLSearchParams({ sortOrder: sortOrderParam });
            history(`/business-owner?${queryParams.toString()}`);

            return newSort;
        });
        const response = await customFetch("/tables/owners", {
            params: {
                searchKey: params.searchKey,
                sortOrder: createdDateSort.ascending ? 'asc' : 'desc',
                page: params.page
            },
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
    };
    
    const [isCarouselOpen, setIsCarouselOpen] = useState(false);

    const handleCarousel = async (id) => {
        setSelectedOwnerId(id);
        console.log(selectedOwnerId);
        setIsCarouselOpen(true);
    };

    const formatDateDuration = (createdDate) => {
        const date = new Date(createdDate);
        const year = date.getFullYear();
        const month = date.getMonth();
        const day = date.getDate();
        return `${day}/${month}/${year}`;
    };

    const formatTime = (createdDate) => {
        const date = new Date(createdDate);
    
        let hours = date.getHours();
        let minutes = date.getMinutes();
    
        const amOrPm = hours >= 12 ? 'PM' : 'AM';
    
        hours = hours % 12;
        hours = hours ? hours : 12;
    
        minutes = minutes < 10 ? '0' + minutes : minutes;
    
        return `${hours}:${minutes}`+` `+`${amOrPm}`;
    };
    
    const addHeaderToPDF = (doc, logo, page, isAllPages = false) => {
        const imgWidth = 30;
        const imgHeight = 30; 
        const pageWidth = doc.internal.pageSize.getWidth();
        const centerX = (pageWidth - imgWidth) / 2;
    
        doc.addImage(logo, 'PNG', centerX, 10, imgWidth, imgHeight);
    
        let pageTitle;
        if (isAllPages) {
            pageTitle = `Business Owners Report - All Pages`;
        } else {
            pageTitle = `Business Owners Report - Page ${page}`;
        }
        doc.setFontSize(22);
        doc.text(pageTitle, pageWidth / 2, 40, { align: 'center' }); 
    
        const today = new Date();
        const dateStr = today.toLocaleDateString(); 
        doc.setFontSize(12);
        doc.text(`Date: ${dateStr}`, pageWidth / 2, 50, { align: 'center' });
    };

    const handleExportRowsPDF = (businessOwners) => {
        const page = params.page || 1;
        const doc = new jsPDF();
        
        const img = new Image();
        img.src = logo;
        img.onload = () => {
            addHeaderToPDF(doc, img, page);


        const tableData = businessOwners.map((owner) => {
            const { firstName, lastName,createdAt, email, avatarUrl, username, id, _deactivated, active,fullName,businesses,lastLogin,lastLogout } = owner;
            const formattedDate = formatDate(createdAt);
            return [firstName, lastName,username, email, formattedDate, avatarUrl, , id, _deactivated, active,fullName,businesses,lastLogin,lastLogout];
        });
    
        const tableHeaders = [
            'First Name',
            'Last Name',
            'Username',
            'Email',
            'Created At',
        ];
            
        doc.autoTable({
            head: [tableHeaders],
            body: tableData,
            startY: 60 

        });
    
        doc.save('businesses owner.pdf');
    };
    };
      const csvConfig = {
        fieldSeparator: ',',
        decimalSeparator: '.',
        columnHeaders: [
            'First Name  ',
            'Last Name  ',
            'Username  ',
            'Email  ','','',
            'Created at','',
            'Last Login  ','',
            'Last Logout  ',

            ],
        showColumnHeaders:true,
        useKeysAsHeaders: true,
        filename:'businesses owners',
      };
      
    const handleExportRowsCSV = (businessOwners) => {

        if (businessOwners.length === 0) {
            toast.error('No data to export');
            return;
        }

        const today = new Date();
        const formattedToday = `${today.getDate()}-${today.getMonth() + 1}-${today.getFullYear()}`;
        const title = 'Business Owners List';

        const firstRow = [
            formattedToday, "", 
            title, "",
            ...Array(csvConfig.columnHeaders.length *2 - 4).fill('')
        ];    
        const blankRow = Array(csvConfig.columnHeaders.length).fill('');
        const tableDataWithSpacing = [firstRow,blankRow];
        
        const tableData = businessOwners.map((owner) => {
            const { firstName, lastName,createdAt, email, username, id, _deactivated, active,businesses,lastLogin,lastLogout } = owner;
            return [firstName, lastName,username,email,"","",createdAt,"",lastLogin,"",lastLogout];
        });
    

        
        const csv = generateCsv({ ...csvConfig, showColumnHeaders: false })([...tableDataWithSpacing, csvConfig.columnHeaders, ...tableData]); // Include the first row, column headers, and business data

        download({ ...csvConfig, showColumnHeaders: false })(csv);

    };
    
    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const day = date.getDate();
        const month = date.getMonth();
        const year = date.getFullYear();
        return `${month}/${day}/${year}`;
    };

    const handleAllExportRows = async () => {
        
        const response = await customFetch("/tables/allPagesUsers", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });
    
        console.log(response.data);

        const businessOwners = response.data;

        const tableData = businessOwners.map(businessOwner => [
            businessOwner.firstName,
            businessOwner.lastName,
            businessOwner.username,
            businessOwner.email,
            formatDate(businessOwner.createdAt),
        ]);
                const doc = new jsPDF();
    
                const img = new Image();
                img.src = logo;
                img.onload = () => {
                    addHeaderToPDF(doc, img, null, true);

                    
                    const tableHeaders = [
                        'First Name',
                        'Last Name',
                        'Username',
                        'Email',
                        'Created At',
                    ];    
                doc.autoTable({
                    head: [tableHeaders],
                    body: tableData,
                    startY: 60 
                });
            
                doc.save('List businesses.pdf');
    };
    };
    const handleExportAllRowsCSV = async () => {

        const response = await customFetch("/tables/allPagesUsers", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });

        console.log(response.data);

        const businessOwners = response.data;

        if (businessOwners.length === 0) {
            toast.error('No data to export');
            return;
        }

        const today = new Date();
        const formattedToday = `${today.getDate()}-${today.getMonth() + 1}-${today.getFullYear()}`;
        const title = 'Business Owners List';

        const firstRow = [
            formattedToday, "", 
            title, "",
            ...Array(csvConfig.columnHeaders.length *2 - 4).fill('')
        ];    
        const blankRow = Array(csvConfig.columnHeaders.length).fill('');
        const tableDataWithSpacing = [firstRow,blankRow];
        
        const tableData = businessOwners.map((owner) => {
            const { firstName, lastName,createdAt, email, username, id, _deactivated, active,businesses,lastLogin,lastLogout } = owner;
            return [firstName, lastName,username,email,"","",createdAt,"",lastLogin,"",lastLogout];
        });
    

        
        const csv = generateCsv({ ...csvConfig, showColumnHeaders: false })([...tableDataWithSpacing, csvConfig.columnHeaders, ...tableData]); // Include the first row, column headers, and business data

        download({ ...csvConfig, showColumnHeaders: false })(csv);

    };

    if (!businessOwners || businessOwners.length < 1) {
        return (
            <div className="font-bold mx-auto text-4xl text-center text-error">
                There is no match for the keyword You Typed !!!
            </div>
        );
    }

    return (

        <div>
        <div className="flex justify-center space-x-12 mb-4">
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportRowsPDF(businessOwners)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page PDF'}
    </button>
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportRowsCSV(businessOwners)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page CSV'}
    </button>     
            <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleAllExportRows(businessOwners)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export All PDF'}
    </button>  
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportAllRowsCSV(businessOwners)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export All CSV'}
    </button>     
</div>

        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
                {/* head */}
                <thead>
                    <tr>
                        <th key="checkbox">
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectAll} 
                                         onChange={handleSelectAll}
                                         />
                                    </label>
                        </th>
                    <th key="profile" className="text-center">Profile</th>
                    <th key="fullName" className="text-center">Full Name</th>
                    {/* <th key="fullName" className="text-center">Username</th> */}
                    <th key="fullName" >Last Login</th>
                    {/* <th key="fullName">Last Logout</th> */}

                    {/* <th key="status" className="text-center">Status</th> */}
                    <th key="signedUp" style={{ cursor: 'pointer' }} onClick={toggleCreatedDateSort}>
                            <div className="flex items-center">
                                Signed Up  &nbsp;
                                <ArrowUpDown className={`w-4 h-4 ml-1 text-gray-800 dark:text-black`} />
                            </div>
                    </th>
                    <th key="Business" className="text-center">Businesses</th>

 
                    <th key="edit" className="text-center"></th>
                    <th key="lockButton">
    {selectedOwners.length > 0 ? (
        <button
            onClick={() => handleLockAll(selectedOwners)}
            className="btn btn-error btn-sm flex justify-end" 
        >
            <Lock className='w-4 h-4' />
            {/* <span className="ml-1">Lock &nbsp;&nbsp;&nbsp;</span> */}
        </button>
    ) : (
        <button
            disabled
            className="btn btn-error btn-sm flex justify-end cursor-not-allowed"
            aria-disabled="true"
        >
            <Lock className='w-4 h-4' />
            {/* <span className="ml-1">Lock &nbsp;&nbsp;&nbsp;</span> */}
        </button>
    )}
</th>

                    </tr>
                </thead>
                <tbody>
                    {businessOwners.map((owner) => {
                        const { firstName, lastName,createdAt, email, avatarUrl, username, id, _deactivated, active,fullName,businesses,lastLogin,lastLogout } = owner;
                        const numberOfBusinesses = businesses.length;     

                        return (
                            <tr>
                                <th key="checkbox">
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectedOwners.includes(id)} 
                                         onChange={() => handleSelectOwner(id)} 
                                          />
                                    </label>
                                </th>
                                <td>
                                <div className="flex items-center gap-3 relative">
    <div className="avatar relative">
        <div className="mask mask-squircle w-12 h-12">
            <img alt="profile image" src={avatarUrl ? `${avatarUrl}` : default_avatar} />
        </div>
        {active ? (
            <div className="absolute -top-0 -right-0 flex items-center justify-center w-2 h-2 rounded-full bg-green-400 text-white">
            </div>
        ): (
            <div className="absolute -top-0 -right-0 flex items-center justify-center w-2 h-2 rounded-full bg-red-500 text-white">
            </div>
        )}
    </div>
</div>

                                </td>
                                <td>
                                <div className="font-bold">{firstName} {lastName}</div>
                                <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{email}</div>
                                </td>
                                {/* <td>
                                <div className="font-bold">{username}</div>
                                </td> */}
                            {/* <td>{formatDateDuration(business.createdDate)}
                            <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{formatTime(business.createdDate)}</div>
                            </td> */}

                                <td>
                                <div >{getSignedUpText(lastLogin)}
                                <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{formatDateDuration(lastLogin)}</div>
                                </div>
                                </td>
                                {/* <td>
                                <div>{getSignedUpText(lastLogout)}</div>
                                </td> */}

                    <td>
                    <div>{getSignedUpText(createdAt)}</div>
                    <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{formatDateDuration(createdAt)}</div>
                    </td>
                    <td className="text-center">
                {/* Afficher les entreprises associées */}
                
                {businesses.map((business) => (
                    <div key={business.id}>
                        {/* <briefcase-business/> */}
                        {/* <div className="font-bold">{business.businessName}</div> */}
                    </div>
                ))}

                <button className='btn btn-success btn-sm '
                        onClick={() => setOwnerIdForBusiness(id)}

                                    >
                                    <BriefcaseBusinessIcon className='w-4 h-4' /> {numberOfBusinesses}
                                    </button>
            </td>

                    <td>
                                    <button className='btn btn-success btn-sm'
                                        onClick={() => {
                                            handleEditOwnerClick(id)
                                            // setOwnerIdForEdit(id)
                                                                                }}
                                    >
                                    <PencilIcon className='w-4 h-4' />
                                    {/* <span className="ml-1">Edit Owner</span> */}
                                    </button>
                    </td>


                                <td>
                                    <button 
                                        className={`btn btn-${_deactivated ? 'success' : 'error'} btn-sm flex items-center`}
                                        onClick={() => {
                                            handleLockClick(id, _deactivated); 
                                        }}
                                    >
                                            {_deactivated ? 
        <>
            <LockOpen className='w-4 h-4' />
            {/* <span>Unlock</span> */}
        </> 
        : 
        <>
            <Lock className='w-4 h-4'/>
            {/* <span >Lock</span> */}
        </>
    }
                                    </button>
    </td>
                        </tr>
                        );
                    })}
                </tbody>
            </table>


             {/* Lock Dialog */}
             {businessOwners.map((owner) => {
                const {id} = owner;
                return (

                    <dialog
                    id={`lock-dialog-${id}`}
                    className="modal modal-bottom sm:modal-middle"
                    open={lockDialogOpen}
                    onClose={() => setLockDialogOpen(false)}
                            >
                        <div className="fixed inset-0 z-50 bg-black opacity-10"></div> 
                        <LockOwnerDialog
                            ownerId={selectedOwnerId}
                            isDeactivated = {DeactivatedState}
                            onClose={() => setLockDialogOpen(false)}
                        />
                    </dialog>
                );
            })}

             {/* Edit Dialog */}
             {businessOwners.map((owner) => {
                const {id} = owner;
                return (
                    <dialog
                        key={id}
                        id={`edit-dialog-${id}`}
                        className="modal modal-bottom sm:modal-middle"
                        open={selectedOwnerIdForEdit === id}
                        onClose={() => setOwnerIdForEdit(null)}
                    >
                        <div className="fixed inset-0 z-50 bg-black opacity-50"></div> 
                        <EditOwnerForm
                            ownerId={selectedOwnerIdForEdit}
                            onClose={() => setOwnerIdForEdit(null)}
                        />
                    </dialog>
                );
            })}

            {businessOwners.map((owner) => {
                const {id} = owner;
            return (
                <Form  
                    key={id}
                    id={`business-caroussel-${id}`}    
                    className="modal xs:modal-middle "
                    open={selectedOwnerIdForBusiness === id}
                    onClose={() => setOwnerIdForBusiness(null)}
                    >

                            <BusinessCarousel
                            ownerId = {selectedOwnerIdForBusiness}
                            onClose={() => setOwnerIdForBusiness(null)}
                                                        />
                </Form>
            );
})}
        </div>
        </div>

    );

};

export default BusinessOwnerList;