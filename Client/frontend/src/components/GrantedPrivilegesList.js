
import { useItemsContext } from './ItemContext'

const GrantedPrivilegesList = () => {
    const { grantedItems ,setGrantedItems , checkedGrantedItems , setCheckedGrantedItems } = useItemsContext()
    console.log(checkedGrantedItems)
    const handleCheckboxChange =(privilege)=>{
        if (checkedGrantedItems.some((checkedItem) => checkedItem.id === privilege.id)) {
            setCheckedGrantedItems(checkedGrantedItems.filter((checkedPrivilege) => checkedPrivilege.id !== privilege.id));
          } else {
            setCheckedGrantedItems([...checkedGrantedItems, privilege]);
          }
        


    }
   
    return (
        <ul className="menu menu-xs bg-base-200 rounded-lg max-w-xs w-full">
          {grantedItems.map((privilege)=>{
            const {id  , name  ,description} =privilege
            return(<li key={id}>
              <label className="inline-flex items-center">
                <input type="checkbox" className="form-checkbox checkbox-secondary checkbox h-4 w-4" defaultChecked={false}
                 checked={checkedGrantedItems.some((checkedPrivilege) => checkedPrivilege.id === privilege.id)}
                onChange={()=>handleCheckboxChange(privilege)} 
                 />
                
                <span className="text-base font-semibold">{name}</span>
              </label>
            </li>) 
          })
        }
      
  
    </ul>
    
      )
}

export default GrantedPrivilegesList
