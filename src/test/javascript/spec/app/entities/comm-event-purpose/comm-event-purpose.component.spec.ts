import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEventPurposeComponent } from 'app/entities/comm-event-purpose/comm-event-purpose.component';
import { CommEventPurposeService } from 'app/entities/comm-event-purpose/comm-event-purpose.service';
import { CommEventPurpose } from 'app/shared/model/comm-event-purpose.model';

describe('Component Tests', () => {
  describe('CommEventPurpose Management Component', () => {
    let comp: CommEventPurposeComponent;
    let fixture: ComponentFixture<CommEventPurposeComponent>;
    let service: CommEventPurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEventPurposeComponent],
      })
        .overrideTemplate(CommEventPurposeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommEventPurposeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommEventPurposeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommEventPurpose(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commEventPurposes && comp.commEventPurposes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
