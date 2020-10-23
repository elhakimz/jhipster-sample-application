import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityRoleUpdateComponent } from 'app/entities/facility-role/facility-role-update.component';
import { FacilityRoleService } from 'app/entities/facility-role/facility-role.service';
import { FacilityRole } from 'app/shared/model/facility-role.model';

describe('Component Tests', () => {
  describe('FacilityRole Management Update Component', () => {
    let comp: FacilityRoleUpdateComponent;
    let fixture: ComponentFixture<FacilityRoleUpdateComponent>;
    let service: FacilityRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityRoleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityRole(123);
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
        const entity = new FacilityRole();
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
