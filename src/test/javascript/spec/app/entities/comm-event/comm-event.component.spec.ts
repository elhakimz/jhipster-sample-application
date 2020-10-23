import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEventComponent } from 'app/entities/comm-event/comm-event.component';
import { CommEventService } from 'app/entities/comm-event/comm-event.service';
import { CommEvent } from 'app/shared/model/comm-event.model';

describe('Component Tests', () => {
  describe('CommEvent Management Component', () => {
    let comp: CommEventComponent;
    let fixture: ComponentFixture<CommEventComponent>;
    let service: CommEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEventComponent],
      })
        .overrideTemplate(CommEventComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommEventComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommEventService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommEvent(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commEvents && comp.commEvents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
