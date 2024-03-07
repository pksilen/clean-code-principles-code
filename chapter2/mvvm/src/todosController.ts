import store from "./store";
import { AppState } from "./AppState";
import ToggleDoneTodoAction from "./ToggleDoneTodoAction";
import StartFetchTodosAction from "./StartFetchTodosAction";
import Controller from "./Controller";

class TodosController extends Controller {
  getState(appState: AppState) {
    return {
      todos: appState.todosState.todos,
    };
  }

  getActionDispatchers() {
    return {
      toggleTodoDone: (id: number) =>
        this.dispatch(new ToggleDoneTodoAction(id)),

      startFetchTodos: () => {
        this.dispatch(new StartFetchTodosAction());
      }
    };
  }
}

export const controller = new TodosController(store.dispatch);
export type State = ReturnType<typeof controller.getState>;

export type ActionDispatchers = ReturnType<
  typeof controller.getActionDispatchers
>;

