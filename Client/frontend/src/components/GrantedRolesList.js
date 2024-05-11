import { useItemsContext } from "./ItemContext";

const GrantedRolesList = () => {
    const { grantedItems, checkedGrantedItems, setCheckedGrantedItems , handleSelectAllGrantedChanges} = useItemsContext();

    const handleCheckboxChange = (event, role) => {
        if (checkedGrantedItems.some((checkedRole) => checkedRole.id === role.id)) {
            setCheckedGrantedItems(checkedGrantedItems.filter((checkedRole) => checkedRole.id !== role.id));
        } else {
            setCheckedGrantedItems([...checkedGrantedItems, role]);
        }
    };

    return (
        <ul className="menu">
            <li>
                <details open>
                    <summary>
                      <label className="inline-flex items-center">
                        <input type="checkbox" className="form-checkbox checkbox checkbox-sm checkbox-secondary h-4 w-4" checked={checkedGrantedItems.length === grantedItems.length}
                        onChange={handleSelectAllGrantedChanges} />
                        <span className="ml-3 text-base font-semibold">Granted Roles</span>
                      </label>
                    </summary>
                    <ul className="menu menu-sm bg-base-200 rounded-lg max-w-xs w-full">
                        {grantedItems.map((role) => {
                            const { id, name, privileges } = role;
                            return (
                                <li key={id}>
                                    <label className="inline-flex items-center">
                                        <input
                                            type="checkbox"
                                            className="form-checkbox checkbox checkbox-sm checkbox-secondary h-4 w-4"
                                            defaultChecked={false}
                                            checked={checkedGrantedItems.some((checkedRole) => checkedRole.id === role.id)}
                                            onChange={(event) => handleCheckboxChange(event, role)}
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

export default GrantedRolesList;
