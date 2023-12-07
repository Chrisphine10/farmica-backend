import { IPackingZoneDetail } from 'app/shared/model/packing-zone-detail.model';
import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { IStyle } from 'app/shared/model/style.model';
import { IUser } from 'app/shared/model/user.model';

export interface IWarehouseDetail {
  id?: number;
  uicode?: string | null;
  warehouseDate?: string;
  numberOfCTNs?: number | null;
  receivedCTNs?: number | null;
  startCTNNumber?: number | null;
  endCTNNumber?: number | null;
  createdAt?: string | null;
  packingZoneDetail?: IPackingZoneDetail;
  lotDetail?: ILotDetail;
  style?: IStyle;
  user?: IUser;
}

export const defaultValue: Readonly<IWarehouseDetail> = {};
