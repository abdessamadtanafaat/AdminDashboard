import { useItemsContext } from './ItemContext';

const RolesList = () => {
  const {checkedPredefinedItems ,setCheckedPredefinedItems , predefinedItems , setPredefinedItems} = useItemsContext();
  
  console.log(checkedPredefinedItems)
  const handleCheckboxChange = (event , role) => {
    if (checkedPredefinedItems.some((checkedItem) => checkedItem.id === role.id)) {
      setCheckedPredefinedItems(checkedPredefinedItems.filter((checkedItem) => checkedItem.id !== role.id))
    } else {
      setCheckedPredefinedItems([...checkedPredefinedItems, role])
    }
  };
  
  return (
    <ul className="menu menu-xs bg-base-200 rounded-lg max-w-xs w-full">
      {predefinedItems.map((role)=>{
        const {id  , name  ,priveleges} =role
        return(<li key={id} >
          <label className="inline-flex items-center">
            <input type="checkbox" className="form-checkbox checkbox checkbox-secondary h-4 w-4" defaultChecked={false} 
            checked={checkedPredefinedItems.some((checkedItem) => checkedItem.id === role.id)} onChange={(event) => handleCheckboxChange(event, role)}
             />
            
            <span className="text-base font-semibold">{name}</span>
          </label>
        </li>) 
      })
    }
    
</ul>

  )
}

export default RolesList
