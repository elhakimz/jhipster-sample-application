import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyRelationshipComponent } from 'app/entities/party-relationship/party-relationship.component';
import { PartyRelationshipService } from 'app/entities/party-relationship/party-relationship.service';
import { PartyRelationship } from 'app/shared/model/party-relationship.model';

describe('Component Tests', () => {
  describe('PartyRelationship Management Component', () => {
    let comp: PartyRelationshipComponent;
    let fixture: ComponentFixture<PartyRelationshipComponent>;
    let service: PartyRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyRelationshipComponent],
      })
        .overrideTemplate(PartyRelationshipComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyRelationshipComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyRelationshipService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyRelationship(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyRelationships && comp.partyRelationships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
