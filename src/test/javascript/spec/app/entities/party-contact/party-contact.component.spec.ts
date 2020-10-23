import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactComponent } from 'app/entities/party-contact/party-contact.component';
import { PartyContactService } from 'app/entities/party-contact/party-contact.service';
import { PartyContact } from 'app/shared/model/party-contact.model';

describe('Component Tests', () => {
  describe('PartyContact Management Component', () => {
    let comp: PartyContactComponent;
    let fixture: ComponentFixture<PartyContactComponent>;
    let service: PartyContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactComponent],
      })
        .overrideTemplate(PartyContactComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyContactComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyContactService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyContact(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyContacts && comp.partyContacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
