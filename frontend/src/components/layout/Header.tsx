import Params from "../../Params";
import Label from "../text/Label";
import {Button} from "@mui/material";
import React, {useEffect, useState} from "react";
import NavigationButton from "../NavigationButton";
import {useCookies} from "react-cookie";
import HeaderPopupMenu from "../popup/HeaderPopupMenu";
import HeaderPopupMenuItem from "../popup/HeaderPopupMenuItem";
import {Base64} from "js-base64";

function Header() {

    const [cookies, setCookie, removeCookie] = useCookies();
    const [username, setUsername] = useState<string>(Base64.decode(cookies[Params.cookies.uname] || ''));

    const onLogout = () => {
        removeCookie(Params.cookies.uname);
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
                <NavigationButton href={Params.path.dashboard} style={{fontSize: '1.5vw'}}>
                    Dashboard
                </NavigationButton>
                <HeaderPopupMenu label='MoneyFlow'>
                    <HeaderPopupMenuItem href={Params.path.mf.n}>New</HeaderPopupMenuItem>
                    <HeaderPopupMenuItem href={Params.path.mf.one}>One time</HeaderPopupMenuItem>
                    <HeaderPopupMenuItem href={Params.path.mf.recurring}>Recurring</HeaderPopupMenuItem>
                </HeaderPopupMenu>
                <HeaderPopupMenu label='Savings'>
                    <HeaderPopupMenuItem href={Params.path.savings.n}>New</HeaderPopupMenuItem>
                    <HeaderPopupMenuItem href={Params.path.savings.page}>View</HeaderPopupMenuItem>
                </HeaderPopupMenu>
                <HeaderPopupMenu label='Profile'>
                    <HeaderPopupMenuItem href={Params.path.login}>Notifications</HeaderPopupMenuItem>
                </HeaderPopupMenu>
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