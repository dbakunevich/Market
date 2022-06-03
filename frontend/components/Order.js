import React from "react"

function Order({ChangeOrder}){
    const [order, setOrder] = React.useState("");

    const click = async (index) => {
        if (index === 1) {
            if (order === "cost")
                setOrder("costFalse")
            else
                setOrder("cost");
        }
        if (index === 2) {
            if (order === "name")
                setOrder("nameFalse")
            else
                setOrder("name");
        }
        checkOrder()
    }

    const checkOrder = () => {
        switch (order){
            case "name":{
                ChangeOrder("name_order=true&");
                break
            }
            case "cost":{
                ChangeOrder("price_order=true&");
                break
            }
            case "nameFalse":{
                ChangeOrder("name_order=false&");
                break
            }
            case "costFalse":{
                ChangeOrder("price_order=false&");
                break
            }
            default: {
                console.log("error in order " + order)
                ChangeOrder("");
                break
            }
        }
    }

    return (
        <ul>
            <p onClick={()=>{click(1)}}>По цене </p>
            <p onClick={()=>{click(2)}}>По имени</p>
            <p onClick={()=>{click(3)}}>По оценкам</p>
        </ul>
    )
}
export default Order
