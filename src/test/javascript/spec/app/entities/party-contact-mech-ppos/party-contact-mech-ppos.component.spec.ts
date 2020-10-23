import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyContactMechPposComponent } from 'app/entities/party-contact-mech-ppos/party-contact-mech-ppos.component';
import { PartyContactMechPposService } from 'app/entities/party-contact-mech-ppos/party-contact-mech-ppos.service';
import { PartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';

describe('Component Tests', () => {
  describe('PartyContactMechPpos Management Component', () => {
    let comp: PartyContactMechPposComponent;
    let fixture: ComponentFixture<PartyContactMechPposComponent>;
    let service: PartyContactMechPposService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyContactMechPposComponent],
      })
        .overrideTemplate(PartyContactMechPposComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyContactMechPposComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyContactMechPposService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyContactMechPpos(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyContactMechPpos && comp.partyContactMechPpos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
