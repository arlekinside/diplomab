import Page from "../../components/layout/Page";
import {useNotification} from "../../components/NotificationProvider";
import {useEffect, useState} from "react";
import MoneyFlowDTO from "../../dto/MoneyFlowDTO";
import Params from "../../Params";
import Label from "../../components/text/Label";
import {IconButton, Table, TableBody, TableCell, TableHead, TableRow} from "@mui/material";
import ClearIcon from "@mui/icons-material/Clear";
import ChipLabel from "../../components/text/ChipLabel";
import ErrorDTO from "../../dto/ErrorDTO";

function RecurringMoneyFlowPage() {
    const {showNotification} = useNotification();
    const [incomes, setIncomes] = useState<MoneyFlowDTO[]>();
    const [expenses, setExpenses] = useState<MoneyFlowDTO[]>();

    const doDelete = (id?: number, income = true) => {
        fetch(`${income ? Params.fetch.mf.recurring.incomes : Params.fetch.mf.recurring.expenses}/${id}`, {
            method: 'DELETE'
        }).then(res => {
            if (!res.ok) {
                showNotification('Error performing delete operation', 'warning');
                return;
            }
            if (income) {
                setIncomes(incomes?.filter((m) => m.id !== id))
            } else {
                setExpenses(expenses?.filter((m) => m.id !== id))
            }
            showNotification('Entity deleted', 'success');
        }).catch(e => {
            showNotification('Error connecting to the server');
        })
    }

    useEffect(() => {
        fetch(Params.fetch.mf.recurring.incomes, {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            redirect: 'error',
        }).then(async res => {
            if (!res.ok) {
                let json : ErrorDTO = await res.json();
                showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                throw new Error();
            }
            return res.json();
        }).then(json => {
            setIncomes(json);
            fetch(Params.fetch.mf.recurring.expenses, {
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                redirect: 'error',
            }).then(async res => {
                if (!res.ok) {
                    let json : ErrorDTO = await res.json();
                    showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                    throw new Error();
                }
                return res.json();
            }).then(json => {
                setExpenses(json);
            }).catch(e => {
                showNotification('Got an unexpected error while reading MoneyFlow');
            })
        }).catch(e => {
            showNotification('Got an unexpected error while reading MoneyFlow');
        })
    }, []);

    return (
        <Page>
            <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-around', width: '100%', minWidth: '1000px'}}>
                <div style={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'flex-start',
                    margin: '0 5px 0 10px',
                    minWidth: '500px'
                }}>
                    <ChipLabel>Incomes</ChipLabel>
                    <Table sx={{width: "40%", minWidth: "300px", margin: "30px"}}>
                        <TableHead>
                            <TableRow>
                                <TableCell>Name</TableCell>
                                <TableCell>Amount</TableCell>
                                <TableCell>Cycle</TableCell>
                                <TableCell>Date</TableCell>
                                <TableCell align="right"></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {incomes?.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell>{row.name}</TableCell>
                                    <TableCell>{String(row.money?.amount) + row.money?.currency}</TableCell>
                                    <TableCell>{String(row.cycle)}</TableCell>
                                    <TableCell>{String(row.dateCreated)}</TableCell>
                                    <TableCell align="right">
                                        <IconButton onClick={() => doDelete(row.id)}>
                                            <ClearIcon/>
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
                <div style={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'flex-start',
                    margin: '0 5px 0 10px',
                    minWidth: '500px'
                }}>
                    <ChipLabel>Expenses</ChipLabel>
                    <Table sx={{width: "40%", minWidth: "300px", margin: "30px"}}>
                        <TableHead>
                            <TableRow>
                                <TableCell>Name</TableCell>
                                <TableCell>Amount</TableCell>
                                <TableCell>Cycle</TableCell>
                                <TableCell>Date</TableCell>
                                <TableCell align="right"></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {expenses?.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell>{row.name}</TableCell>
                                    <TableCell>{String(row.money?.amount) + row.money?.currency}</TableCell>
                                    <TableCell>{String(row.cycle)}</TableCell>
                                    <TableCell>{String(row.dateCreated)}</TableCell>
                                    <TableCell align="right">
                                        <IconButton onClick={() => doDelete(row.id, false)}>
                                            <ClearIcon/>
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
            </div>
        </Page>
    );
}

export default RecurringMoneyFlowPage;