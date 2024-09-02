import { registerComponent } from "@vuu-ui/vuu-utils";
import { MarketDepthFeature } from "./components/market-depth";
import { PricesTable } from "./components/prices-table";

registerComponent("MarketDepthFeature", MarketDepthFeature, "view");
registerComponent("PricesTable", PricesTable, "view");

export const layoutJSON = {
  type: "Flexbox",
  props: {
    splitterSize: false,
    style: {
      flexDirection: "column",
    },
  },
  children: [
    {
      type: "View",
      props: {
        resizeable: true,
        style: {
          flexBasis: 0,
          flexGrow: 1,
          flexShrink: 1,
          height: "auto",
          width: "auto",
        },
      },
      children: [{ type: "PricesTable" }],
    },
    {
      type: "View",
      props: {
        resizeable: true,
        style: {
          flexBasis: 0,
          flexGrow: 1,
          flexShrink: 1,
          height: "auto",
          width: "auto",
        },
      },
      children: [{ type: "MarketDepthFeature" }],
    },
  ],
};
