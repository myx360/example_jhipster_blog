import { Moment } from 'moment';
import { ISubject } from 'app/shared/model/subject.model';
import { PostType } from 'app/shared/model/enumerations/post-type.model';

export interface IPost {
  id?: number;
  title?: string;
  date?: string;
  type?: PostType;
  content?: string;
  pinned?: boolean;
  eventTime?: string;
  subjects?: ISubject[];
  blogId?: number;
}

export const defaultValue: Readonly<IPost> = {
  pinned: false,
};
