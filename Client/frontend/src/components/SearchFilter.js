import { useState } from 'react';
import {FormInput} from '../components'
import { useLoaderData , Form } from 'react-router-dom';
const SearchFilter = () => {
  const {params} = useLoaderData();
  const {searchKey} = params;
  const [searchValue ,setSearchValue] = useState(searchKey)
  console.log(params);
  
  return (
    <Form className="join">
        <input className="input input-accent input-bordered join-item" placeholder="Type a keyword" defaultValue={searchKey} name="searchKey" onChange={(e)=>setSearchValue(e.target.value)}/>
        <button className="btn btn-accent join-item text-base-content rounded-r-full p-5 place-content-center">Search</button>
    </Form>

  )
}

export default SearchFilter
