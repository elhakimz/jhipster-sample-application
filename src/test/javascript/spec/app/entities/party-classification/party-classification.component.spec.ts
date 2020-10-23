import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyClassificationComponent } from 'app/entities/party-classification/party-classification.component';
import { PartyClassificationService } from 'app/entities/party-classification/party-classification.service';
import { PartyClassification } from 'app/shared/model/party-classification.model';

describe('Component Tests', () => {
  describe('PartyClassification Management Component', () => {
    let comp: PartyClassificationComponent;
    let fixture: ComponentFixture<PartyClassificationComponent>;
    let service: PartyClassificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyClassificationComponent],
      })
        .overrideTemplate(PartyClassificationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyClassificationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyClassificationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyClassification(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyClassifications && comp.partyClassifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
