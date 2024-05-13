import React from "react";
import {Button, Menu, MenuItem, MenuItemClassKey} from "@mui/material";
import PopupState, {bindMenu, bindTrigger} from "material-ui-popup-state";
import NavigationButton from "./NavigationButton";

function PopupMenu(props: { children: any, label: string }) {

    return (
        <PopupState variant="popover" disableAutoFocus>
            {(popupState) => (
                <React.Fragment>
                    <NavigationButton popupTrigger={bindTrigger(popupState)}>
                        {props.label}
                    </NavigationButton>
                    {/*<Button variant='text' color='primary' {} style={{fontSize: '32px'}}>*/}
                    {/*</Button>*/}
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