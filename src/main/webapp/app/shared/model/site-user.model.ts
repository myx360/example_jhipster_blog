import { IBlog } from 'app/shared/model/blog.model';

export interface ISiteUser {
  id?: number;
  displayName?: string;
  moderator?: boolean;
  userId?: number;
  blogs?: IBlog[];
}

export const defaultValue: Readonly<ISiteUser> = {
  moderator: false,
};
