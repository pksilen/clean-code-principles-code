import { connect } from "react-redux";
import { useEffect } from "react";
import { controller, ActionDispatchers, State } from "./todosController";

type Props = ActionDispatchers & State;

function TodosListView({ toggleTodoDone, startFetchTodos, todos }: Props) {
  useEffect(() => {
    startFetchTodos();
  }, [startFetchTodos]);

  const todoElements = todos.map(({ id, name, isDone }) => (
    <li key={id}>
      {id}&nbsp;
      {name}&nbsp;
      {isDone ? undefined : (
        <button onClick={() => toggleTodoDone(id)}>Mark done</button>
      )}
    </li>
  ));

  return <ul>{todoElements}</ul>;
}

export default connect(controller.getState, () =>
  controller.getActionDispatchers()
)(TodosListView);
