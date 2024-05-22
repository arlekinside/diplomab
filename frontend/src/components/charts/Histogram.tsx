import React from 'react';
import {Bar} from 'react-chartjs-2';
import {
    Chart as ChartJS,
    BarElement,
    CategoryScale,
    LinearScale,
    Tooltip,
    Legend,
    ChartOptions,
    ChartData,
} from 'chart.js';

ChartJS.register(
    BarElement,
    CategoryScale,
    LinearScale,
    Tooltip,
    Legend
);

function Histogram(props: { data: ChartData<'bar'> }) {

    const options: ChartOptions<'bar'> = {
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: (value: number | string) => `$${value}`,
                },
            },
        },
        responsive: true,
        plugins: {
            legend: {
                display: true,
                position: 'top',
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
        }
    };


    return (
        <div style={{width: '100%'}}>
            <Bar data={props.data} options={options}/>
        </div>);
}

export default Histogram;
