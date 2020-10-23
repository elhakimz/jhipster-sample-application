import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommWorkEffortUpdateComponent } from 'app/entities/comm-work-effort/comm-work-effort-update.component';
import { CommWorkEffortService } from 'app/entities/comm-work-effort/comm-work-effort.service';
import { CommWorkEffort } from 'app/shared/model/comm-work-effort.model';

describe('Component Tests', () => {
  describe('CommWorkEffort Management Update Component', () => {
    let comp: CommWorkEffortUpdateComponent;
    let fixture: ComponentFixture<CommWorkEffortUpdateComponent>;
    let service: CommWorkEffortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommWorkEffortUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommWorkEffortUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommWorkEffortUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommWorkEffortService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommWorkEffort(123);
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
        const entity = new CommWorkEffort();
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
