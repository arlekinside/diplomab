import {MenuItem} from "@mui/material";
import React from "react";

function PopupMenuItem(props: {children: any, href?: string, onClick?: () => void, style?: React.CSSProperties}) {
    return (
        <MenuItem onClick={props.href ? () => window.location.href = props.href as string : props.onClick} style={props.style}>
            {props.children}
        </MenuItem>
    );
}

export default PopupMenuItem;