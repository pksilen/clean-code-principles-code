import React from "react";
import {Provider} from "react-redux";
import store from '../common/model/store';
import TodosView from "../todo/view/TodosView";
import ListItemTodoView from "../todo/view/list/ListItemTodoView";
import TableRowTodoView from "../todo/view/table/TableRowTodoView";

function App() {
  return (
    <div>
      <Provider store={store}>
          { /*You can change the TodoView to TableRowTodoView */ }
          <TodosView TodoView={TableRowTodoView} />
      </Provider>
    </div>
  );
}

export default App;
