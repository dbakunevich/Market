import React, {Component} from "react";



export default class Results extends Component{

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            items: [],
            lastName: 'name'
        };
    }

    componentDidMount() {
        this.lastName = this.props.name;
        let url = "http://localhost:8181/search?";
        let search = "toSearch=";
        if (this.props.name === null)
            search += "phone&";
        else
            search += this.props.name + "&";

        let page_size = "page_size=" + this.props.page_size+ "&";
        let query = url +  search + page_size;
        fetch(query,{
        })
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
        if (this.lastName !== this.props.name)
            this.componentDidMount();
        const {error, isLoaded, items} = this.state;
        if (error) {
            return <p> Error {error.message}</p>
        } else if (!isLoaded) {
            return <p>Loading...</p>
        } else
            return (
            <ul>
                {items.map(item => (
                    <li key={item.name}>
                        <img width='50px' src={item.images}/>
                        {item.name}
                        {item.descr}
                    </li>
                ))}
            </ul>
            )
    }
}
