import React from "react";
import { useLoaderData, Link } from "react-router-dom";
import default_avatar from '../assets/default_avatar.webp';

const BusinessList = () => {
    const { businesses, params } = useLoaderData();

    if (!businesses || businesses.length < 1) {
        return (
            <div className="font-bold mx-auto text-4xl text-center text-error">
                There are no businesses matching the search criteria.
            </div>
        );
    }

    const formatCreatedDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
    };

    return (
        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
                {/* table header */}
                <thead>
                    <tr>
                        <th></th>
                        <th>Business Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Owner</th>
                        <th>Created Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {businesses.map((business) => {
                        const {businessName, email, phone, createdDate, id,user } = business;
                        return (
                            <tr key={id}>
                                <td>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox" className="checkbox" />
                                    </label>
                                </td>
                                <td><div className="font-bold">{businessName}</div></td>
                                <td>{email}</td>
                                <td>{phone}</td>
                                <td><div className="font-bold">{user.fullName}</div></td>
                                <td>{formatCreatedDate(createdDate)}</td>
                                <td>
                                    <Link to={`/business/${id}`} className="btn btn-ghost btn-xs">Details</Link>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default BusinessList;
