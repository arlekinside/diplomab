import React from 'react';
import './css/App.css';
import {useRoutes} from "hookrouter/dist/routes";
import Routes from "./routes/Routes";
import {createTheme, ThemeProvider} from "@mui/material";
import Params from "./Params";
import NotFoundPage from "./pages/NotFoundPage";
import NotificationProvider from "./components/NotificationProvider";

const theme = createTheme({
    palette: {
        primary: {
            main: Params.colors.primary
        },
        secondary: {
            main: Params.colors.secondary
        },
        text: {
            primary: Params.colors.primary,
            secondary: Params.colors.secondary,
        }
    }
});

function App() {
    const route = useRoutes(Routes);
    return (
        <ThemeProvider theme={theme}>
            <NotificationProvider>
                {route || <NotFoundPage/>}
            </NotificationProvider>
        </ThemeProvider>
    )
}

export default App;
