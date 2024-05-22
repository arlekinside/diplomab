import Page from "../../components/layout/Page";
import Label from "../../components/text/Label";
import {Button, TextField} from "@mui/material";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {useNotification} from "../../components/NotificationProvider";
import SavingDTO from "../../dto/SavingDTO";
import Params from "../../Params";
import ChipLabel from "../../components/text/ChipLabel";
import ErrorDTO from "../../dto/ErrorDTO";

interface IFormInput {
    name: string
    initial?: number
    target: number
    monthlyPercent: number
}

function NewSavingPage() {

    const {control, handleSubmit, formState: {errors}} = useForm<IFormInput>();
    const {showNotification} = useNotification();

    const onSubmit: SubmitHandler<IFormInput> = (data) => {
        let dto : SavingDTO = {
            name: data.name,
            money: {
                amount: Math.abs(data.initial || 0),
                currency: 'USD'
            },
            target: {
                amount: Math.abs(data.target),
                currency: 'USD'
            },
            monthlyPercent: data.monthlyPercent
        }

        fetch(Params.fetch.savings, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            redirect: 'error',
            body: JSON.stringify(dto)
        }).then(async res => {
            if (!res.ok) {
                let json : ErrorDTO = await res.json();
                showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                return;
            }
            showNotification('Saving created. Moving to savings page', 'success')
            setTimeout(() => window.location.href = Params.path.savings.page, 3000);
        }).catch(e => {
            showNotification('Got an unexpected error while creating Saving');
        })
    }

    return (
        <Page>
            <ChipLabel>Saving - NEW</ChipLabel>
            <div style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "flex-start",
                alignItems: 'center',
                width: "100%",
                height: "50vh"
            }}>
                <form onSubmit={handleSubmit(onSubmit)} style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "flex-start",
                    alignItems: "stretch",
                    width: "20%"
                }}
                >
                    <Controller
                        control={control}
                        name="name"
                        rules={{
                            maxLength: {
                                value: 20,
                                message: 'Name should not be longer than 20 characters'
                            }
                        }}
                        render={({field}) =>
                            <TextField {...field} label="Name" variant={"outlined"} margin="normal"
                                       helperText={errors?.name?.message} required/>
                        }
                    />
                    <Controller
                        control={control}
                        name="initial"
                        rules={{
                            min: {
                                value: 0,
                                message: "Initial amount should be >=0"
                            }
                        }}
                        render={({field}) =>
                            <TextField {...field} label="Initial amount" variant={"outlined"} margin="normal" type={"number"}
                                       helperText={errors?.initial?.message}/>
                        }
                    />
                    <Controller
                        control={control}
                        name="target"
                        rules={{
                            min: {
                                value: 1,
                                message: "Target amount should be greater than 0"
                            }
                        }}
                        render={({field}) =>
                            <TextField {...field} label="Target amount" variant={"outlined"} margin="normal" type={"number"}
                                       helperText={errors?.initial?.message} required/>
                        }
                    />
                    <Controller
                        control={control}
                        name="monthlyPercent"
                        rules={{
                            min: {
                                value: 1,
                                message: "Value cannot be lover than 1%"
                            },
                            max: {
                                value: 100,
                                message: "Value cannot exceed 100%"
                            }
                        }}
                        render={({field}) =>
                            <TextField {...field} label="Monthly percent" variant={"outlined"} margin="normal" type={"number"}
                                       helperText={errors?.monthlyPercent?.message} required/>
                        }
                    />
                    <Button variant="contained" color="primary" type={"submit"} size={"large"}>Submit</Button>
                </form>
            </div>
        </Page>
    );
}


export default NewSavingPage;