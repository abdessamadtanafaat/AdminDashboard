import React, { useState, useEffect,useRef } from "react";
import { useLoaderData, Link, useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import { useSelector } from 'react-redux';
import { customFetch } from '../utils';
import { ArrowUpDown, BriefcaseBusinessIcon,LampWallDown, PencilIcon, SortAscIcon, SortDescIcon, User,File, Download } from "lucide-react";
import {InfoOwnerBusiness} from ".";
import jsPDF from 'jspdf';
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
    // const generatePDF= useReactToPrint({
    //     content: ()=>conponentPDF.current,
    //     documentTitle:"Business data",
    //     onBeforeGetContent: () => setIsGenerating(true),
    //     onAfterPrint:()=>{
    //         setIsGenerating(false);
    //         //toast.success("Business data saved successfully");
    //     }

    // });    
    const handleExportRows = (businesses) => {
        const doc = new jsPDF();
        
        const tableData = businesses.map((business) => {
            const { businessName, address, phone, createdDate, type } = business;
            return [businessName, address, phone, formatDateDuration(createdDate), type.typeName];
        });
    
        const tableHeaders = ['Business Name', 'Address', 'Phone', 'Created Date', 'Type'];
    
        doc.autoTable({
            head: [tableHeaders],
            body: tableData,
        });
    
        doc.save('businesses.pdf');
    };

    const handleAllExportRows = (businesses) => {
        // const doc = new jsPDF();
        
        // const tableData = businesses.map((business) => {
        //     const { businessName, address, phone, createdDate, type } = business;
        //     return [businessName, address, phone, formatDateDuration(createdDate), type.typeName];
        // });
    
        // const tableHeaders = ['Business Name', 'Address', 'Phone', 'Created Date', 'Type'];
    
        // doc.autoTable({
        //     head: [tableHeaders],
        //     body: tableData,
        // });
    
        // doc.save('businesses.pdf');
    };
    
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
        <div className="justify-content-md-start" style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem'}}>
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportRows(businesses)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page Rows'}
    </button>   
    {/* <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportRows(businesses)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page Rows : CSV '}
    </button>  */}
            <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleAllExportRows(businesses)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export All Rows'}
    </button>     
</div>

        <div className="overflow-x-auto">
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
                    const { businessName, email, phone, id,address,type } = business;
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
                                <div className="font-bold">{business.address}</div>
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