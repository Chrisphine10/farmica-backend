import { IUser } from 'app/shared/model/user.model';
import { IReworkDetail } from 'app/shared/model/rework-detail.model';

export interface IReworkComment {
  id?: number;
  comment?: string;
  createdAt?: string;
  user?: IUser;
  reworkDetail?: IReworkDetail;
}

export const defaultValue: Readonly<IReworkComment> = {};
