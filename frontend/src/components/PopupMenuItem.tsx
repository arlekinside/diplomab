import {MenuItem} from "@mui/material";

function PopupMenuItem(props: {children: any, href?: string, onClick?: () => void}) {
    return (
        <MenuItem onClick={props.href ? () => window.location.href = props.href as string : props.onClick}>
            {props.children}
        </MenuItem>
    );
}

export default PopupMenuItem;