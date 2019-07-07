import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from 'app/shared';
import {
  PostCategoryComponent,
  PostCategoryDetailComponent,
  PostCategoryUpdateComponent,
  PostCategoryDeletePopupComponent,
  PostCategoryDeleteDialogComponent,
  postCategoryRoute,
  postCategoryPopupRoute
} from './';

const ENTITY_STATES = [...postCategoryRoute, ...postCategoryPopupRoute];

@NgModule({
  imports: [BlogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PostCategoryComponent,
    PostCategoryDetailComponent,
    PostCategoryUpdateComponent,
    PostCategoryDeleteDialogComponent,
    PostCategoryDeletePopupComponent
  ],
  entryComponents: [
    PostCategoryComponent,
    PostCategoryUpdateComponent,
    PostCategoryDeleteDialogComponent,
    PostCategoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogPostCategoryModule {}
