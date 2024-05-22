import React from 'react';
import { Pie } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend,
    ChartOptions,
    ChartData,
} from 'chart.js';

ChartJS.register(
    ArcElement,
    Tooltip,
    Legend
);

function PieChart(props: {data: ChartData<'pie'>}) {

    const options: ChartOptions<'pie'> = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: true,
                position: 'bottom',
            },
            tooltip: {
                callbacks: {
                    label: (context: any) => {
                        let label = context.label || '';
                        if (label) {
                            label += ': ';
                        }
                        if (context.raw !== null) {
                            label += `$${context.raw}`;
                        }
                        return label;
                    },
                },
            },
        },
    };

    return (
        <div style={{ width: '100%', height: '400px' }}>
            <Pie data={props.data} options={options}/>
        </div>
    );
}

export default PieChart;