import { TodoViewProps } from "./TodoViewProps";

export default function TableRowTodoView({
  toggleTodoDone,
  todo: { id, name, isDone },
}: TodoViewProps) {
  return (
    <tr>
      <td>{id}</td>
      <td>{name}</td>
      <td>
        <input type="checkbox" checked={isDone} onChange={() => toggleTodoDone(id)} />
      </td>
    </tr>
  );
}
