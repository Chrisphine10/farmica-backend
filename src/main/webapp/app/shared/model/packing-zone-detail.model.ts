import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { IStyle } from 'app/shared/model/style.model';
import { IUser } from 'app/shared/model/user.model';

export interface IPackingZoneDetail {
  id?: number;
  uicode?: string | null;
  pdnDate?: string;
  packageDate?: string;
  weightReceived?: number;
  weightBalance?: number;
  numberOfCTNs?: number;
  receivedCTNs?: number | null;
  startCTNNumber?: number | null;
  endCTNNumber?: number | null;
  numberOfCTNsReworked?: number | null;
  numberOfCTNsSold?: number | null;
  numberOfCTNsPacked?: number | null;
  numberOfCTNsInWarehouse?: number | null;
  createdAt?: string | null;
  lotDetail?: ILotDetail;
  style?: IStyle;
  user?: IUser;
}

export const defaultValue: Readonly<IPackingZoneDetail> = {};
