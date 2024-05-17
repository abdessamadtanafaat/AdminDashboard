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

     const [isGenerating, setIsGenerating] = useState(false);
     const conponentPDF= useRef();

   
    const handleExportRowsPDF = (businesses) => {
        const doc = new jsPDF();
        
        const tableData = businesses.map((business) => {
            const {instagramLink,googleLink,facebookLink,coverImageUrl, businessName, email, phone,createdDate,id,address,type } = business;
            return [businessName, address, email,phone, createdDate, type.typeName,instagramLink,googleLink,facebookLink,coverImageUrl];
        });
    
        const tableHeaders = ['Business Name', 'Address', 'Email', 'Phone', 'Created Date', 'Type'];
    
        doc.autoTable({
            head: [tableHeaders],
            body: tableData,
        });
    
        doc.save('businesses.pdf');
    };

      const csvConfig = {
        fieldSeparator: ',',
        decimalSeparator: '.',
        columnHeaders: ['ID', 'Business Name', 'Address', 'Email', 'Phone', 'Created Date', 'Type', 'Instagram Link', 'Google Link', 'Facebook Link', 'Cover Image URL'],
        showColumnHeaders:true,
        useKeysAsHeaders: true,
        filename:'List businesses',
      };
      
    const handleExportRowsCSV = (businesses) => {

        if (businesses.length === 0) {
            toast.error('No data to export');
            return;
        }
        const tableData = businesses.map((business) => {
            const {instagramLink,googleLink,facebookLink,coverImageUrl, businessName, email, phone,createdDate,id,address,type } = business;
            const formattedPhone = phone.toString();

            return [id,businessName, address, email,formattedPhone, createdDate, type.typeName,instagramLink,googleLink,facebookLink,coverImageUrl];
        });

  const csv = generateCsv(csvConfig)([csvConfig.columnHeaders, ...tableData]);
        download(csvConfig)(csv);

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
            business.createdDate,
            business.type.typeName
        ]);
                const doc = new jsPDF();
    
                const tableHeaders = ['Business Name', 'Address', 'Email', 'Phone', 'Created Date', 'Type'];
    
                doc.autoTable({
                    head: [tableHeaders],
                    body: tableData,
                });
            
                doc.save('List businesses.pdf');
    };
    
    const handleAllExportRowsCSV = async () => {

        const response = await customFetch("/tables/allPagesBusiness", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });
    
        console.log(response.data);
        
        const businesses = response.data;

        const tableData = businesses.map(business => [
            business.id,
            business.businessName,
            business.address,
            business.email,
            business.phone,
            business.createdDate,
            business.type.typeName,
            business.instagramLink,
            business.googleLink,
            business.facebookLink,
            business.coverImageUrl
        ]);
  const csv = generateCsv(csvConfig)([csvConfig.columnHeaders, ...tableData]);
        download(csvConfig)(csv);

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