import './MarketDepthPanel.css';
import { PriceCell } from './PriceCell';
import { MarketDepthRow } from './useMarketDepthData';

interface MarketDepthPanelProps {
  data: MarketDepthRow[];
}

export const MarketDepthPanel = (props: MarketDepthPanelProps) => {
  console.log({ props });
  const { data } = props;
  //   return <table>
  //   </table>;
  return (
    <table className='MarketDepthPanel'>
      <thead>
        <tr>
          <th>Bid</th>
          <th>Bid Quantity</th>
          <th>Offer</th>
          <th>Offer Quantity</th>
        </tr>
      </thead>
      <tbody>
        {data.map((row, index) => (
          <tr key={index}>
            {/* <td>{row.level}</td> */}
            <td>{row.bidQuantity}</td>
            <PriceCell price={row.bid} />
        <PriceCell price={row.offer} /> 
            <td>{row.offerQuantity}</td> 
          </tr>
        ))}
      </tbody>
    </table>
  );
};
