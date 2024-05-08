import {Doughnut} from 'react-chartjs-2'
import { Chart as ChartJS } from 'chart.js';
import 'chart.js/auto';
const DoughnutChart = ({chart}) => {
    const labels = Object.keys(chart);
    const data = Object.values(chart);
    console.log(labels)
    console.log(data);
    const chartData = {
      labels: labels,
      datasets: [
        {
          label: 'Businesses',
          data: data
        },
      ],
    };
  
    const chartOptions = {
      scales: {
        y: {
          beginAtZero: true,
        },
      },
    };
  return (
    <div className="gird place-content-center items bg-base-200 ">
      
      
      <Doughnut data={chartData} options={chartOptions}
        />
    
    </div>
  )
}

export default DoughnutChart
