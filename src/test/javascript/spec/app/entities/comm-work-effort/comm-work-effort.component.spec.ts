import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommWorkEffortComponent } from 'app/entities/comm-work-effort/comm-work-effort.component';
import { CommWorkEffortService } from 'app/entities/comm-work-effort/comm-work-effort.service';
import { CommWorkEffort } from 'app/shared/model/comm-work-effort.model';

describe('Component Tests', () => {
  describe('CommWorkEffort Management Component', () => {
    let comp: CommWorkEffortComponent;
    let fixture: ComponentFixture<CommWorkEffortComponent>;
    let service: CommWorkEffortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommWorkEffortComponent],
      })
        .overrideTemplate(CommWorkEffortComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommWorkEffortComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommWorkEffortService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommWorkEffort(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commWorkEfforts && comp.commWorkEfforts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
