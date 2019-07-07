/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BlogTestModule } from '../../../test.module';
import { PostCategoryDetailComponent } from 'app/entities/post-category/post-category-detail.component';
import { PostCategory } from 'app/shared/model/post-category.model';

describe('Component Tests', () => {
  describe('PostCategory Management Detail Component', () => {
    let comp: PostCategoryDetailComponent;
    let fixture: ComponentFixture<PostCategoryDetailComponent>;
    const route = ({ data: of({ postCategory: new PostCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BlogTestModule],
        declarations: [PostCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PostCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
