import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyIdentificationDetailComponent } from 'app/entities/party-identification/party-identification-detail.component';
import { PartyIdentification } from 'app/shared/model/party-identification.model';

describe('Component Tests', () => {
  describe('PartyIdentification Management Detail Component', () => {
    let comp: PartyIdentificationDetailComponent;
    let fixture: ComponentFixture<PartyIdentificationDetailComponent>;
    const route = ({ data: of({ partyIdentification: new PartyIdentification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyIdentificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyIdentificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyIdentificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyIdentification on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyIdentification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
