import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPostCategory } from 'app/shared/model/post-category.model';
import { PostCategoryService } from './post-category.service';

@Component({
  selector: 'jhi-post-category-delete-dialog',
  templateUrl: './post-category-delete-dialog.component.html'
})
export class PostCategoryDeleteDialogComponent {
  postCategory: IPostCategory;

  constructor(
    protected postCategoryService: PostCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.postCategoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'postCategoryListModification',
        content: 'Deleted an postCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-post-category-delete-popup',
  template: ''
})
export class PostCategoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ postCategory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PostCategoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.postCategory = postCategory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/post-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/post-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
