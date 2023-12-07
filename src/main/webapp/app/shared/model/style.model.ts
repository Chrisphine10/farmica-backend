import { IUser } from 'app/shared/model/user.model';
import { Grade } from 'app/shared/model/enumerations/grade.model';

export interface IStyle {
  id?: number;
  name?: string;
  grade?: keyof typeof Grade;
  code?: string;
  user?: IUser;
}

export const defaultValue: Readonly<IStyle> = {};
