import { IBatchDetail } from 'app/shared/model/batch-detail.model';
import { IUser } from 'app/shared/model/user.model';

export interface ILotDetail {
  id?: number;
  lotNo?: number;
  batchDetail?: IBatchDetail;
  user?: IUser;
}

export const defaultValue: Readonly<ILotDetail> = {};
