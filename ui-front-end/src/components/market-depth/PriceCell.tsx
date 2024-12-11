import { useRef } from 'react';
export interface PriceCellProps {
  price: number;
}

export const PriceCell = (props: PriceCellProps) => {
  const { price } = props;
  const OldPrice = useRef(price);
  OldPrice.current = price;
  return <td>{price}</td>;
};
