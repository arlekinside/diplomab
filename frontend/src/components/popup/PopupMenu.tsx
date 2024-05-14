import React from "react";
import {Button, Menu, MenuItem, MenuItemClassKey} from "@mui/material";
import PopupState, {bindMenu, bindTrigger} from "material-ui-popup-state";
import NavigationButton from "../NavigationButton";

function PopupMenu(props: { children: any, label: string, style?: React.CSSProperties }) {

    return (
        <PopupState variant="popover" disableAutoFocus>
            {(popupState) => (
                <React.Fragment>
                    <NavigationButton popupTrigger={bindTrigger(popupState)} style={props.style}>
                        {props.label}
                    </NavigationButton>
                    <Menu {...bindMenu(popupState)}
                          anchorOrigin={{
                              vertical: 'bottom',
                              horizontal: 'left',
                          }}>
                        {props.children}
                    </Menu>
                </React.Fragment>
            )}
        </PopupState>
    );
}

export default PopupMenu;