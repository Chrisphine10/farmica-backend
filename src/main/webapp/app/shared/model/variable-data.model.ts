export interface IVariableData {
  id?: number;
  accumulation?: number | null;
  aiAccessCode?: string | null;
}

export const defaultValue: Readonly<IVariableData> = {};
