import React, { useState, useEffect,useRef } from "react";
import { useLoaderData, Link, useNavigate } from "react-router-dom";
import { useSelector } from 'react-redux';
import { customFetch } from '../utils';
import { ArrowUpDown,Languages,Download, Check, ClockIcon, PlayIcon, CheckCheckIcon } from "lucide-react";
import {InfoOwnerBusiness} from ".";
import jsPDF from 'jspdf';
import { mkConfig, generateCsv, download } from 'export-to-csv';
import 'jspdf-autotable';
import { toast } from 'react-toastify';
import logo from '../assets/logoDark.png';


const CampaignList = () => {
    const admin = useSelector(state => state.adminState.admin);
    const { campaigns, params } = useLoaderData();
    const [campaignsData, setCampaignsData] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedCampaigns, setSelectedCampaigns] = useState([]);

    const history = useNavigate();
    const initialSortOrder = localStorage.getItem('sortOrder') || 'asc';
    const [createdDateSort, setCreatedDateSort] = useState({ ascending: false });


    const [isGenerating, setIsGenerating] = useState(false);
    const conponentPDF= useRef();


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

 

    // const fetchCampaigns = async () => {
    //     try {
    //         const response = await customFetch("/tables/campagnes", {
    //             params: {
    //                 searchKey: params.searchKey,
    //                 page: params.page
    //             },

    //             headers: { Authorization: `Bearer ${admin.token}` }
    //         });
    //         setCampaignsData(response.data);
    //         console.log(response.data);
    //     } catch (error) {
    //         console.error('Error fetching campaigns:', error);
    //     }
    // };

    
    const handleSelectAll = () => {
        setSelectAll(!selectAll); 
        if (!selectAll) {
            setSelectedCampaigns(campaigns.map(campaign => campaign.id));
        } else {
            setSelectedCampaigns([]);
        }
    };

    const handleLanguageClick = ()=> {

    }
    const handleSelectCampaign = (campaignId) => {
        if (selectedCampaigns.includes(campaignId)) {
            setSelectedCampaigns(selectedCampaigns.filter(id => id !== campaignId));
        } else {
            setSelectedCampaigns([...selectedCampaigns, campaignId]);
        }
    };

    const formatDateDuration = (createdDate) => {
        const currentDate = new Date();
        const startDate = new Date(createdDate);
        const millisecondsPerSecond = 1000;
        const millisecondsPerMinute = millisecondsPerSecond * 60;
        const millisecondsPerHour = millisecondsPerMinute * 60;
        const millisecondsPerDay = millisecondsPerHour * 24;
        const millisecondsPerWeek = millisecondsPerDay * 7;
        const millisecondsPerMonth = millisecondsPerDay * 30;
        const millisecondsPerYear = millisecondsPerDay * 365;
    
        const elapsedMilliseconds = currentDate - startDate;
    
        if (elapsedMilliseconds < millisecondsPerMinute) {
            return 'Just now';
        } else if (elapsedMilliseconds < millisecondsPerHour) {
            const minutes = Math.floor(elapsedMilliseconds / millisecondsPerMinute);
            return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerDay) {
            const hours = Math.floor(elapsedMilliseconds / millisecondsPerHour);
            return `${hours} hour${hours > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerWeek) {
            const days = Math.floor(elapsedMilliseconds / millisecondsPerDay);
            return `${days} day${days > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerMonth) {
            const weeks = Math.floor(elapsedMilliseconds / millisecondsPerWeek);
            return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
        } else if (elapsedMilliseconds < millisecondsPerYear) {
            const months = Math.floor(elapsedMilliseconds / millisecondsPerMonth);
            return `${months} month${months > 1 ? 's' : ''} ago`;
        } else {
            const years = Math.floor(elapsedMilliseconds / millisecondsPerYear);
            return `${years} year${years > 1 ? 's' : ''} ago`;
        }
    };
    const toggleCreatedDateSort = async () => {
        setCreatedDateSort(prevSort => { 
            const newSort = { ascending: !prevSort.ascending };

            localStorage.setItem('sortOrder', newSort.ascending ? 'asc' : 'desc');


            const sortOrderParam = newSort.ascending ? 'asc' : 'desc';
            const queryParams = new URLSearchParams({ sortOrder: sortOrderParam });
            history(`/campaign?${queryParams.toString()}`);

            return newSort;
        });

        const response = await customFetch("/tables/campagnes", {
            params: {
                searchKey: params.searchKey,
                sortOrder: createdDateSort.ascending ? 'asc' : 'desc',
                page: params.page
            },
            headers: { Authorization: `Bearer ${admin.token}` }
        });
        console.log(response.data);
    };

    const csvConfig = {
        fieldSeparator: ',',
        decimalSeparator: '.',
        columnHeaders: ['ID', 'Campaign Name','','Start Date    ','','End Date    ','','Status', 'Business Name','', 'Email','','', 'Address','','', 'Template Name',''],
        showColumnHeaders: true,
        useKeysAsHeaders: true,
        filename: 'List_campaigns.csv',
    };
       
    const addHeaderToPDF = (doc, logo, page, isAllPages = false) => {
        const imgWidth = 30;
        const imgHeight = 30; 
        const pageWidth = doc.internal.pageSize.getWidth();
        const centerX = (pageWidth - imgWidth) / 2;
    
        doc.addImage(logo, 'PNG', centerX, 10, imgWidth, imgHeight);
    
        let pageTitle;
        if (isAllPages) {
            pageTitle = `Campaign Report - All Pages`;
        } else {
            pageTitle = `Campaign Report - Page ${page}`;
        }
        doc.setFontSize(22);
        doc.text(pageTitle, pageWidth / 2, 40, { align: 'center' }); 
    
        const today = new Date();
        const dateStr = today.toLocaleDateString(); 
        doc.setFontSize(12);
        doc.text(`Date: ${dateStr}`, pageWidth / 2, 50, { align: 'center' });
    };
    

    
    const handleExportRowsPDF = (campaigns) => {        
        const page = params.page || 1;
        const doc = new jsPDF();
        
        const img = new Image();
        img.src = logo;
        img.onload = () => {
            addHeaderToPDF(doc, img, page);

        console.log(campaigns);
    
        const tableData = campaigns.map((campaign) => {
            const {
                campaignName,
                createdDate,
                endDate,
                status,
                business: { businessName },
                template: { templateName }
            } = campaign;
            const CreatedFormattedDate = formatDate(createdDate);
            const EndFormattedDate = formatDate(endDate);
            return [campaignName,CreatedFormattedDate,EndFormattedDate,status, businessName, templateName];
        });
    
        const tableHeaders = ['Campaign Name','Start Date','End Date','Status', 'Business Name', 'Template Name'];
    
        doc.autoTable({
            head: [tableHeaders],
            body: tableData,
            startY: 60 
        });
    
        doc.save('campaignes.pdf');
    };
};
    const handleExportRowsCSV = (campaigns) => {
        console.log(campaigns);
        
        if (campaigns.length === 0) {
            toast.error('No data to export');
            return;
        }
    
        const today = new Date();
        const formattedToday = `${today.getDate()}-${today.getMonth() + 1}-${today.getFullYear()}`;
        const title = 'Campaign List';

        const firstRow = [
            formattedToday, "", 
            title, "",
            ...Array(csvConfig.columnHeaders.length *2 - 4).fill('')
        ];    
        const blankRow = Array(csvConfig.columnHeaders.length).fill('');
        const tableDataWithSpacing = [firstRow,blankRow];

        const tableData = campaigns.map((campaign) => {
            const {
                id,
                campaignName,
                createdDate,
                status,
                endDate,
                business: { businessName, email, phone, address },
                template: { templateName }
            } = campaign;
            return [id,campaignName,"",createdDate,"",endDate,"",status, businessName,"", email,"","", address,"","", templateName,"","",];
        });
    
    
        const csv = generateCsv({ ...csvConfig, showColumnHeaders: false })([...tableDataWithSpacing, csvConfig.columnHeaders, ...tableData]); // Include the first row, column headers, and business data

        download({ ...csvConfig, showColumnHeaders: false })(csv);
    };
    
    const handleAllExportRowsPDF = async () => {
        const response = await customFetch("/tables/allPagesCampagnes", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });
    
        console.log(response.data);
    
        const campaigns = response.data;
    
        const tableData = campaigns.map(campaign => [
            campaign.campaignName,
            formatDate(campaign.createdDate),
            formatDate(campaign.endDate),
            campaign.business.businessName,
            campaign.status,
            campaign.template.templateName,
            
        ]);
    
        const doc = new jsPDF();
    
        const img = new Image();
        img.src = logo;
        img.onload = () => {
            addHeaderToPDF(doc, img, null, true);

            
        const tableHeaders = ['Campaign Name','Start Date','End Date', 'Business Name','Status', 'Template Name'];
    
        doc.autoTable({
            head: [tableHeaders],
            body: tableData,
            startY: 60 
        });
    
        doc.save('List_Campaigns.pdf');
    };
    };
    
    const handleAllExportRowsCSV = async () => {

        const response = await customFetch("/tables/allPagesCampagnes", {
            headers: { Authorization: `Bearer ${admin.token}` }
        });
    
        console.log(response.data);
        
        const campaigns = response.data;

        const today = new Date();
        const formattedToday = `${today.getDate()}-${today.getMonth() + 1}-${today.getFullYear()}`;
        const title = 'Campaign List';

        const firstRow = [
            formattedToday, "", 
            title, "",
            ...Array(csvConfig.columnHeaders.length *2 - 4).fill('')
        ];    
        const blankRow = Array(csvConfig.columnHeaders.length).fill('');
        const tableDataWithSpacing = [firstRow,blankRow];


        const tableData = campaigns.map(campaign => {

            const {id,
            campaignName,
            createdDate,
            endDate,
            status,
            business,
            template,
            } = campaign
            return [id,campaignName,"",createdDate,"",endDate,"",status, business.businessName,"", business.email,"","", business.address,"","", template.templateName,"","",];

    });
        const csv = generateCsv({ ...csvConfig, showColumnHeaders: false })([...tableDataWithSpacing, csvConfig.columnHeaders, ...tableData]);

        download({ ...csvConfig, showColumnHeaders: false })(csv);

    }
    
    const formatDate = (createdDate) => {
        const date = new Date(createdDate);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        return `${day}/${month}/${year}`;
    };

    const formatTime = (endDate) => {
        const date = new Date(endDate);
    
        let hours = date.getHours();
        let minutes = date.getMinutes();
    
        const amOrPm = hours >= 12 ? 'PM' : 'AM';
    
        hours = hours % 12;
        hours = hours ? hours : 12;
    
        minutes = minutes < 10 ? '0' + minutes : minutes;
    
        return `${hours}:${minutes}`+` `+`${amOrPm}`;
    };

    const getStatusIcon = (status) => {
        switch(status) {
            case 'Planned':
                return <ClockIcon className="w-4 h-4 ml-1 text-gray-800 dark:text-black" />;
            case 'In progress':
                return <PlayIcon className="w-4 h-4 ml-1 text-gray-800 dark:text-black" />;
            case 'Completed':
                return <CheckCheckIcon className="w-4 h-4 ml-1 text-gray-800 dark:text-black" />;
            default:
                return null;
        }
    }

    
    if (!campaigns || campaigns.length < 1) {
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
         onClick={() => handleExportRowsPDF(campaigns)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export Page PDF'} 
    </button>
    <button
         className={`btn btn-success btn-sm ${isGenerating ? 'disabled' : ''}`}
         onClick={() => handleExportRowsCSV(campaigns)}
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
         onClick={() => handleAllExportRowsCSV(campaigns)}
        style={{ borderRadius: '0.25rem', padding: '0.5rem 1rem', fontSize: '1rem' }}
        disabled={isGenerating}>
        <Download className='w-4 h-4' />
        {isGenerating ? 'Exporting...' : 'Export All CSV'}
    </button>     
</div>
<div className="overflow-x-auto mb-4">
<div ref={conponentPDF} style={{width:'100%'}}>

            <table className="table table-zebra-zebra">
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
                        <th>Campaign Name</th>
                        <th className="text-center">Business Name</th>
                        <th className="text-center">Template</th>
                        <th style={{ cursor: 'pointer' }} onClick={toggleCreatedDateSort}>
                            <div className="flex items-center">
                                Start Date
                                <ArrowUpDown className={`w-4 h-4 ml-1 text-gray-800 dark:text-black`} />

                            </div>
                        </th>
                        <th>
                            <div className="flex items-center">
                                End Date
                            </div>
                        </th>
                        <th className="text-center">Status</th>


                        {/* <th className="text-center">Services area</th> */}
                        {/* <th className="text-center">Langues</th> */}
                    </tr>
                </thead>
                <tbody>
                {Array.isArray(campaigns) && campaigns.map((campaign) => (
        <tr key={campaign.id}>
            <th>
                <label className="flex justify-center gap-2">
                    <input
                        type="checkbox"
                        className="checkbox"
                        checked={selectedCampaigns.includes(campaign.id)}
                        onChange={() => handleSelectCampaign(campaign.id)}
                    />
                </label>
            </th>
            <td className="font-bold">{campaign.campaignName}</td>
            <td>{campaign.business.businessName}
            <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{campaign.business.email}</div>
            </td>
            <td>{campaign.template.templateName}</td>
            <td>{formatDate(campaign.createdDate)}
            <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{formatDateDuration(campaign.createdDate)}</div>
            </td>
            <td>{formatDate(campaign.endDate)}
            <div className="text-sm font-normal text-gray-500 dark:text-gray-400"
                                     style={{ fontSize: '0.8em' }}>{formatTime(campaign.endDate)}</div>
            </td>
            {/* <td>
            {getStatusIcon(campaign.status)} 
            {campaign.status}

            </td> */}

<td className="flex justify-center items-center w-40">
    <div>
      {campaign.status === "In progress" ? (
        <div className="flex items-center">
          <div className="h-2.5 w-2.5 rounded-full bg-green-400 mr-2"></div>
          <span className="text-sm">In progress</span>
        </div>
        
    ) : (
        <div className="flex items-center">
          <div className="h-2.5 w-2.5 rounded-full bg-red-500 mr-2"></div>
          <span className="text-sm">Completed</span>
        </div>
      )}
    </div>
  </td>


                            

            {/* <td>{campaign.template.templateName}</td> */}

            {/* <td>
            <button class="btn btn-active btn-sucess && btn-sm" onClick={() => {window.location.href=`/campaign/${campaign.id}`}}>details</button>
            </td> */}
        </tr>
    ))}
</tbody>

            </table>
        </div>
        </div>
        </div>


    );
};

export default CampaignList;
