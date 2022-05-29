import React from "react"

function Filters({ChangeFilters}){
    const [cost, setCost] = React.useState(false);
    const [name, setName] = React.useState(false);

    const click = (index) => {
        if (index === 0) {
            setCost(false);
            setName(false);
            ChangeFilters('');
        }
        if (index === 1){
            setCost(!cost);
            setName(false);
            chekFilter();
        }
        if (index === 2){
            setName(!name);
            setCost(false);
            chekFilter()
        }
        console.log("click");
    }

    const chekFilter = () => {
        let filter = "price_order=" + cost + "&name_order=" + name + "&";
        ChangeFilters(filter);
    }

    return (
        <ul>
            <p onClick={()=>{click(0)}}>Сбросить</p>
            <p onClick={()=>{click(1)}}>По цене </p>
            <p onClick={()=>{click(2)}}>По имени</p>
            <p>Минимальная цена</p>
            <p>Максимальная цена</p>
        </ul>
    )
}
export default Filters
