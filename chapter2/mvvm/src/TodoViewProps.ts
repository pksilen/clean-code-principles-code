import { Todo } from "./Todo";

export type TodoViewProps = {
  toggleTodoDone: (id: number) => void;
  todo: Todo;
};
