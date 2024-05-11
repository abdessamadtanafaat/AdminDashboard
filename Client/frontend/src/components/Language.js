import {useState} from 'react'
const Language = ({id  ,name , companges}) => {
  const [campains , setCampains ] = useState(companges || [])
  console.log(companges)
  return (
    <div id={`language_${id}`}
    className="shadow-md w-full h-50 p-2 rounded-lg flex flex-col carousel-item justify-between gap-3 border border-outline border-primary">
        <div className="">
            <h2 className="font-bold ">{name}</h2>
        </div>
        <div className={`w-full pr-3 h-60 overflow-y-auto border-primary scrollbar-track-base-content
            scrollbar-thumb-base-100 p-1
            scrollbar-thin flex flex-col gap-2  `}>
                {!campains.length <1 || (
                    <h3 className="text-center font-bold text-bg-info text-xl">No Campains with availble with this language </h3>
                )}

                {!campains || campains.map((compagne)=>{
                    const {  campaignName ,business } = compagne
                    const {businessName } = business
                    
                    return (
                        <div id={compagne.id}className="w-full p-2 bg-base-200   rounded-md shadow-md
                        flex justify-between  gap-5 ">
                            
                            <h3 className="font-semibold capitalize ">{campaignName}</h3>
                            <h3 className="font-semibold capitalize ">{businessName}</h3>
                            

                        </div>
                    )

                })}
                
                
            </div>  

      
    </div>
  )
}

export default Language
