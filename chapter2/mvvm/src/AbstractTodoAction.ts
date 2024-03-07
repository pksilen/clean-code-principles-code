import { TodoState } from "./TodoState";
import AbstractUndoableAction from "./AbstractUndoableAction";

export default abstract class AbstractTodoAction extends AbstractUndoableAction<TodoState> {}
