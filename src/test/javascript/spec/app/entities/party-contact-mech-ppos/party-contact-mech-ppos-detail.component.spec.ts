import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactMechPposDetailComponent } from 'app/entities/party-contact-mech-ppos/party-contact-mech-ppos-detail.component';
import { PartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';

describe('Component Tests', () => {
  describe('PartyContactMechPpos Management Detail Component', () => {
    let comp: PartyContactMechPposDetailComponent;
    let fixture: ComponentFixture<PartyContactMechPposDetailComponent>;
    const route = ({ data: of({ partyContactMechPpos: new PartyContactMechPpos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactMechPposDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyContactMechPposDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyContactMechPposDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyContactMechPpos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyContactMechPpos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
