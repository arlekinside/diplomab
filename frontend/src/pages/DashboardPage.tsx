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

function DashboardPage() {
    const {showNotification} = useNotification();

    const [budget, setBudget] = useState<number>(99);
    const [incomes, setIncomes] = useState<number>(99);
    const [expenses, setExpenses] = useState<number>(99);
    const [humanHours, setHumanHours] = useState<number>(99);

    const [budgetPieData, setBudgetPieData] = useState<ChartData<'pie'>>({
            labels: ['Budget', 'Savings'],
            datasets: [
                {
                    label: 'Dataset',
                    data: [10, 20],
                    backgroundColor: [
                        Params.colors.primary, // blue
                        Params.colors.secondary, // orange
                    ],
                    borderWidth: 1,
                },
            ],
        }
    );


    const [savingPieData, setSavingPieData] = useState<ChartData<'pie'>>({
            labels: ['Savings', 'Savings left'],
            datasets: [
                {
                    label: 'Dataset',
                    data: [70, 30],
                    backgroundColor: [
                        Params.colors.primary, // blue
                        Params.colors.secondary, // orange
                    ],
                    borderWidth: 1,
                },
            ],
        }
    );

    const [histData, setHistData] = useState<ChartData<'bar'>>({
        labels: ['Incomes', 'Expenses', 'Savings'],
        datasets: [
            {
                label: 'Last Month',
                data: [5, 6, 7], // values for group 1
                backgroundColor: Params.colors.primary, // blue
                borderWidth: 1,
                type: 'bar',
            },
            {
                label: 'Current Month',
                data: [8, 9, 10], // values for group 2
                backgroundColor: Params.colors.secondary, // orange
                borderWidth: 1,
                type: 'bar',
            },
            {
                label: 'Next Month',
                data: [11, 12, 13], // values for group 3
                backgroundColor: Params.colors.thirdly, // green
                borderWidth: 1,
                type: 'bar',
            },
        ],
    });

    const [blData, setBlData] = useState<ChartData<'line'>>({
        labels: ['Point 1', 'Point 2', 'Point 3'],
        datasets: [
            {
                label: 'Broken Line',
                data: [6, 10, 12], // values for the line
                borderColor: Params.colors.primary, // main color
                borderWidth: 2,
                fill: false,
                tension: 0.1, // Add some tension to create a "broken" appearance
                pointRadius: 5, // Make points larger for emphasis
                pointBackgroundColor: Params.colors.secondary, // orange
            },
        ],
    });

    useEffect(() => {
        fetch(Params.fetch.dashboard, {
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            redirect: "error"
        }).then(req => {
            if (!req.ok) {
                showNotification("Error loading dashboard");
                throw new Error();
            }
            return req.json();
        }).then((json : DashboardDTO) => {
            setBudget(json.budget.amount);
        });
    }, []);


    return (
        <Page>
            <div style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "flex-start",
                width: "100%",
                height: "100%"
            }}>
                <br />
                <div style={{display: "flex", flexDirection: "row", justifyContent: "space-around", width: "100%", alignItems:"flex-start", flexWrap: "wrap"}}>
                    <ChipLabel>
                        Budget -${budget}
                    </ChipLabel>
                    <ChipLabel>
                        Monthly Income ${incomes}
                    </ChipLabel>
                    <ChipLabel>
                        Monthly Expense ${expenses}
                    </ChipLabel>
                    <ChipLabel>
                        HH ratio ${humanHours}
                    </ChipLabel>
                </div>
                <br />
                <div style={{display: "flex", flexDirection: "row", justifyContent: "space-around", width: "50%"}}>
                    <PieChart data={budgetPieData}/>
                    <PieChart data={savingPieData}/>
                </div>
                <br/>
                <div style={{width: "60%"}}>
                    <Histogram data={histData}/>
                </div>
                <br/>
                <div style={{width: "60%"}}>
                    <BrokenLineChart data={blData}/>
                </div>
            </div>

        </Page>
    );
}

export default DashboardPage;