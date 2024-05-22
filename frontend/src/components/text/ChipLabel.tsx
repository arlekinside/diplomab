import {Chip} from "@mui/material";
import React from "react";

function ChipLabel(props: {children: React.ReactNode}) {

    return(
        <Chip label={props.children} size="medium" style={{fontSize: "1.5vw", padding: "30px", marginBottom: "30px"}} color="secondary"/>
    );
}

export default ChipLabel;