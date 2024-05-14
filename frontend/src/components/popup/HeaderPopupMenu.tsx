import React from "react";
import PopupMenu from "./PopupMenu";

function HeaderPopupMenu(props: { children: any, label: string, style?: React.CSSProperties }) {

    return (
        <PopupMenu label={props.label} style={{...props.style, fontSize: "1.5vw"}}>
            {props.children}
        </PopupMenu>
    );
}

export default HeaderPopupMenu;