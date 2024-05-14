import Page from "../../components/layout/Page";
import Label from "../../components/text/Label";
import {Button, Checkbox, FormControlLabel, TextField} from "@mui/material";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {useNotification} from "../../components/NotificationProvider";

interface IFormInput {
    name: string
    amount: number
    monthlyPercent: number

}

function NewSavingPage() {

    const {control, handleSubmit, formState: {errors}} = useForm<IFormInput>();
    const {showNotification} = useNotification();

    const onSubmit: SubmitHandler<IFormInput> = (data) => {
        fetch('/savings', {
            method: 'POST'
        }).then(res => {
            if (!res.ok) {
                showNotification(`Got unexpected response ${res.status} from server`);
                return;
            }
            showNotification('Saving created', 'success')
            return res.json();
        }).then(json => {
        }).catch(e => {
            showNotification('Got an unexpected error while creating Saving');
        })
    }

    return (
        <Page>
            <Label>Saving - NEW</Label>
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
                        name="amount"
                        rules={{
                            min: {
                                value: 1,
                                message: "Initial amount should be greater than 0"
                            }
                        }}
                        render={({field}) =>
                            <TextField {...field} label="Initial amount" variant={"outlined"} margin="normal" type={"number"}
                                       helperText={errors?.amount?.message} required/>
                        }
                    />
                    <Controller
                        control={control}
                        name="monthlyPercent"
                        rules={{
                            pattern: {
                                value: /(-|)[1-9]+/,
                                message: "Amount should be a valid not 0 number"
                            }
                        }}
                        render={({field}) =>
                            <TextField {...field} label="Amount" variant={"outlined"} margin="normal" type={"number"}
                                       helperText={errors?.amount?.message} required/>
                        }
                    />
                    <Button variant="contained" color="primary" type={"submit"} size={"large"}>Submit</Button>
                </form>
            </div>
        </Page>
    );
}


export default NewSavingPage;