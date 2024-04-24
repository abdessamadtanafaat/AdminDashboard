import React from "react";
import { useLoaderData, Link } from "react-router-dom";
import default_avatar from '../assets/default_avatar.webp';

const BusinessOwnerList = () => {
    const { businessOwners, params } = useLoaderData();

    if (!businessOwners || businessOwners.length < 1) {
        return (
            <div className="font-bold mx-auto text-4xl text-center text-error">
                There is no match for the keyword You Typed !!!
            </div>
        );
    }

    return (
        <div className="overflow-x-auto">
            <table className="table table-zebra-zebra">
                {/* head */}
                <thead>
                    <tr>
                        <th></th>
                        <th>Profile</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th className="text-center">Email</th>
                    </tr>
                </thead>
                <tbody>
                    {businessOwners.map((owner) => {
                        const { firstName, lastName, email, avatarUrl, username, id } = owner;
                        return (
                            <tr key={id}>
                                <th>
                                    <label className="flex justify-center gap-2">
                                        <input type="checkbox" className="checkbox" />
                                    </label>
                                </th>
                                <td>
                                    <div className="flex items-center gap-3">
                                        <div className="avatar">
                                            <div className="mask mask-squircle w-12 h-12">
                                                <img alt="profile image" src={avatarUrl ? `${avatarUrl}` : default_avatar} />
                                            </div>
                                        </div>
                                        <div></div>
                                    </div>
                                </td>
                                <td><div className="font-bold">{firstName}</div></td>
                                <td><div className="font-bold">{lastName}</div></td>
                                <td className="text-center">{email}</td>
                                <th>
                                    <Link key={id} to={`/businessOwner/${id}`} className="btn btn-ghost btn-xs">details</Link>
                                </th>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default BusinessOwnerList;
