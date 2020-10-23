import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyAddressUpdateComponent } from 'app/entities/party-address/party-address-update.component';
import { PartyAddressService } from 'app/entities/party-address/party-address.service';
import { PartyAddress } from 'app/shared/model/party-address.model';

describe('Component Tests', () => {
  describe('PartyAddress Management Update Component', () => {
    let comp: PartyAddressUpdateComponent;
    let fixture: ComponentFixture<PartyAddressUpdateComponent>;
    let service: PartyAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyAddressUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyAddress(123);
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
        const entity = new PartyAddress();
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
