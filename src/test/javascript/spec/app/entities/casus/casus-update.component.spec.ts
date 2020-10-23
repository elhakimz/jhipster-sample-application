import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusUpdateComponent } from 'app/entities/casus/casus-update.component';
import { CasusService } from 'app/entities/casus/casus.service';
import { Casus } from 'app/shared/model/casus.model';

describe('Component Tests', () => {
  describe('Casus Management Update Component', () => {
    let comp: CasusUpdateComponent;
    let fixture: ComponentFixture<CasusUpdateComponent>;
    let service: CasusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CasusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CasusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CasusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Casus(123);
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
        const entity = new Casus();
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
