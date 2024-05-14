import React from 'react';
import Snackbar from '@mui/material/Snackbar';
import Alert, {AlertColor} from '@mui/material/Alert';

function Notification(props: { open: boolean, onClose: () => void , message: any, severity: AlertColor }) {
    return (
        <Snackbar open={props.open} autoHideDuration={60000} onClose={props.onClose}>
            <Alert severity={props.severity} onClose={props.onClose}>
                {props.message}
            </Alert>
        </Snackbar>
    );
}

export default Notification;
