import React from "react";
import {Provider} from "react-redux";
import store from './store';
import TodosView from "./TodosView";
import TableRowTodoView from "./TableRowTodoView";

function App() {
  return (
    <div>
      <Provider store={store}>
          <TodosView TodoView={TableRowTodoView} />
      </Provider>
    </div>
  );
}

export default App;
