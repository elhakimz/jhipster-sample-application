import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusRoleUpdateComponent } from 'app/entities/casus-role/casus-role-update.component';
import { CasusRoleService } from 'app/entities/casus-role/casus-role.service';
import { CasusRole } from 'app/shared/model/casus-role.model';

describe('Component Tests', () => {
  describe('CasusRole Management Update Component', () => {
    let comp: CasusRoleUpdateComponent;
    let fixture: ComponentFixture<CasusRoleUpdateComponent>;
    let service: CasusRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusRoleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CasusRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CasusRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CasusRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CasusRole(123);
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
        const entity = new CasusRole();
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
