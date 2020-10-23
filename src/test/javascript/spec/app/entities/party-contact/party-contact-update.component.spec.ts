import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactUpdateComponent } from 'app/entities/party-contact/party-contact-update.component';
import { PartyContactService } from 'app/entities/party-contact/party-contact.service';
import { PartyContact } from 'app/shared/model/party-contact.model';

describe('Component Tests', () => {
  describe('PartyContact Management Update Component', () => {
    let comp: PartyContactUpdateComponent;
    let fixture: ComponentFixture<PartyContactUpdateComponent>;
    let service: PartyContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyContactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyContactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyContactService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyContact(123);
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
        const entity = new PartyContact();
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
