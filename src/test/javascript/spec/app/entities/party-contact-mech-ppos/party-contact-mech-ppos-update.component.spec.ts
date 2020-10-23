import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactMechPposUpdateComponent } from 'app/entities/party-contact-mech-ppos/party-contact-mech-ppos-update.component';
import { PartyContactMechPposService } from 'app/entities/party-contact-mech-ppos/party-contact-mech-ppos.service';
import { PartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';

describe('Component Tests', () => {
  describe('PartyContactMechPpos Management Update Component', () => {
    let comp: PartyContactMechPposUpdateComponent;
    let fixture: ComponentFixture<PartyContactMechPposUpdateComponent>;
    let service: PartyContactMechPposService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactMechPposUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyContactMechPposUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyContactMechPposUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyContactMechPposService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyContactMechPpos(123);
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
        const entity = new PartyContactMechPpos();
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
