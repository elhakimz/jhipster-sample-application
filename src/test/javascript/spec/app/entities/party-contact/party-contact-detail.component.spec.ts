import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactDetailComponent } from 'app/entities/party-contact/party-contact-detail.component';
import { PartyContact } from 'app/shared/model/party-contact.model';

describe('Component Tests', () => {
  describe('PartyContact Management Detail Component', () => {
    let comp: PartyContactDetailComponent;
    let fixture: ComponentFixture<PartyContactDetailComponent>;
    const route = ({ data: of({ partyContact: new PartyContact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyContactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyContactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyContact on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyContact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
