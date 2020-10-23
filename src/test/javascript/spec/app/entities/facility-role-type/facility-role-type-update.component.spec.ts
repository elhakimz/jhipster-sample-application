import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityRoleTypeUpdateComponent } from 'app/entities/facility-role-type/facility-role-type-update.component';
import { FacilityRoleTypeService } from 'app/entities/facility-role-type/facility-role-type.service';
import { FacilityRoleType } from 'app/shared/model/facility-role-type.model';

describe('Component Tests', () => {
  describe('FacilityRoleType Management Update Component', () => {
    let comp: FacilityRoleTypeUpdateComponent;
    let fixture: ComponentFixture<FacilityRoleTypeUpdateComponent>;
    let service: FacilityRoleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityRoleTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityRoleTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityRoleTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityRoleTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityRoleType(123);
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
        const entity = new FacilityRoleType();
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
