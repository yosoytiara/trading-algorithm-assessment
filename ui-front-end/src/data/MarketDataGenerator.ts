import { buildDataColumnMap, Table } from "@vuu-ui/vuu-data-test";
import { VuuRange } from "@vuu-ui/vuu-protocol-types";
import { generateMarketDepth, nextRandomDouble, random } from "./data-utils";
import { TableSchema } from "@vuu-ui/vuu-data-types";

interface UpdateGenerator {
  setTable: (table: Table) => void;
  setRange: (range: VuuRange) => void;
}

const getNewValue = (value: number) => {
  const multiplier = random(0, 100) / 1000;
  const direction = random(0, 10) >= 5 ? 1 : -1;
  return value + value * multiplier * direction;
};

export class MarketDataGenerator implements UpdateGenerator {
  private table: Table | undefined;
  private range: VuuRange | undefined;
  private updating = false;
  private timer: number | undefined;
  #tickingColumns: { [key: string]: number };
  #keyIdx: number;

  constructor(schema: TableSchema) {
    const {
      [schema.key]: keyIdx,
      bid,
      bidQuantity,
      offer,
      offerQuantity,
    } = buildDataColumnMap({ prices: schema }, "prices");

    this.#keyIdx = keyIdx;
    this.#tickingColumns = {
      bid,
      bidQuantity,
      offer,
      offerQuantity,
    };
  }

  setRange(range: VuuRange) {
    this.range = range;
    if (!this.updating && this.table) {
      this.startUpdating();
    }
  }

  setTable(table: Table) {
    this.table = table;
  }

  private startUpdating() {
    this.updating = true;
    this.update();
  }

  private stopUpdating() {
    this.updating = false;
    if (this.timer) {
      window.clearTimeout(this.timer);
      this.timer = undefined;
    }
  }

  update = () => {
    if (this.range && this.table) {
      const updatedMarketData = generateMarketDepth("VOD.L");
      for (const updatedRow of updatedMarketData) {
        this.table.updateRow(updatedRow);
      }
    }

    if (this.updating) {
      this.timer = window.setTimeout(this.update, 250);
    }
  };
}
