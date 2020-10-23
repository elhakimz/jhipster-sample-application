import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { WorkEffortComponent } from 'app/entities/work-effort/work-effort.component';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { WorkEffort } from 'app/shared/model/work-effort.model';

describe('Component Tests', () => {
  describe('WorkEffort Management Component', () => {
    let comp: WorkEffortComponent;
    let fixture: ComponentFixture<WorkEffortComponent>;
    let service: WorkEffortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [WorkEffortComponent],
      })
        .overrideTemplate(WorkEffortComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WorkEffort(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.workEfforts && comp.workEfforts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
