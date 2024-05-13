import {Button} from "@mui/material";

function NavigationButton(props: {children: any, href?: string, popupTrigger?: any}) {
    return (
        <Button variant='text' color='primary' style={{fontSize: '32px'}} href={props.href} {...props.popupTrigger}>
            {props.children}
        </Button>
    )
}

export default NavigationButton;