import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyIdentificationUpdateComponent } from 'app/entities/party-identification/party-identification-update.component';
import { PartyIdentificationService } from 'app/entities/party-identification/party-identification.service';
import { PartyIdentification } from 'app/shared/model/party-identification.model';

describe('Component Tests', () => {
  describe('PartyIdentification Management Update Component', () => {
    let comp: PartyIdentificationUpdateComponent;
    let fixture: ComponentFixture<PartyIdentificationUpdateComponent>;
    let service: PartyIdentificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyIdentificationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyIdentificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyIdentificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyIdentificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyIdentification(123);
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
        const entity = new PartyIdentification();
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
