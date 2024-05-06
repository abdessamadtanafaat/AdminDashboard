import {BriefcaseBusiness ,User  ,MessageCircleQuestion , UserRoundCheck } from 'lucide-react'
import { BarChart , LineChart , CreationLine } from '../components';
import { customFetch } from '../utils';
import { useLoaderData } from 'react-router-dom';
export const loader =(store)=>async({})=>{
  const admin = store.getState().adminState.admin ; 
  try{
    const response= await customFetch("/config/statistics",{  headers: {
      Authorization: `Bearer ${admin.token}`} 
    }) ;
    console.log(response.data)
    return {businessNbr : response.data.businessNbr , usersNbr :response.data.usersNbr , 
      compainsNbr : response.data.compainsNbr , 
      customersNbr :response.data.customerNbr, 
      businessTypes :response.data.businessTypes , 
      businesesCreated : response.data.businesesCreated , 
      campaignsCreated: response.data.campaignsCreated
    }

  }
  catch(err){
    return {businessNbr : 0 , usersNbr  :0 , compainsNbr : 0, customersNbr :0,businessTypes :{} ,businesesCreated : {} , campaignsCreated: {}} ; 

  }
  
}

const Landing = () => {
  
  const {businessNbr , usersNbr , compainsNbr , customersNbr , businessTypes , businesesCreated , campaignsCreated}= useLoaderData();
  return (
    <div className="flex flex-col justify-between  items-center gap-6">
      <div className="stats shadow md:stats-horizontal stats-vertical">
        <div className="stat">
          <div className="stat-figure text-secondary">
            <BriefcaseBusiness />
          </div>
          <div className="stat-title">Busineses</div>
            <div className="stat-value">{businessNbr}</div>
            <div className="stat-desc">Total</div>
          </div>
          <div className="stat">
            <div className="stat-figure text-secondary">
              <User />  
          </div>
        <div className="stat-title">Users</div>
<div className="stat-value">{usersNbr}</div>
<div className="stat-desc">↗︎ 400 (22%)</div>
</div>

<div className="stat">
<div className="stat-figure text-secondary">
<MessageCircleQuestion />
</div>
<div className="stat-title">Compains</div>
<div className="stat-value">{compainsNbr}</div>
<div className="stat-desc">↘︎ 90 (14%)</div>
</div>
<div className="stat">
<div className="stat-figure text-secondary">
<UserRoundCheck />
</div>
<div className="stat-title">Customers</div>
<div className="stat-value">{customersNbr}</div>
<div className="stat-desc">↘︎ 90 (14%)</div>
</div>

      </div>
      <div className="w-full"> 
      <BarChart chart={businessTypes}/>
      <CreationLine businessData={businesesCreated} campaignData={campaignsCreated}/>
      </div>
      
    </div>
   

  )
}

export default Landing
