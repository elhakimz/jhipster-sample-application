import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityContactMechComponent } from 'app/entities/facility-contact-mech/facility-contact-mech.component';
import { FacilityContactMechService } from 'app/entities/facility-contact-mech/facility-contact-mech.service';
import { FacilityContactMech } from 'app/shared/model/facility-contact-mech.model';

describe('Component Tests', () => {
  describe('FacilityContactMech Management Component', () => {
    let comp: FacilityContactMechComponent;
    let fixture: ComponentFixture<FacilityContactMechComponent>;
    let service: FacilityContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityContactMechComponent],
      })
        .overrideTemplate(FacilityContactMechComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityContactMechComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityContactMechService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FacilityContactMech(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facilityContactMeches && comp.facilityContactMeches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
