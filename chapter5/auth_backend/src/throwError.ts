export default function throwError(error: { new(): Error }): never {
  throw error;
}
