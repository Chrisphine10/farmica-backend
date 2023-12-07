import { IStyle } from 'app/shared/model/style.model';

export interface IStyleReport {
  id?: number;
  createdAt?: string;
  totalStyleInWarehouse?: number | null;
  totalStyleInSales?: number | null;
  totalStyleInRework?: number | null;
  totalStyleInPacking?: number | null;
  totalStyle?: number | null;
  style?: IStyle;
}

export const defaultValue: Readonly<IStyleReport> = {};
