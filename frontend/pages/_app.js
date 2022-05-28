import '../styles/globals.css';
import '../styles/searchButton.css';
import '../styles/filters.css';
import '../styles/resultsOfSearch.css'
import React, {useState} from "react";
import Results from "../components/Results";
import Button from "../components/SearchButton";
import Filters from "../components/Filters";
import ChangePager from "../components/ChangePager";


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


    const searchQuery = (search) => {
        setSearch(search)
    }
    console.log(search);
    return (
        <>
            <div className="header">
                <div className="header_inner">
                    <Link href="/">
                        <a className="logo">Yans</a>
                    </Link>
                    <Button onChange={searchQuery}/>
                    <nav>
                        <a className="nav_link" href="../pages/about.js">About</a>
                        <a className="nav_link" href="#">About</a>
                        <a className="nav_link" href="#">Button</a>
                        <a className="nav_link" href="#">Button</a>
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
