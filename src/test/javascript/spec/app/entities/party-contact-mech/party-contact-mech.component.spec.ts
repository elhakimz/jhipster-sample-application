import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactMechComponent } from 'app/entities/party-contact-mech/party-contact-mech.component';
import { PartyContactMechService } from 'app/entities/party-contact-mech/party-contact-mech.service';
import { PartyContactMech } from 'app/shared/model/party-contact-mech.model';

describe('Component Tests', () => {
  describe('PartyContactMech Management Component', () => {
    let comp: PartyContactMechComponent;
    let fixture: ComponentFixture<PartyContactMechComponent>;
    let service: PartyContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactMechComponent],
      })
        .overrideTemplate(PartyContactMechComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyContactMechComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyContactMechService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyContactMech(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyContactMeches && comp.partyContactMeches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
