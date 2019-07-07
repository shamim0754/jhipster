import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostCategory } from 'app/shared/model/post-category.model';

@Component({
  selector: 'jhi-post-category-detail',
  templateUrl: './post-category-detail.component.html'
})
export class PostCategoryDetailComponent implements OnInit {
  postCategory: IPostCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ postCategory }) => {
      this.postCategory = postCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
