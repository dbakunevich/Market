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
import Head from "next/head";




const  App = () => {
    const  [search, setSearch] = useState('телефоны')
    const  [filter, setFilter] = useState('price_order=false&name_order=false')
    const searchQuery = (search) => {
        setSearch(search)
    }
    const searchWithFilters = (filter) => {
        setFilter(filter)
    }

    return (
        <>
            <Head>
                <title>Market</title>
            </Head>
            <div className="header">
                <div className="header_inner">
                    <Link href="http://51.250.108.33:3000/">
                        <a className="logo">Market</a>
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
                <Results name={search} filter={filter} page_size={40}/>
            </div>
        </>
    )
};
export default App;
