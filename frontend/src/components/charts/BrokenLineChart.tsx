import React from 'react';
import { Line } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Legend,
    ChartOptions,
    ChartData,
} from 'chart.js';
import Params from "../../Params";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Legend
);

function BrokenLineChart(props: {data: ChartData<'line'>}) {

    const options: ChartOptions<'line'> = {
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: (value: number | string) => `$${value}`,
                },
            },
        },
        plugins: {
            legend: {
                display: true,
                position: 'bottom',
            },
            tooltip: {
                callbacks: {
                    label: (context: any) => {
                        let label = context.dataset.label || '';
                        if (label) {
                            label += ': ';
                        }
                        if (context.parsed.y !== null) {
                            label += `$${context.parsed.y}`;
                        }
                        return label;
                    },
                },
            },
        },
    };

    return (
        <div style={{ width: '100%', height: '400px' }}>
            <Line data={props.data} options={options} />
        </div>
    );
};

export default BrokenLineChart;
