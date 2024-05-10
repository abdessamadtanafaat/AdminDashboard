import { useState } from 'react';
import { useItemsContext } from "./ItemContext";

const PrivilegesList = () => {
    const { checkedPredefinedItems, setCheckedPredefinedItems, predefinedItems, setPredefinedItems , handleSelectAllChange } = useItemsContext();

    console.log(checkedPredefinedItems);

    const handleCheckboxChange = (event, privilege) => {
        if (checkedPredefinedItems.some((checkedItem) => checkedItem.id === privilege.id)) {
            setCheckedPredefinedItems(checkedPredefinedItems.filter((checkedItem) => checkedItem.id !== privilege.id));
        } else {
            setCheckedPredefinedItems([...checkedPredefinedItems, privilege]);
        }
    };



    return (
        <ul className="menu">
            <li>
                <details open>
                    <summary>
                        <label className="inline-flex items-center">
                            <input
                                type="checkbox"
                                className="form-checkbox checkbox checkbox-sm checkbox-secondary h-4 w-4"
                                checked={checkedPredefinedItems.length === predefinedItems.length}
                                onChange={handleSelectAllChange}
                            />
                            <span className="ml-3 text-base font-semibold">UnGranted Privileges</span>
                        </label>
                    </summary>
                    <ul className="menu menu-sm bg-base-200 rounded-lg max-w-xs w-full">
                        {predefinedItems.map((privilege) => {
                            const { id, name, desc } = privilege;
                            return (
                                <li key={id}>
                                    <label className="inline-flex items-center">
                                        <input
                                            type="checkbox"
                                            className="form-checkbox checkbox checkbox-sm checkbox-secondary h-4 w-4"
                                            defaultChecked={false}
                                            checked={checkedPredefinedItems.some((checkedItem) => checkedItem.id === privilege.id)}
                                            onChange={(event) => handleCheckboxChange(event, privilege)}
                                        />
                                        <span className="text-base font-semibold">{name}</span>
                                    </label>
                                </li>
                            );
                        })}
                    </ul>
                </details>
            </li>
        </ul>
    );
};

export default PrivilegesList;
