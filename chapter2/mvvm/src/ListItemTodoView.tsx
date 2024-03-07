import { TodoViewProps } from "./TodoViewProps";

export default function ListItemTodoView({
  toggleTodoDone,
  todo: { id, name, isDone },
}: TodoViewProps) {
  return (
    <li>
      {id}&nbsp;
      {name}&nbsp;
      {isDone ? undefined : (
        <button onClick={() => toggleTodoDone(id)}>Mark done</button>
      )}
    </li>
  );
}
