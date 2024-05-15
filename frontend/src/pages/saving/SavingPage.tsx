import Page from "../../components/layout/Page";
import {useEffect, useState} from "react";
import {useNotification} from "../../components/NotificationProvider";
import Params from "../../Params";
import {Button, Card, CardActions, CardContent, IconButton, Typography} from "@mui/material";
import SavingDTO from "../../dto/SavingDTO";
import ClearIcon from "@mui/icons-material/Clear";

function SavingPage() {

    const {showNotification} = useNotification();
    const [savings, setSavings] = useState<SavingDTO[]>();

    const doDelete = (id?: number) => {
        fetch(`${Params.fetch.savings}/${id}`, {
            method: 'DELETE'
        }).then(res => {
            if (!res.ok) {
                showNotification('Error performing delete operation', 'warning');
                return;
            }
            setSavings(savings?.filter((s) => s.id !== id))
            showNotification('Entity deleted', 'success');
        }).catch(e => {
            showNotification('Error connecting to the server');
        })
    }

    const doFinish = (saving: SavingDTO) => {
        saving.finished = true;
        fetch(`${Params.fetch.savings}/${saving.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;utf-8'
            },
            body: JSON.stringify(saving)
        }).then(res => {
            if (!res.ok) {
                showNotification('Error performing update operation', 'warning');
                return;
            }
            let newSavings = savings?.map(sav => {
                if (sav.id == saving.id) {
                    sav.finished = true;
                }
                return sav;
            });
            setSavings(newSavings);
            showNotification('Entity update', 'success');
        }).catch(e => {
            showNotification('Error connecting to the server');
        })
    }

    useEffect(() => {
        fetch(Params.fetch.savings, {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            redirect: 'error',
        }).then(res => {
            if (!res.ok) {
                showNotification(`Got error response ${res.status} from server`, 'warning');
                throw new Error();
            }
            return res.json();
        }).then(json => {
            setSavings(json);
        }).catch(e => {
            showNotification('Got an unexpected error while reading MoneyFlow');
        })
    }, []);

    return (
        <Page>
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                flexWrap: 'wrap',
                width: '100%',
                minWidth: '1000px'
            }}>
                {savings?.map(sav =>
                    <Card sx={{minWidth: 275}} style={{margin: '10px'}}>
                        <CardContent>
                            <Typography sx={{fontSize: 14}} color="text.secondary" gutterBottom>
                                {sav.finished ? "Finished" : sav.monthlyPercent + "%"}
                            </Typography>
                            <Typography variant="h5" component="div">
                                {sav.name}
                            </Typography>
                            <Typography sx={{mb: 1.5}} color="text.secondary">
                                Target: {sav.target.amount} {sav.target.currency}
                            </Typography>
                            <Typography variant="body2">
                                Saved: {sav?.money?.amount} {sav?.money?.currency}
                                <br />
                                Created: {sav.dateCreated}
                            </Typography>
                        </CardContent>
                        {!sav.finished &&
                            <CardActions style={{display: 'flex', justifyContent: 'space-between'}}>
                                <Button size="small" onClick={() => doFinish(sav)}>
                                    Finish
                                </Button>
                                <IconButton onClick={() => doDelete(sav.id)}>
                                    <ClearIcon/>
                                </IconButton>
                            </CardActions>
                        }
                        {sav.finished && <CardActions style={{display: 'flex', justifyContent: 'space-between'}} />}
                    </Card>
                )}
            </div>
        </Page>
    );
}

export default SavingPage;