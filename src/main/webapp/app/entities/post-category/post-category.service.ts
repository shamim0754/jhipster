import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPostCategory } from 'app/shared/model/post-category.model';

type EntityResponseType = HttpResponse<IPostCategory>;
type EntityArrayResponseType = HttpResponse<IPostCategory[]>;

@Injectable({ providedIn: 'root' })
export class PostCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/post-categories';

  constructor(protected http: HttpClient) {}

  create(postCategory: IPostCategory): Observable<EntityResponseType> {
    return this.http.post<IPostCategory>(this.resourceUrl, postCategory, { observe: 'response' });
  }

  update(postCategory: IPostCategory): Observable<EntityResponseType> {
    return this.http.put<IPostCategory>(this.resourceUrl, postCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
