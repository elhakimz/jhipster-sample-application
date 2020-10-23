import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyRelationshipUpdateComponent } from 'app/entities/party-relationship/party-relationship-update.component';
import { PartyRelationshipService } from 'app/entities/party-relationship/party-relationship.service';
import { PartyRelationship } from 'app/shared/model/party-relationship.model';

describe('Component Tests', () => {
  describe('PartyRelationship Management Update Component', () => {
    let comp: PartyRelationshipUpdateComponent;
    let fixture: ComponentFixture<PartyRelationshipUpdateComponent>;
    let service: PartyRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyRelationshipUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyRelationshipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyRelationshipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyRelationshipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyRelationship(123);
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
        const entity = new PartyRelationship();
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
