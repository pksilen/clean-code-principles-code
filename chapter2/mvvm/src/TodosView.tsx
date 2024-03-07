import { useEffect } from "react";
import { connect } from "react-redux";
import ListItemTodoView from "./ListItemTodoView";
import TableRowTodoView from "./TableRowTodoView";
import { controller, ActionDispatchers, State } from "./todosController";

type Props = ActionDispatchers &
  State & {
    TodoView: typeof ListItemTodoView | typeof TableRowTodoView;
  };

function TodosView({
  toggleTodoDone,
  startFetchTodos,
  todos
}: Props) {
  useEffect(() => {
    startFetchTodos();
      console.log("Jee");
  }, [startFetchTodos]);

  const todoViews = todos.map((todo) => (
    <TableRowTodoView key={todo.id} todo={todo} toggleTodoDone={toggleTodoDone} />
  ));

  return (
    <div>
      <table>
        <tbody>{todoViews}</tbody>
      </table>
    </div>
  );
}

export default connect(controller.getState, () =>
  controller.getActionDispatchers()
)(TodosView);
