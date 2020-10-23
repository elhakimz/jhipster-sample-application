import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyIdentificationComponent } from 'app/entities/party-identification/party-identification.component';
import { PartyIdentificationService } from 'app/entities/party-identification/party-identification.service';
import { PartyIdentification } from 'app/shared/model/party-identification.model';

describe('Component Tests', () => {
  describe('PartyIdentification Management Component', () => {
    let comp: PartyIdentificationComponent;
    let fixture: ComponentFixture<PartyIdentificationComponent>;
    let service: PartyIdentificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyIdentificationComponent],
      })
        .overrideTemplate(PartyIdentificationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyIdentificationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyIdentificationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyIdentification(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyIdentifications && comp.partyIdentifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
