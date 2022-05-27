import '../styles/globals.css';
import '../styles/searchButton.css';
import React, {useState} from "react";
import Results from "../components/Results";
import Button from "../components/SearchButton";
import Link from "next/link";


const  App = () => {
    const  [search, setSearch] = useState('phone')

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
                <p>filter</p>
                <p>filter</p>
                <p>filter</p>
                <p>filter</p>


            </div>

            <div className="results">
                <Results name={search} page_size={5}/>
            </div>
    </>
    )
};
export default App;
