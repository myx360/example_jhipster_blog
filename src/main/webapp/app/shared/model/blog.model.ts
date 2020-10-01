import { IPost } from 'app/shared/model/post.model';
import { ISubject } from 'app/shared/model/subject.model';

export interface IBlog {
  id?: number;
  name?: string;
  posts?: IPost[];
  tags?: ISubject[];
  userId?: number;
}

export const defaultValue: Readonly<IBlog> = {};
