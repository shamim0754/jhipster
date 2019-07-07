import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PostCategory } from 'app/shared/model/post-category.model';
import { PostCategoryService } from './post-category.service';
import { PostCategoryComponent } from './post-category.component';
import { PostCategoryDetailComponent } from './post-category-detail.component';
import { PostCategoryUpdateComponent } from './post-category-update.component';
import { PostCategoryDeletePopupComponent } from './post-category-delete-dialog.component';
import { IPostCategory } from 'app/shared/model/post-category.model';

@Injectable({ providedIn: 'root' })
export class PostCategoryResolve implements Resolve<IPostCategory> {
  constructor(private service: PostCategoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPostCategory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PostCategory>) => response.ok),
        map((postCategory: HttpResponse<PostCategory>) => postCategory.body)
      );
    }
    return of(new PostCategory());
  }
}

export const postCategoryRoute: Routes = [
  {
    path: '',
    component: PostCategoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'PostCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PostCategoryDetailComponent,
    resolve: {
      postCategory: PostCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PostCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PostCategoryUpdateComponent,
    resolve: {
      postCategory: PostCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PostCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PostCategoryUpdateComponent,
    resolve: {
      postCategory: PostCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PostCategories'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const postCategoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PostCategoryDeletePopupComponent,
    resolve: {
      postCategory: PostCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PostCategories'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
