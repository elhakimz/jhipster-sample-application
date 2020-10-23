import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyAddressComponent } from 'app/entities/party-address/party-address.component';
import { PartyAddressService } from 'app/entities/party-address/party-address.service';
import { PartyAddress } from 'app/shared/model/party-address.model';

describe('Component Tests', () => {
  describe('PartyAddress Management Component', () => {
    let comp: PartyAddressComponent;
    let fixture: ComponentFixture<PartyAddressComponent>;
    let service: PartyAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyAddressComponent],
      })
        .overrideTemplate(PartyAddressComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyAddressComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyAddressService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyAddress(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyAddresses && comp.partyAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
