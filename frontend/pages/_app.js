import '../styles/globals.css';
import '../styles/searchButton.css';
import '../styles/filters.css';
import '../styles/resultsOfSearch.css'
import '../styles/lcab.css'
import React, {useState} from "react";
import Results from "../components/Results";
import Button from "../components/SearchButton";
import Filters from "../components/Filters";
import Lcab from "../components/Lcab"


import Link from "next/link";




const  App = () => {
    const  [search, setSearch] = useState('phone')
    const  [filter, setFilter] = useState('')
    const searchQuery = (search) => {
        setSearch(search)
    }
    const searchWithFilters = (filter) => {
        setFilter(filter)
    }

    return (
        <>
            <div className="header">
                <div className="header_inner">
                    <Link href="/">
                        <a className="logo">Yans</a>
                    </Link>
                    <Button onChange={searchQuery}/>
                    <nav>
                        <Lcab/>
                    </nav>
                </div>
            </div>
            <div className="filters">
                <div className="filters_inner">
                    <Filters ChangeFilters={searchWithFilters}/>
                </div>
            </div>
            <div className="results">
                <Results name={search} filter={filter} page_size={100}/>
            </div>
        </>
    )
};
export default App;
