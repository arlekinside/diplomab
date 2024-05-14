import Page from "../../components/layout/Page";
import Label from "../../components/text/Label";
import {Button, Checkbox, FormControlLabel, Radio, RadioGroup, TextField} from "@mui/material";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {useNotification} from "../../components/NotificationProvider";
import Params from "../../Params";
import {RecurringCycleEnum} from "../../dto/RecurringCycleEnum";
import MoneyFlowDTO from "../../dto/MoneyFlowDTO";

interface IFormInput {
    name: string
    amount: number
    cycle: RecurringCycleEnum
}

function NewMoneyFlowPage() {

    const {control, handleSubmit, formState: {errors}} = useForm<IFormInput>({});
    const {showNotification} = useNotification();

    const getUrl = (amount: number, cycle: RecurringCycleEnum): string => {
        let recurring = cycle != RecurringCycleEnum.NONE;
        if (amount > 0) {
            if (recurring) {
                return Params.fetch.mf.recurring.incomes;
            }
            return Params.fetch.mf.incomes;
        }
        if (recurring) {
            return Params.fetch.mf.recurring.expenses;
        }
        return Params.fetch.mf.expenses;
    }

    const onSubmit: SubmitHandler<IFormInput> = (data) => {
        let dto : MoneyFlowDTO = {
            money: {
                amount: data.amount,
                currency: 'USD'
            },
            cycle: data.cycle
        }

        fetch(getUrl(data.amount, data.cycle), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(dto)
        }).then(res => {
            if (!res.ok) {
                showNotification(`Got unexpected response ${res.status} from server`);
                return;
            }
            showNotification('MoneyFlow created', 'success')
            return;
        }).catch(e => {
            showNotification('Got an unexpected error while creating MoneyFlow');
        })
    }

    return (
        <Page>
            <Label>MoneyFlow - NEW</Label>
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
                    width: "30%",
                    minWidth: "400px"
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
                    <Controller
                        name="cycle"
                        control={control}
                        defaultValue={RecurringCycleEnum.NONE}
                        render={({field}) => (
                            <RadioGroup row value={field.value} onChange={(e) => field.onChange(e.target.value)}>
                                <FormControlLabel label="None" control={<Radio/>} value={RecurringCycleEnum.NONE}/>
                                <FormControlLabel label="Daily" control={<Radio/>} value={RecurringCycleEnum.DAILY}/>
                                <FormControlLabel label="Monthly" control={<Radio/>} value={RecurringCycleEnum.MONTHLY}/>
                            </RadioGroup>
                        )}
                    />
                    <Button variant="contained" color="primary" type={"submit"} size={"large"}>Submit</Button>
                </form>
            </div>
        </Page>
    );
}


export default NewMoneyFlowPage;