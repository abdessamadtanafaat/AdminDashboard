import {Line} from 'react-chartjs-2'
import { Chart as ChartJS } from 'chart.js';
import 'chart.js/auto';
const LineChart = ({chart}) => {
  const labels = Object.keys(chart);
  const data = Object.values(chart);
  console.log(labels)
  console.log(data);
  const chartData = {
    labels: labels,
    datasets: [
      {
        label: 'Business Created ',
        data: data,
        backgroundColor: 'green'
    

      },
      {
        label: 'Campins Created ',
        data: data,
        backgroundColor: 'red',
      }

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
      
      
    <Line data={chartData} options={chartOptions}
    />
  
  </div>
  )
}

export default LineChart
