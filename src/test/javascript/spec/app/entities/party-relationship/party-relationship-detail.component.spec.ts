import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyRelationshipDetailComponent } from 'app/entities/party-relationship/party-relationship-detail.component';
import { PartyRelationship } from 'app/shared/model/party-relationship.model';

describe('Component Tests', () => {
  describe('PartyRelationship Management Detail Component', () => {
    let comp: PartyRelationshipDetailComponent;
    let fixture: ComponentFixture<PartyRelationshipDetailComponent>;
    const route = ({ data: of({ partyRelationship: new PartyRelationship(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyRelationshipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyRelationshipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyRelationshipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyRelationship on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyRelationship).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
