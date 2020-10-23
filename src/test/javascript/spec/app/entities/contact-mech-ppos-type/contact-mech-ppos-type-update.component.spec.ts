import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechPposTypeUpdateComponent } from 'app/entities/contact-mech-ppos-type/contact-mech-ppos-type-update.component';
import { ContactMechPposTypeService } from 'app/entities/contact-mech-ppos-type/contact-mech-ppos-type.service';
import { ContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';

describe('Component Tests', () => {
  describe('ContactMechPposType Management Update Component', () => {
    let comp: ContactMechPposTypeUpdateComponent;
    let fixture: ComponentFixture<ContactMechPposTypeUpdateComponent>;
    let service: ContactMechPposTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechPposTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContactMechPposTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechPposTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechPposTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactMechPposType(123);
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
        const entity = new ContactMechPposType();
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
