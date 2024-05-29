import React, { useState, useEffect,useRef } from "react";
import { useLoaderData, Link, useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import { useSelector } from 'react-redux';
import { customFetch } from '../utils';
import { ArrowUpDown, BriefcaseBusinessIcon,LampWallDown, PencilIcon, SortAscIcon, SortDescIcon, User,File, Download } from "lucide-react";
import {InfoOwnerBusiness} from ".";
import jsPDF from 'jspdf';
import { mkConfig, generateCsv, download } from 'export-to-csv';
import 'jspdf-autotable';
import logo from '../assets/logoDark.png'; // Import your image


const BusinessList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const { businesses, params } = useLoaderData();
    const history = useNavigate();

    const initialSortOrder = localStorage.getItem('sortOrder') || 'asc';
    const [createdDateSort, setCreatedDateSort] = useState({ ascending: false });

    const [selectAll, setSelectAll] = useState(false);
    const [selectedBusinessOwners, setSelectedBusinessOwners] = useState([]);

    const [infoOwnerDialogOpen, setInfoOwnerDialogOpen] = useState(false);
    const [selectedbusinessId, setSelectedBusinessId] = useState(false);


    useEffect(() => {
        setCreatedDateSort({ ascending: initialSortOrder === 'asc' });

        const sortOrder = localStorage.getItem('sortOrder');
        if (sortOrder) {
            setCreatedDateSort({ ascending: sortOrder === 'asc' });
        } else {
            setCreatedDateSort({ ascending: false });
            localStorage.setItem('sortOrder', 'desc');
        }

    }, [initialSortOrder]);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        return `${month}/${day}/${year}`;
    };

     const [isGenerating, setIsGenerating] = useState(false);
     const conponentPDF= useRef();

     const addHeaderToPDF = (doc, logo, page, isAllPages = false) => {
        const imgWidth = 30;
        const imgHeight = 30; 
        const pageWidth = doc.internal.pageSize.getWidth();
        const centerX = (pageWidth - imgWidth) / 2;
    
        doc.addImage(logo, 'PNG', centerX, 10, imgWidth, imgHeight);
    
        let pageTitle;
        if (isAllPages) {
            pageTitle = `Business Report - All Pages`;
        } else {
            pageTitle = `Business Report - Page ${page}`;
        }
        doc.setFontSize(22);
        doc.text(pageTitle, pageWidth / 2, 40, { align: 'center' }); 
    
        const today = new Date();
        const dateStr = today.toLocaleDateString(); 
        doc.setFontSize(12);
        doc.text(`Date: ${dateStr}`, pageWidth / 2, 50, { align: 'center' });
    };
   
    const handleExportRowsPDF = (businesses) => {
        const page = params.page || 1;
        const doc = new jsPDF();
        
        const img = new Image();
        img.src = logo;
        img.onload = () => {
            addHeaderToPDF(doc, img, page);


        const tableData = businesses.map((business) => {
            const {instagramLink,googleLink,facebookLink,coverImageUrl, businessName, email, phone,createdDate,id,address,type } = business;
            const formattedDate = formatDate(createdDate);
            return [businessName, address, email,phone, formattedDate, type.typeName,instagramLink,googleLink,facebookLink,coverImageUrl];
        });
    
        const tableHeaders = ['Business Name', 'Address', 'Email', 'Phone', 'Created Date', 'Type'];
    
        doc.autoTable({
            head: [tableHeaders],
            body: tableData,
            startY: 60 
        });
    
        doc.save('businesses.pdf');
    };
    };

      const csvConfig = {
        fieldSeparator: ',',
        decimalSeparator: '.',
        columnHeaders: ['ID', 'Business Name','', 'Address         ','','', 'Email          ','','','Created Date','', 'Type'],
        showColumnHeaders:true,
        useKeysAsHeaders: true,
        filename:'List businesses',
      };

      
    const handleExportRowsCSV = (businesses) => {

        if (businesses.length === 0) {
            toast.error('No data to export');
            return;
        }
        const today = new Date();
        const formattedToday = `${today.getDate()}-${today.getMonth() + 1}-${today.getFullYear()}`;
        const title = 'Business List';

        const firstRow = [
            formattedToday, "", 
            title, "",
            ...Array(csvConfig.columnHeaders.length *2 - 4).fill('')
        ];    
        const blankRow = Array(csvConfig.columnHeaders.length).fill('');
        const tableDataWithSpacing = [firstRow,blankRow];

        const tableData = businesses.map((business) => {
            const {businessName, email, phone,createdDate,id,address,type } = business;

            return [id, businessName, "", address, "","", email, "","", createdDate, "", type.typeName, ""];
        });

        
        const csv = generateCsv({ ...csvConfig, showColumnHeaders: false })([...tableDataWithSpacing, csvConfig.columnHeaders, ...tableData]); // Include the first row, column headers, and business data

        download({ ...csvConfig, showColumnHeaders: false })(csv);

    };
    
    
    const handleAllExportRowsPDF = async () => {
        
        const response = await customFetch("/tables/allPagesBusiness", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });
    
        console.log(response.data);
        
        const businesses = response.data;

        const tableData = businesses.map(business => [
            business.businessName,
            business.address,
            business.email,
            business.phone,
            formatDate(business.createdDate),
            business.type.typeName
        ]);
                const doc = new jsPDF();
    
                const img = new Image();
                img.src = logo;
                img.onload = () => {
                    addHeaderToPDF(doc, img, null, true);

                    
                const tableHeaders = ['Business Name', 'Address', 'Email', 'Phone', 'Created Date', 'Type'];
    
                doc.autoTable({
                    head: [tableHeaders],
                    body: tableData,
                    startY: 60 
                });
            
                doc.save('List businesses.pdf');
    };
};
    
    const handleAllExportRowsCSV = async () => {

        const response = await customFetch("/tables/allPagesBusiness", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });
    
        console.log(response.data);
        
        const businesses = response.data;

        const today = new Date();
        const formattedToday = `${today.getDate()}-${today.getMonth() + 1}-${today.getFullYear()}`;
        const title = 'Business List';

        const firstRow = [
            formattedToday, "", 
            title, "",
            ...Array(csvConfig.columnHeaders.length *2 - 4).fill('')
        ];    
        const blankRow = Array(csvConfig.columnHeaders.length).fill('');
        const tableDataWithSpacing = [firstRow,blankRow];


        const tableData = businesses.map(business => {
            const {id,
            businessName,
            address,
            email,
            phone,
            createdDate,
            type} = business;
            return [id, businessName, "", address, "","", email, "","",createdDate, "", type.typeName, ""];
        });

    
        const csv = generateCsv({ ...csvConfig, showColumnHeaders: false })([...tableDataWithSpacing, csvConfig.columnHeaders, ...tableData]); // Include the first row, column headers, and business data

        download({ ...csvConfig, showColumnHeaders: false })(csv);

    }

    const toggleCreatedDateSort = async () => {
        setCreatedDateSort(prevSort => { 
            const newSort = { ascending: !prevSort.ascending };

            localStorage.setItem('sortOrder', newSort.ascending ? 'asc' : 'desc');


            const sortOrderParam = newSort.ascending ? 'asc' : 'desc';
            const queryParams = new URLSearchParams({ sortOrder: sortOrderParam });
            history(`/business?${queryParams.toString()}`);

            return newSort;
        });

        const response = await customFetch("/tables/business", {
            params: {
                searchKey: params.searchKey,
                sortOrder: createdDateSort.ascending ? 'asc' : 'desc',
                page: params.page
            },
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
    };

    const formatDateDuration = (createdDate) => {
        const date = new Date(createdDate);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        return `${month}/${day}/${year}`;
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
    
    const handleSelectAll = () => {
        setSelectAll(!selectAll); 
        if (!selectAll) {
            setSelectedBusinessOwners(businesses.map(owner => owner.id));
        } else {
            setSelectedBusinessOwners([]);
        }
    };

    const handleSelectOwner = (businessOwnerId) => {
        if (selectedBusinessOwners.includes(businessOwnerId)) {
            setSelectedBusinessOwners(selectedBusinessOwners.filter(id => id !== businessOwnerId));
        } else {
            setSelectedBusinessOwners([...selectedBusinessOwners, businessOwnerId]);
        }
    };

    const handleOwnerInfoClick = async (id) => {
        setSelectedBusinessId(id)
        console.log(selectedbusinessId)
        setInfoOwnerDialogOpen(true);
    };


    if (!businesses || businesses.length < 1) {
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
         onClick={() => handleExportRowsPDF(businesses)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page PDF'}
    </button>
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportRowsCSV(businesses)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page CSV'}
    </button>     
            <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleAllExportRowsPDF()}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export All PDF'}
    </button>  
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleAllExportRowsCSV(businesses)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export All CSV'}
    </button>     
</div>

        <div className="overflow-x-auto mb-4">
            <div ref={conponentPDF} style={{width:'100%'}}>
            <table className="table table-zebra-zebra">
                {/* table header */}
                <thead>
                    <tr>
                    <th>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectAll} 
                                         onChange={handleSelectAll}
                                         />
                                    </label>
                        </th>
                        <th>Business Name</th>
                        <th>Address</th>
                        <th>Phone</th>
                        <th style={{ cursor: 'pointer' }} onClick={toggleCreatedDateSort}>
                            <div className="flex items-center">
                                Created Date
                                <ArrowUpDown className={`w-4 h-4 ml-1 text-gray-800 dark:text-black`} />

                            </div>
                        </th>
                        <th className="text-center">Business Owner</th>

                    </tr>
                </thead>
                <tbody>
                    {businesses.map((business) => {
                    const {instagramLink,googleLink,facebookLink,coverImageUrl, businessName, email, phone, id,address,type } = business;
                    return (
                        <tr key={business.id}>
                                <th>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox"
                                         className="checkbox"
                                         checked={selectedBusinessOwners.includes(id)} 
                                         onChange={() => handleSelectOwner(id)} 
                                          />
                                    </label>
                                </th>
                            <td>
                                <div className="font-bold">{businessName}</div>
                                <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{business.type.typeName}</div>
                            </td>
                            <td>
                                <div>{business.address}</div>
                            </td>
                            <td>
                                <div>{business.phone}</div>        
                            </td>
                            <td>{formatDateDuration(business.createdDate)}
                            <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{formatTime(business.createdDate)}</div>
                            </td>
                            <td style={{ textAlign: 'center' }}>
                                    <button className='btn btn-success btn-sm'
                                        onClick={() => {
                                            handleOwnerInfoClick(id)
                                                                                }}
                                    >
                                    <User className='w-4 h-4' />
                                    {/* <span className="ml-1">Edit Owner</span> */}
                                    </button>
                            </td>

                            {/* <button class="btn btn-active btn-sucess && btn-sm" onClick={() => {window.location.href=`/business/${business.id}`}}>details</button> */}
                        </tr>
                    );
                    })}
                </tbody>
            </table>
            </div>


                         {/* Info de Owner */}
                         {businesses.map((business) => {
                const {id} = business;
                return (
                    <dialog
                        key={id}
                        id={`owner-dialog-${id}`}
                        className="modal modal-bottom sm:modal-middle"
                        open={infoOwnerDialogOpen && selectedbusinessId === id}
                        onClose={() => setInfoOwnerDialogOpen(false)}
                    >
                        <div className="fixed inset-0 z-50 bg-black opacity-50"></div> 
                        <InfoOwnerBusiness
                            businessId={selectedbusinessId}
                            onClose={() => setInfoOwnerDialogOpen(false)}
                        />
                    </dialog>
                );
            })}

        </div>
        </div>
    );
};

export default BusinessList;