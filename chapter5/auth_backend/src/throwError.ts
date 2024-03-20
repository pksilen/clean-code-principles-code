export default function throwError(Error: { new(): Error }): never {
  throw new Error();
}
