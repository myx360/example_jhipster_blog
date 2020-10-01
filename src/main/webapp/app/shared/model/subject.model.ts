export interface ISubject {
  id?: number;
  name?: string;
  postId?: number;
  blogId?: number;
}

export const defaultValue: Readonly<ISubject> = {};
