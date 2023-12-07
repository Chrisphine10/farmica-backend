import { IRegion } from 'app/shared/model/region.model';
import { IUser } from 'app/shared/model/user.model';

export interface IBatchDetail {
  id?: number;
  batchNo?: string;
  createdAt?: string;
  drier?: number | null;
  region?: IRegion;
  user?: IUser;
}

export const defaultValue: Readonly<IBatchDetail> = {};
