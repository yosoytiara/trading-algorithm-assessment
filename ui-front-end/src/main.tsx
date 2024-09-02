import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { TestDataProvider } from "./data/TestDataProvider.tsx";

import "@vuu-ui/vuu-icons/index.css";
import "@vuu-ui/vuu-theme/index.css";
import "./index.css";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <TestDataProvider>
      <App />
    </TestDataProvider>
  </React.StrictMode>
);
