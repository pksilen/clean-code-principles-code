import React from "react";
import ReactDOM from "react-dom/client";
import App from "./app/App";
import diContainer from "./common/di/diContainer";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

diContainer.configure().then(() => {
  root.render(<App />);
});
