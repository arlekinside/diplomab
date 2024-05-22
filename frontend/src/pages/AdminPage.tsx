import React, {useEffect, useState} from "react";
import Page from "../components/layout/Page";
import {useNotification} from "../components/NotificationProvider";
import {Button, IconButton, Table, TableBody, TableCell, TableHead, TableRow} from "@mui/material";
import {SchedulerTypeEnum} from "../dto/SchedulerTypeEnum";
import Params from "../Params";
import ChipLabel from "../components/text/ChipLabel";
import SchedulerDTO from "../dto/SchedulerDTO";
import SchedulerLogDTO from "../dto/SchedulerLogDTO";

interface Data {
    correctness: number;
    usersCount: number;
    health: boolean
}

function AdminPage() {

    const {showNotification} = useNotification();

    const [logs, setLogs] = useState<SchedulerLogDTO[]>();
    const [counter, setCounter] = useState<number>(0);

    const trigger = (type: SchedulerTypeEnum) => {
        let body: SchedulerDTO = {
            type: type
        };
        fetch(Params.fetch.scheduler.base, {
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
            setTimeout(() => {
                setCounter(counter + 1);
            }, 5000)
            return;
        }).catch(e => {
            showNotification(`Error connecting to the server`, 'error');
        });
    }

    useEffect(() => {
        fetch(Params.fetch.scheduler.logs, {
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            redirect: "error",
        }).then(async res => {
            if (!res.ok) {
                let json = await res.json();
                showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                throw new Error();
            }
            return res.json();
        }).then((json: SchedulerLogDTO[]) => {
            setLogs(json);
        }).catch(e => {
            showNotification(`Error connecting to the server`, 'error');
        });
    }, [counter,]);

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
                <div style={{
                    display: 'flex',
                    flexDirection: 'row',
                    flexWrap: 'wrap',
                    justifyContent: 'center',
                    width: '100%'
                }}>
                    <Button size={"large"} color='secondary' variant='outlined' style={{margin: '30px'}}
                            onClick={() => trigger(SchedulerTypeEnum.MF_DAILY)}>Trigger MoneyFlow Daily</Button>
                    <Button size={"large"} color='secondary' variant='outlined' style={{margin: '30px'}}
                            onClick={() => trigger(SchedulerTypeEnum.MF_MONTHLY)}>Trigger MoneyFlow Monthly</Button>
                    <Button size={"large"} color='secondary' variant='outlined' style={{margin: '30px'}}
                            onClick={() => trigger(SchedulerTypeEnum.SAVING_MONTHLY)}>Trigger Savings Monthly</Button>
                </div>
                <br/>
                <ChipLabel>Scheduler history</ChipLabel>
                <Table sx={{width: "70%", minWidth: "500px", margin: "30px"}}>
                    <TableHead>
                        <TableRow>
                            <TableCell>Success</TableCell>
                            <TableCell>Processed</TableCell>
                            <TableCell>Type</TableCell>
                            <TableCell align="right">Date</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {logs?.map(row => (
                            <TableRow key={row.id}>
                                <TableCell>
                                    <span style={{color: row.success ? "green" : "red"}}>
                                        {String(row.success)}
                                    </span>
                                </TableCell>
                                <TableCell>{String(row.processedNum)}</TableCell>
                                <TableCell>{row.type}</TableCell>
                                <TableCell align="right" style={{wordBreak: "keep-all"}}>
                                    {String(row.dateCreated)}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </Page>
    );
}

export default AdminPage;