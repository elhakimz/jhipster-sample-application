import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyFacilityDetailComponent } from 'app/entities/party-facility/party-facility-detail.component';
import { PartyFacility } from 'app/shared/model/party-facility.model';

describe('Component Tests', () => {
  describe('PartyFacility Management Detail Component', () => {
    let comp: PartyFacilityDetailComponent;
    let fixture: ComponentFixture<PartyFacilityDetailComponent>;
    const route = ({ data: of({ partyFacility: new PartyFacility(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyFacilityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyFacilityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyFacilityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyFacility on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyFacility).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
