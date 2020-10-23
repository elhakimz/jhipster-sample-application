import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyFacilityComponent } from 'app/entities/party-facility/party-facility.component';
import { PartyFacilityService } from 'app/entities/party-facility/party-facility.service';
import { PartyFacility } from 'app/shared/model/party-facility.model';

describe('Component Tests', () => {
  describe('PartyFacility Management Component', () => {
    let comp: PartyFacilityComponent;
    let fixture: ComponentFixture<PartyFacilityComponent>;
    let service: PartyFacilityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyFacilityComponent],
      })
        .overrideTemplate(PartyFacilityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyFacilityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyFacilityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyFacility(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyFacilities && comp.partyFacilities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
