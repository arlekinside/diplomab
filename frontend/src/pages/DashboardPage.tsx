import Page from "../components/layout/Page";
import Histogram from "../components/charts/Histogram";
import PieChart from "../components/charts/PieChart";
import {ChartData} from "chart.js";
import {useEffect, useState} from "react";
import Params from "../Params";
import BrokenLineChart from "../components/charts/BrokenLineChart";
import ChipLabel from "../components/text/ChipLabel";
import {useNotification} from "../components/NotificationProvider";
import DashboardDTO from "../dto/DashboardDTO";
import {LinearProgress} from "@mui/material";
import MoneyDTO from "../dto/MoneyDTO";

function DashboardPage() {
    const {showNotification} = useNotification();

    const [fetched, setFetched] = useState<boolean>(false)

    const [budget, setBudget] = useState<MoneyDTO>();
    const [incomes, setIncomes] = useState<MoneyDTO>();
    const [expenses, setExpenses] = useState<MoneyDTO>();
    const [humanHours, setHumanHours] = useState<MoneyDTO>();

    const [budgetPieData, setBudgetPieData] = useState<ChartData<'pie'>>();
    const [savingPieData, setSavingPieData] = useState<ChartData<'pie'>>();
    const [histData, setHistData] = useState<ChartData<'bar'>>();
    const [blData, setBlData] = useState<ChartData<'line'>>();

    useEffect(() => {
        fetch(Params.fetch.dashboard, {
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            redirect: "error"
        }).then(async res => {
            if (!res.ok) {
                let json = await res.json();
                showNotification(`Got error response ${res.status} - ${json.message}`, 'warning');
                throw new Error();
            }
            return res.json();
        }).then((json: DashboardDTO) => {
            setBudget(json.budget);
            setIncomes(json.monthIncome);
            setExpenses(json.monthExpense);
            setHumanHours(json.hhRate);
            setBudgetPieData({
                labels: ['Budget', 'Savings'],
                datasets: [
                    {
                        label: 'Dataset',
                        data: [json.budget.amount, json.savingsTotal.amount],
                        backgroundColor: [
                            Params.colors.primary, // blue
                            Params.colors.secondary, // orange
                        ],
                        borderWidth: 1,
                    },
                ],
            });
            setSavingPieData({
                labels: ['Savings', 'Savings left'],
                datasets: [
                    {
                        label: 'Dataset',
                        data: [json.savingsTotal.amount, json.savingsLeft.amount],
                        backgroundColor: [
                            Params.colors.primary, // blue
                            Params.colors.secondary, // orange
                        ],
                        borderWidth: 1,
                    },
                ],
            });
            setHistData({
                labels: ['Last Month', 'Current Month', 'Next Month'],
                datasets: [
                    {
                        label: 'Incomes',
                        data: [json.lastMonth.incomes.amount, json.currentMonth.incomes.amount, json.nextMonth.incomes.amount], // values for group 1
                        backgroundColor: Params.colors.primary, // blue
                        borderWidth: 1,
                        type: 'bar',
                    },
                    {
                        label: 'Expenses',
                        data: [json.lastMonth.expenses.amount, json.currentMonth.expenses.amount, json.nextMonth.expenses.amount], // values for group 2
                        backgroundColor: Params.colors.secondary, // orange
                        borderWidth: 1,
                        type: 'bar',
                    }
                ],
            });
            setBlData({
                labels: ['Last Month', 'Current Month', 'Next Month'],
                datasets: [
                    {
                        label: 'Human hours',
                        data: [json.lastMonth.hh.amount, json.currentMonth.hh.amount, json.nextMonth.hh.amount], // values for the line
                        borderColor: Params.colors.primary, // main color
                        borderWidth: 2,
                        fill: false,
                        tension: 0.1, // Add some tension to create a "broken" appearance
                        pointRadius: 10, // Make points larger for emphasis
                        pointBackgroundColor: Params.colors.secondary, // orange
                    },
                ],
            });
            setFetched(true);
        }).catch(e => {
            showNotification(`Error connecting to the server`, 'error');
        });
    }, []);


    return (
        <Page>
            {fetched ?
                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "flex-start",
                    width: "100%",
                    height: "100%"
                }}>
                    <br/>
                    <div style={{
                        display: "flex",
                        flexDirection: "row",
                        justifyContent: "space-around",
                        width: "100%",
                        alignItems: "flex-start",
                        flexWrap: "wrap"
                    }}>
                        <ChipLabel>
                            Budget {budget?.amount} {budget?.currency || "USD"}
                        </ChipLabel>
                        <ChipLabel>
                            Monthly Income {incomes?.amount} {incomes?.currency || "USD"}
                        </ChipLabel>
                        <ChipLabel>
                            Monthly Expense {expenses?.amount} {expenses?.currency || "USD"}
                        </ChipLabel>
                        <ChipLabel>
                            HH ratio {humanHours?.amount} {humanHours?.currency || "USD"}
                        </ChipLabel>
                    </div>
                    <br/>
                    <div style={{display: "flex", flexDirection: "row", justifyContent: "space-around", width: "50%"}}>
                        {budgetPieData && <PieChart data={budgetPieData}/>}
                        {savingPieData && <PieChart data={savingPieData}/>}
                    </div>
                    <br/>
                    <div style={{width: "60%"}}>
                        {histData && <Histogram data={histData}/>}
                    </div>
                    <br/>
                    <div style={{width: "60%"}}>
                        {blData && <BrokenLineChart data={blData}/>}
                    </div>

                </div>
            : <LinearProgress color="secondary" style={{marginTop: '10px'}}/>
            }

        </Page>
    );
}

export default DashboardPage;