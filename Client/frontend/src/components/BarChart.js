import {Bar} from 'react-chartjs-2'
import { Chart as ChartJS } from 'chart.js';
import 'chart.js/auto';
const BarChart = ({chart}) => {
  const labels = Object.keys(chart);
  const data = Object.values(chart);
  console.log(labels)
  console.log(data);
  const chartData = {
    labels: labels,
    datasets: [
      {
        label: 'Number of Businesses',
        data: data,
        backgroundColor: ''
    

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
    <div className="gird place-content-center items bg-center ">
      
      
      <Bar data={chartData} options={chartOptions}
        />
    
    </div>
  );
}

export default BarChart
