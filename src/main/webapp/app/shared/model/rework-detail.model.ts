import { IWarehouseDetail } from 'app/shared/model/warehouse-detail.model';
import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { IUser } from 'app/shared/model/user.model';
import { ReworkStatus } from 'app/shared/model/enumerations/rework-status.model';

export interface IReworkDetail {
  id?: number;
  uicode?: string | null;
  pdnDate?: string;
  reworkDate?: string;
  numberOfCTNs?: number;
  startCTNNumber?: number | null;
  endCTNNumber?: number | null;
  status?: keyof typeof ReworkStatus;
  createdAt?: string | null;
  warehouseDetail?: IWarehouseDetail;
  lotDetail?: ILotDetail;
  user?: IUser;
}

export const defaultValue: Readonly<IReworkDetail> = {};
