import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusRoleTypeUpdateComponent } from 'app/entities/casus-role-type/casus-role-type-update.component';
import { CasusRoleTypeService } from 'app/entities/casus-role-type/casus-role-type.service';
import { CasusRoleType } from 'app/shared/model/casus-role-type.model';

describe('Component Tests', () => {
  describe('CasusRoleType Management Update Component', () => {
    let comp: CasusRoleTypeUpdateComponent;
    let fixture: ComponentFixture<CasusRoleTypeUpdateComponent>;
    let service: CasusRoleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusRoleTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CasusRoleTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CasusRoleTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CasusRoleTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CasusRoleType(123);
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
        const entity = new CasusRoleType();
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
