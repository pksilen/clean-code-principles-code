import { combineReducers, createStore } from "redux";
import createReducer from "./createReducer";
import initialTodosState from "./initialTodoState";
import AbstractTodoAction from "./AbstractTodoAction";

const rootReducer = combineReducers({
  todosState: createReducer(initialTodosState, AbstractTodoAction),
});

export default createStore(rootReducer);
