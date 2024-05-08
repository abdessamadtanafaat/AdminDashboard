import { Line } from 'react-chartjs-2';
import {formatDate} from '../utils'
import colors from "tailwindcss/colors";

const CreationLine = ({ businessData, campaignData }) => {
  const businessLabels = Object.keys(businessData);

  const campaignLabels = Object.keys(campaignData);
 

  const allLabels = [...new Set([...businessLabels, ...campaignLabels])];
  const businessDataset = allLabels.map(label => businessData[label] || 0);
  const campaignDataset = allLabels.map(label => campaignData[label] || 0);
  const chartData = {
    labels: allLabels,
    datasets: [
      {
        label: 'Businesses Created',
        data: businessDataset
      },
      {
        label: 'Campaigns Created',
        data: campaignDataset
      }
    ]
  };
  const chartOptions = {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };

  return (
    <div className="grid place-content-center items  rounded-md ">
      <Line data={chartData} options={chartOptions} />
    </div>
  );
};

export default CreationLine;
