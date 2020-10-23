import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEvtPposTypeUpdateComponent } from 'app/entities/comm-evt-ppos-type/comm-evt-ppos-type-update.component';
import { CommEvtPposTypeService } from 'app/entities/comm-evt-ppos-type/comm-evt-ppos-type.service';
import { CommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';

describe('Component Tests', () => {
  describe('CommEvtPposType Management Update Component', () => {
    let comp: CommEvtPposTypeUpdateComponent;
    let fixture: ComponentFixture<CommEvtPposTypeUpdateComponent>;
    let service: CommEvtPposTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEvtPposTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommEvtPposTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommEvtPposTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommEvtPposTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommEvtPposType(123);
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
        const entity = new CommEvtPposType();
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
