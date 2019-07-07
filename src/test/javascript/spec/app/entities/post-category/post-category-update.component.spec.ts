/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BlogTestModule } from '../../../test.module';
import { PostCategoryUpdateComponent } from 'app/entities/post-category/post-category-update.component';
import { PostCategoryService } from 'app/entities/post-category/post-category.service';
import { PostCategory } from 'app/shared/model/post-category.model';

describe('Component Tests', () => {
  describe('PostCategory Management Update Component', () => {
    let comp: PostCategoryUpdateComponent;
    let fixture: ComponentFixture<PostCategoryUpdateComponent>;
    let service: PostCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlogTestModule],
        declarations: [PostCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PostCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostCategory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostCategory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
