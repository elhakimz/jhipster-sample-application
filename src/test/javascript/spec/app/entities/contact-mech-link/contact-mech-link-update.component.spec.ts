import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechLinkUpdateComponent } from 'app/entities/contact-mech-link/contact-mech-link-update.component';
import { ContactMechLinkService } from 'app/entities/contact-mech-link/contact-mech-link.service';
import { ContactMechLink } from 'app/shared/model/contact-mech-link.model';

describe('Component Tests', () => {
  describe('ContactMechLink Management Update Component', () => {
    let comp: ContactMechLinkUpdateComponent;
    let fixture: ComponentFixture<ContactMechLinkUpdateComponent>;
    let service: ContactMechLinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechLinkUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContactMechLinkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechLinkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechLinkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactMechLink(123);
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
        const entity = new ContactMechLink();
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
