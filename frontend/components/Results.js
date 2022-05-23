import React, {Component} from "react";



export default class Results extends Component {


    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            items: []
        };
    }

    componentDidMount() {
        let response = fetch("http://51.250.108.33:8181/search?toSearch=abc&page_size=5",{
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
                        {item.name}
                    </li>
                ))}
            </ul>
            )
    }
}
