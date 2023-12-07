import { IUser } from 'app/shared/model/user.model';
import { CurrentZone } from 'app/shared/model/enumerations/current-zone.model';

export interface IComment {
  id?: number;
  comment?: string;
  status?: keyof typeof CurrentZone | null;
  zoneId?: number | null;
  createdAt?: string;
  user?: IUser;
}

export const defaultValue: Readonly<IComment> = {};
