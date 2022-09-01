import React, { useState } from "react";
import './Paging.css';
import Pagination from "react-js-pagination";

const Paging = (props) => {
    const [page, setPage] = useState(1);

    const handlePageChange = (page) => {
        setPage(page);
        props.setNo(page-1)
    };

    return (
        <Pagination
            activePage={page}
            itemsCountPerPage={5}
            totalItemsCount={+(props.count)}
            pageRangeDisplayed={5}
            prevPageText={"‹"}
            nextPageText={"›"}
            onChange={handlePageChange}
        />
    );
};

export default Paging;