import React from "react";
import Page from "../components/layout/Page";
import {useNotification} from "../components/NotificationProvider";
import {Button} from "@mui/material";
import {SchedulerTypeEnum} from "../dto/SchedulerTypeEnum";
import Params from "../Params";
import ChipLabel from "../components/text/ChipLabel";
import SchedulerDTO from "../dto/SchedulerDTO";

interface Data {
    correctness: number;
    usersCount: number;
    health: boolean
}

function AdminPage() {

    const {showNotification} = useNotification();

    const trigger = (type: SchedulerTypeEnum) => {
        let body : SchedulerDTO = {
            type: type
        };
        fetch(Params.fetch.scheduler, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            redirect: "error",
            body: JSON.stringify(body)
        }).then(async res => {
            if (!res.ok) {
                let json = await res.json();
                showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                throw new Error();
            }
            showNotification(`Job ${type} triggered`, 'info');
            return;
        }).catch(e => {
            showNotification(`Error connecting to the server`, 'error');
        });
    }

    return (
        <Page admin>
            <ChipLabel>
                Admin page
            </ChipLabel>
            <div style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                width: '100%',
                height: '100%'
            }}>
                <Button size={"large"} color='secondary' variant='contained' style={{margin: '30px'}}
                        onClick={() => trigger(SchedulerTypeEnum.MF_DAILY)}>Trigger MoneyFlow Daily</Button>
                <Button size={"large"} color='secondary' variant='contained' style={{margin: '30px'}}
                        onClick={() => trigger(SchedulerTypeEnum.MF_MONTHLY)}>Trigger MoneyFlow Monthly</Button>
                <Button size={"large"} color='secondary' variant='contained' style={{margin: '30px'}}
                        onClick={() => trigger(SchedulerTypeEnum.SAVING_MONTHLY)}>Trigger Savings Monthly</Button>
            </div>
        </Page>
    );
}

export default AdminPage;