import { IUser } from 'app/shared/model/user.model';

export interface IRegion {
  id?: number;
  name?: string;
  code?: string;
  user?: IUser;
}

export const defaultValue: Readonly<IRegion> = {};
