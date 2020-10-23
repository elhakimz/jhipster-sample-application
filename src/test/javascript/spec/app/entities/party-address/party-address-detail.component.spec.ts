import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyAddressDetailComponent } from 'app/entities/party-address/party-address-detail.component';
import { PartyAddress } from 'app/shared/model/party-address.model';

describe('Component Tests', () => {
  describe('PartyAddress Management Detail Component', () => {
    let comp: PartyAddressDetailComponent;
    let fixture: ComponentFixture<PartyAddressDetailComponent>;
    const route = ({ data: of({ partyAddress: new PartyAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyAddress on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
