import {Button} from "@mui/material";
import React from "react";

function NavigationButton(props: {children: any, href?: string, popupTrigger?: any, style?: React.CSSProperties}) {
    return (
        <Button variant='text' color='primary' style={props.style} href={props.href} {...props.popupTrigger}>
            {props.children}
        </Button>
    )
}

export default NavigationButton;