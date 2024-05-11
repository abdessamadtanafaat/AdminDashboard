import {useState ,  createContext , useContext } from "react";
import { useLoaderData } from "react-router-dom";

const ItemsContext = createContext()

const ItemsProvider = ({children})=>{

    const { systemItems, items } = useLoaderData() || { entity: {}, items: [], systemItems: [] };
    const [predefinedItems, setPredefinedItems] = useState(systemItems);
    const [grantedItems, setGrantedItems] = useState(items);
    const [checkedPredefinedItems, setCheckedPredefinedItems] = useState([]);
    const [checkedGrantedItems, setCheckedGrantedItems] = useState([]);

    const handleGrantButtonClick = () => {
        const uncheckedPredefinedItems = predefinedItems.filter(item => !checkedPredefinedItems.includes(item));
        setGrantedItems([...grantedItems, ...checkedPredefinedItems]);
        setPredefinedItems(uncheckedPredefinedItems);
        setCheckedPredefinedItems([]);
        // Clear checked items
    };
    const handleRevokeButtonClick = () => {
        const uncheckedGrantedItems = grantedItems.filter(item => !checkedGrantedItems.includes(item));
        setPredefinedItems([...predefinedItems, ...checkedGrantedItems]);
        setGrantedItems(uncheckedGrantedItems);
        setCheckedGrantedItems([]);
    };
    const handleRevokeAllButtonClick = () => {
        setPredefinedItems([...predefinedItems, ...grantedItems]);
        setGrantedItems([]);
        // Clear checked granted items
        setCheckedGrantedItems([]);
    };
    const handleSelectAllChange = (event) => {
        const { checked } = event.target;
        if (checked) {
            setCheckedPredefinedItems(predefinedItems);
        } else {
            setCheckedPredefinedItems([]);
        }
    };
    const handleSelectAllGrantedChanges = (event) => {
        const { checked } = event.target;
        if (checked) {
            setCheckedGrantedItems(grantedItems);
        } else {
            setCheckedGrantedItems([]);
        }
    };

    return (
        <ItemsContext.Provider
        value={{
            grantedItems , setGrantedItems , checkedGrantedItems , setCheckedGrantedItems , 
            predefinedItems , setPredefinedItems , checkedPredefinedItems ,setCheckedPredefinedItems ,
            handleGrantButtonClick , handleRevokeAllButtonClick , handleRevokeButtonClick , handleSelectAllChange , handleSelectAllGrantedChanges,

        }}>
            {children}
        </ItemsContext.Provider>
    )
}

export const useItemsContext =()=>useContext(ItemsContext);
export { ItemsContext, ItemsProvider }