import React from "react"

function Filters({ChangeFilters}){
    const [cost, setCost] = React.useState(false);
    const [name, setName] = React.useState(false);

    const click = (index) => {
        if (index === 0) {
            setCost(false);
            setName(false);
            checkFilter();
        }
        if (index === 1){
            setCost(!cost);
            setName(false);
            checkFilter();
        }
        if (index === 2){
            setName(!name);
            setCost(false);
            checkFilter()
        }
    }

    const checkFilter = () => {
        let filter = "price_order=" + cost + "&name_order=" + name + "&";
        ChangeFilters(filter);
    }

    return (
        <ul>
            <p onClick={()=>{click(0)}}>Сбросить</p>
            <p>Минимальная цена</p>
            <p>Максимальная цена</p>
            <p>Оценка</p>
        </ul>
    )
}
export default Filters
