import { Line } from 'react-chartjs-2';
import {formatDate} from '../utils'
const CreationLine = ({ businessData, campaignData }) => {
  const businessLabels = Object.keys(businessData);
  const businessValues = Object.values(businessData);

  const campaignLabels = Object.keys(campaignData);
  const campaignValues = Object.values(campaignData);

  const allLabels = [...new Set([...businessLabels, ...campaignLabels])];
  const businessDataset = allLabels.map(label => businessData[label] || 0);
  const campaignDataset = allLabels.map(label => campaignData[label] || 0);
  const chartData = {
    labels: allLabels,
    datasets: [
      {
        label: 'Businesses Created',
        data: businessDataset,
        backgroundColor: 'green',
        borderColor: 'green'
      },
      {
        label: 'Campaigns Created',
        data: campaignDataset,
        backgroundColor: 'blue',
        borderColor: 'blue'
      }
    ]
  };

  // Configure chart options
  const chartOptions = {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };

  return (
    <div className="grid place-content-center items bg-center">
      <Line data={chartData} options={chartOptions} />
    </div>
  );
};

export default CreationLine;
