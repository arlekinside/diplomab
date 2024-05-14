import React from "react";
import PopupMenuItem from "./PopupMenuItem";

function HeaderPopupMenuItem(props: {children: any, href?: string, onClick?: () => void, style?: React.CSSProperties}) {
    return (
        <PopupMenuItem href={props.href} onClick={props.onClick} style={{...props.style, fontSize: '1vw'}}>
            {props.children}
        </PopupMenuItem>
    );
}

export default HeaderPopupMenuItem;