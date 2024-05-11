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
        enabled: false
    },
    plugins: {
        datalabels: {
            formatter: (value, categories) => {

                let sum = 0;
                let dataArr = categories.chart.data.datasets[0].data;
                dataArr.map(data => {
                    sum += data;
                });
                let percentage = (value*100 / sum).toFixed(2)+"%";
                return percentage;


            },
            color: '#fff',
        }
    }
    }

  return (
    <div className="gird place-content-center items bg-base-200 ">
      
      
      <Doughnut data={chartData} options={chartOptions}
        />
    
    </div>
  )
}

export default DoughnutChart
