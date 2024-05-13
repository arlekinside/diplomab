import Params from "../../Params";
import Label from "../text/Label";
import {Button, MenuItem} from "@mui/material";
import React from "react";
import PopupMenu from "../PopupMenu";
import PopupMenuItem from "../PopupMenuItem";
import NavigationButton from "../NavigationButton";
import {useCookies} from "react-cookie";

function Header(props: { navigation?: boolean }) {
    let username = 'Pidor';

    const [cookies, setCookie, removeCookie] = useCookies();

    const onLogout = () => {
        removeCookie(Params.cookie.uname);
        window.location.href = Params.path.logout;
    }

    return (
        <div style={{
            display: 'flex', flexDirection: 'row', justifyContent: 'space-around', alignItems: 'center', width: '100%'
        }}>
            <h1 style={{color: Params.colors.lightText, margin: 0}}>{Params.labels.appName}</h1>
            <div style={{
                width: '70%',
                minHeight: '100px',
                display: 'flex',
                alignItems: 'center',
                flexDirection: 'row',
                justifyContent: 'space-around',
                backgroundColor: Params.colors.background,
                borderRadius: "15px",
                flexWrap: "wrap"
            }}>
                <NavigationButton href={Params.path.dashboard}>
                    Dashboard
                </NavigationButton>
                <PopupMenu label='MoneyFlow'>
                    <PopupMenuItem href={Params.path.login}>New</PopupMenuItem>
                    <PopupMenuItem href={Params.path.login}>One time</PopupMenuItem>
                    <PopupMenuItem href={Params.path.login}>Recurring</PopupMenuItem>
                </PopupMenu>
                <PopupMenu label='Savings'>
                    <PopupMenuItem href={Params.path.login}>New</PopupMenuItem>
                    <PopupMenuItem href={Params.path.login}>One time</PopupMenuItem>
                    <PopupMenuItem href={Params.path.login}>Recurring</PopupMenuItem>
                </PopupMenu>
                <PopupMenu label='Profile'>
                    <PopupMenuItem href={Params.path.login}>Notifications</PopupMenuItem>
                </PopupMenu>
            </div>
            <div style={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'space-between',
                alignItems: 'center'
            }}>
                <Label isLight>{username}</Label>
                <Button size={"medium"} color='secondary' variant="outlined" href={Params.path.logout} onClick={onLogout}>Logout</Button>
            </div>
        </div>
    )
}

export default Header;