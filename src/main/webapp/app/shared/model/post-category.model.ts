import { IPost } from 'app/shared/model/post.model';

export interface IPostCategory {
  id?: number;
  name?: string;
  aas?: IPost[];
}

export class PostCategory implements IPostCategory {
  constructor(public id?: number, public name?: string, public aas?: IPost[]) {}
}
