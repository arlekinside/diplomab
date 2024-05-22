import {LinearProgress} from "@mui/material";
import {useEffect} from "react";
import Params from "../Params";
import {useNotification} from "../components/NotificationProvider";
import UserDTO from "../dto/UserDTO";
import {UserRoleEnum} from "../dto/UserRoleEnum";

function LandingPage() {

    const {showNotification} = useNotification();


    useEffect(() => {
        fetch(Params.fetch.user.current, {
                headers: {
                    "Content-Type": "application/json;charset=UTF-8"
                },
                redirect: "error"
            }
        ).then(async res => {
            if (!res.ok) {
                let json = await res.json();
                showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                throw new Error();
            }
            return res.json();
        }).then((json: UserDTO) => {
            if (json.role == UserRoleEnum.ADMIN) {
                window.location.href = Params.path.admin;
                return;
            }
            window.location.href = Params.path.dashboard;
            return;
        }).catch(e => {
            showNotification(`Got unexpected response from server`, 'error');
        });
    });

    return (
        <LinearProgress color='secondary'/>
    )
}

export default LandingPage;