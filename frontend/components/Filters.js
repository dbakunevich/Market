import React from "react"

function Filters({ChangeFilters}){
    const [lowCost, setLowCost] = React.useState(null);
    const [highCost, setHighCost] = React.useState(null);
    const [stars, setStars] = React.useState(0);


    const checkFilter = () => {
        let filter = "";
        if (lowCost)
            filter += "lp=" + lowCost + "&";
        if (highCost)
            filter += "hp=" + highCost + "&";
        if (stars !== 0)
            filter += "rating=" + stars + "&";
        ChangeFilters(filter);
    }

    const click = (index) => {
        switch (index) {
            case 0: {
                setLowCost(null);
                setHighCost(null);
                setStars(0);
                checkFilter();
                break;
            }
            case 1: {
                setStars(0);
                break;
            }
            case 2: {
                setStars(4.5);
                break;
            }
            case 3: {
                setStars(4);
                break;
            }
            case 4: {
                setStars(3.5);
                break
            }
            default: {
                console.log("error ind button")
                return;
            }
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
            <div>Цена, Р</div>
            <input name="input-min"  type="numeric" pattern="[0-9 ]*"/>
            <a>-</a>
            <input name="input-max"  type="numeric" pattern="[0-9 ]*"/>
            <div>Оценка товара по отцывам</div>
            <p onClick={()=>{click(1)}}>Любой</p>
            <p onClick={()=>{click(2)}}>4,5 и выше</p>
            <p onClick={()=>{click(3)}}>4 и выше</p>
            <p onClick={()=>{click(4)}}>3,5 и выше</p>
            <p className="btn" onClick={()=>{checkFilter()}}>Применить</p>
            <p className="btn" onClick={()=>{click(0)}}>Сбросить</p>
        </ul>
    )
}
export default Filters
