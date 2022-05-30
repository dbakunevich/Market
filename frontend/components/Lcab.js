import React from 'react'

function Lcab () {
    const[activeIndex, setActiveIndex] = React.useState(true);
    const[login, setLogin] = React.useState("");
    const[password, setPassword] = React.useState("");
    const[logged, setLogged] = React.useState(0);
    const[user, setUser] = React.useState("Авторизация");
    const[error, setError] = React.useState(0);




    const enterLCab = async (index) => {
        if (index === 2) {
            setLogged(0)
            setUser("Авторизация")
            return;
        }
        if ((login !== "") && (password !== "") && (login.length < 12)) {
            let action;
            if (index === 0) {
                action = "login?"
            }
            if (index === 1)
                action = "registration?"
            let query = "http://51.250.16.106:8181/" + action + "login=" + login + "&password=" + password;
            let responsePromise = await fetch(query);
            responsePromise = await responsePromise.text();
            if (responsePromise === '"Неверный логин или пароль!"')
                setError(1);
            else {
                if (responsePromise === '"Данный пользователь уже зарегистрирован!"')
                    setError(2);
                else {
                    setUser(responsePromise);
                    setLogged(true);
                    setActiveIndex(!activeIndex);
                }
            }
        } else console.log("пустой логин или пароль")
    }



    const chLogin = (event) => {
        setLogin(event.target.value);
    }

    const chPassword = (event) => {
        setPassword(event.target.value);
    }




    return (
        <>
            <div className="nav_link" href="../pages/about.js">About</div>
            <div className="nav_link" href="/">About</div>
            <div className="nav_link" href="/">Button</div>
            <div className="nav_link" onClick={()=>{setActiveIndex(!activeIndex)}} >{user}</div>
            <div className="LCabWindow">
                <div className={activeIndex  ? "AuthInner" : "AuthInner active"}>
                    <input type="text" placeholder="Введите логин" onChange={chLogin}/>
                    <input type="text" placeholder="Введите пароль" onChange={chPassword}/>
                    <a className={logged === 0 ? "btn active" : "btn"} onClick={()=>{enterLCab(0)}}>Войти</a>
                    <a className={logged === 0 ? "btn active" : "btn"} onClick={()=>{enterLCab(1)}}>Регистрация</a>
                    <a className={logged === 0 ? "btn" : "btn active"}  onClick={()=>{enterLCab(2)}}>Выйти</a>
                    <a className={error === 1 ? "error active" : "error "}  >Неверный логин или пароль!</a>
                    <a className={error === 2 ? "error active" : "error "}  >Данный пользователь уже существует!</a>
                </div>
            </div>
        </>
    )
}
export default Lcab
