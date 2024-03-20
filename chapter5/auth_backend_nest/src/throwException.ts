export default function throwException(message: string): never {
  throw new Error(message);
}
