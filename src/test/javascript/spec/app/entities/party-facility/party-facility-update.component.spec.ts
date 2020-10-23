import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyFacilityUpdateComponent } from 'app/entities/party-facility/party-facility-update.component';
import { PartyFacilityService } from 'app/entities/party-facility/party-facility.service';
import { PartyFacility } from 'app/shared/model/party-facility.model';

describe('Component Tests', () => {
  describe('PartyFacility Management Update Component', () => {
    let comp: PartyFacilityUpdateComponent;
    let fixture: ComponentFixture<PartyFacilityUpdateComponent>;
    let service: PartyFacilityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyFacilityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyFacilityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyFacilityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyFacilityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyFacility(123);
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
        const entity = new PartyFacility();
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
