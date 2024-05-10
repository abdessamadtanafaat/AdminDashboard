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
      tooltips: {
        callbacks: {
            label: function(tooltipItem, data) {
                var allData = data.datasets[tooltipItem.datasetIndex].data;
                var tooltipLabel = data.labels[tooltipItem.index];
                var tooltipData = allData[tooltipItem.index];
                var total = 0;
                for (var i in allData) {
                    total += parseFloat(allData[i]);
                }
                var tooltipPercentage = Math.round((tooltipData / total) * 100);
                return tooltipLabel + ': ' + tooltipData + ' (' + tooltipPercentage + '%)';
            }
        }
    }
    };
  return (
    <div className="gird place-content-center items bg-base-200 ">
      
      
      <Doughnut data={chartData} options={chartOptions}
        />
    
    </div>
  )
}

export default DoughnutChart
