import '../styles/globals.css';
import '../styles/searchButton.css';
import React from "react";
import Results from "../components/Results";
import Button from "../components/SearchButton";
import Link from "next/link";


const  App = () => {

    return (
        <div>
            <div className="header">
                <div className="header_inner">
                    <Link href="/">
                        <a className="logo">Yans</a>
                    </Link>
                    <div id="search-box" className="search-box">
                        <input id="input" type="text" placeholder="Введите для поиска..."/>
                        <Button/>
                    </div>

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
                <Results/>
            </div>

        </div>

    )
};
export default App;
