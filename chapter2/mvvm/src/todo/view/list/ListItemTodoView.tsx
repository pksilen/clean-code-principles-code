import { TodosViewProps } from "../TodosViewProps";

export default function ListItemTodoView({
  toggleTodoDone,
  todo: { id, name, isDone },
}: TodosViewProps) {
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
