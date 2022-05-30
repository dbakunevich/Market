import React, {Component} from "react";
import Link from "next/link";
import {wait} from "next/dist/build/output/log";



export default class Results extends Component{


    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            items: [],
            lastName: '',
            lastFilter: ''
        };
    }

    getResults() {
        this.lastName = this.props.name;
        this.lastFilter = this.props.filter;
        let url = "http://51.250.16.106:8181/search?";
        let search = "toSearch=";
        search += this.lastName + "&";
        search += this.lastFilter + "&";
        let page_num = "page=0&";
        let page_size = "page_size=" + this.props.page_size+ "&";
        let query = url +  search + page_num + page_size;
        fetch(query)
            .then(result => result.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        items: result.products
                    });
                    },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
                )
    }



    render() {
        if ((this.lastName !== this.props.name) || (this.lastFilter !== this.props.filter))
            this.getResults();
        const {error, isLoaded, items} = this.state;
        if (error) {
            return <p> Error  {error.message}</p>
        } else if (!isLoaded) {
            return <p>Loading...</p>
        } else
            return (
                <>
                    <ul>
                        {items.map(item => (
                            <div key={item.link} className="phoneBlock">
                                <Link href={item.link}>
                                    <img width='100px' src={item.images}/>
                                </Link>
                                <div className="name">{item.name}</div>
                                <div className="price">{item.price}p</div>
                            </div>
                        ))}
                    </ul>
                </>
            )
    }
}
