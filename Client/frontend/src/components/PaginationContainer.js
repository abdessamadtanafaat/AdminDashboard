import {useLoaderData , useNavigate , useLocation} from 'react-router-dom'
const PaginationContainer = () => {
  const { meta } = useLoaderData();
  const { pageCount, page } = meta;
  console.log(meta)
  const { search, pathname } = useLocation();
  const navigate = useNavigate();
  const handlePageChange = (pageNumber) => {
    const searchParams = new URLSearchParams(search);
    searchParams.set('page', pageNumber);
    navigate(`${pathname}?${searchParams.toString()}`);
  };

  const addPageButton = ({ pageNumber, activeClass }) => {
    return (
      <button
        key={pageNumber}
        onClick={() => handlePageChange(pageNumber)}
        className={`btn btn-base sm:btn-md border-none join-item ${
          activeClass ? ' bg-base-300 border-base-300 ' : 'bg-base-100'
        }`}
      >
        {pageNumber}
      </button>
    );
  };

  const renderPageButtons = () => {
    const pageButtons = [];
    
    pageButtons.push(addPageButton({ pageNumber: 1, activeClass: page === 1 }));
    if (page > 2) {
      pageButtons.push(
        <button className='join-item btn btn-base sm:btn-md' key='dots-1'>
          ...
        </button>
      );
    }
    if (page !== 1 && page !== pageCount) {
      pageButtons.push(addPageButton({ pageNumber: page, activeClass: true }));
    }
  
    if (page < pageCount - 1) {
      pageButtons.push(
        <button className='join-item btn btn-base sm:btn-md' key='dots-2'>
          ...
        </button>
      );
    }
    pageButtons.push(
      addPageButton({ pageNumber: pageCount, activeClass: page === pageCount })
    );
    return pageButtons;
  };

  if (pageCount < 2) return null;

  return (
    <div className='mt-16 flex justify-end'>
      <div className='join'>
        <button
          className='btn btn-base btn-accent sm:btn-md join-item'
          onClick={() => {
            let prevPage = page - 1;
            if (prevPage < 1) prevPage = pageCount;
            handlePageChange(prevPage);
          }}
        >
          Prev
        </button>
        {renderPageButtons()}
        <button
          className='btn btn-base btn-accent hover:btn-accent sm:btn-md join-item'
          onClick={() => {
            let nextPage = page + 1;
            if (nextPage > pageCount) nextPage = 1;
            handlePageChange(nextPage);
          }}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default PaginationContainer
