import { Todo } from "../model/state/Todo";
import TodoService from "./TodoService";

export default class FakeTodoService implements TodoService {
  getTodos(): Promise<Todo[]> {
    return Promise.resolve([]);
  }
}
