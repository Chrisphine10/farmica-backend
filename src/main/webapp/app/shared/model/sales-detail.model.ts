import { IWarehouseDetail } from 'app/shared/model/warehouse-detail.model';
import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { IStyle } from 'app/shared/model/style.model';
import { IUser } from 'app/shared/model/user.model';

export interface ISalesDetail {
  id?: number;
  uicode?: string | null;
  salesDate?: string;
  numberOfCTNs?: number;
  receivedCTNs?: number | null;
  startCTNNumber?: number | null;
  endCTNNumber?: number | null;
  createdAt?: string | null;
  warehouseDetail?: IWarehouseDetail;
  lotDetail?: ILotDetail;
  style?: IStyle;
  user?: IUser;
}

export const defaultValue: Readonly<ISalesDetail> = {};
