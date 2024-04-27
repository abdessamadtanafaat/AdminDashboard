import { useItemsContext } from "./ItemContext"

const GrantedRolesList = () => {
    const { grantedItems ,setGrantedItems , checkedGrantedItems , setCheckedGrantedItems } = useItemsContext()
    console.log(checkedGrantedItems)
    const handleCheckboxChange =(role)=>{
        if (checkedGrantedItems.some((checkedRole) => checkedRole.id === role.id)) {
            setCheckedGrantedItems(checkedGrantedItems.filter((checkedItem) => checkedItem.id !== role.id));
          } else {
            setCheckedGrantedItems([...checkedGrantedItems, role]);
          }
        


    }
   
    return (
        <ul className="menu menu-xs bg-base-200 rounded-lg max-w-xs w-full">
          {grantedItems.map((role)=>{
            const {id  , name  ,priveleges} =role
            return(<li key={id}>
              <label className="inline-flex items-center">
                <input type="checkbox" className="form-checkbox checkbox checkbox-secondary checkbox-sm h-4 w-4" defaultChecked={false}
                 checked={checkedGrantedItems.some((checkedRole) => checkedRole.id === role.id)}
                onChange={()=>handleCheckboxChange(role)} 
                 />
                
                <span className="text-base font-semibold">{name}</span>
              </label>
            </li>) 
          })
        }
      
  
    </ul>
    
      )
}

export default GrantedRolesList
