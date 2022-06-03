import React from "react"

function Filters({ChangeFilters}){
    const [lowCost, setLowCost] = React.useState(null);
    const [highCost, setHighCost] = React.useState(null);


    const checkFilter = () => {
        let filter = "";
        if (lowCost)
            filter += "lp=" + lowCost + "&";
        if (highCost)
            filter += "hp=" + highCost + "&";
        ChangeFilters(filter);
    }

    const click = (index) => {
        if (index === 0) {
            setLowCost(null)
            setHighCost(null)
            checkFilter();
        }
    }

    const lc = (event) => {
        let ptr = event.target.value;
        if (ptr > 0) {
            setLowCost(ptr)
        }

    }

    const hc = (event) => {
        let ptr = event.target.value;
        if (ptr > 0) {
            setHighCost(ptr)
        }
    }


    return (
        <ul>
            <p onClick={()=>{click(0)}}>Сбросить</p>
            <input type="number" min={0} max={1000000} placeholder="Минимальная цена" onChange={lc}/>
            <input type="number" min={0} max={1000000} placeholder="Максимальная цена" onChange={hc}/>
            <input type="number" min={0} placeholder="Минимальная оценка"/>
            <input type="number" min={0} max={10} placeholder="Максимальная оценка"/>
            <p onClick={()=>{checkFilter()}}>Применить</p>
        </ul>
    )
}
export default Filters
