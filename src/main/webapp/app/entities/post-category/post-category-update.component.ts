import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPostCategory, PostCategory } from 'app/shared/model/post-category.model';
import { PostCategoryService } from './post-category.service';

@Component({
  selector: 'jhi-post-category-update',
  templateUrl: './post-category-update.component.html'
})
export class PostCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected postCategoryService: PostCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ postCategory }) => {
      this.updateForm(postCategory);
    });
  }

  updateForm(postCategory: IPostCategory) {
    this.editForm.patchValue({
      id: postCategory.id,
      name: postCategory.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const postCategory = this.createFromForm();
    if (postCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.postCategoryService.update(postCategory));
    } else {
      this.subscribeToSaveResponse(this.postCategoryService.create(postCategory));
    }
  }

  private createFromForm(): IPostCategory {
    return {
      ...new PostCategory(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostCategory>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
