export interface IFarmicaReport {
  id?: number;
  createdAt?: string;
  totalItemsInWarehouse?: number;
  totalItemsInSales?: number;
  totalItemsInRework?: number;
  totalItemsInPacking?: number;
  totalItems?: number;
}

export const defaultValue: Readonly<IFarmicaReport> = {};
