import React from 'react';
import './css/App.css';
import {useRoutes} from "hookrouter/dist/routes";
import Routes from "./routes/Routes";
import {createTheme, ThemeProvider} from "@mui/material";
import Params from "./Params";

const theme = createTheme({
    palette: {
        primary: {
            main: Params.colors.main
        },
        secondary: {
            main: Params.colors.secondary
        },
        text: {
            primary: Params.colors.main,
            secondary: Params.colors.secondary,
        }
    }
});

function App() {
    const route = useRoutes(Routes);
    return (
        <ThemeProvider theme={theme}>
            {route}
        </ThemeProvider>
    )
}

export default App;
