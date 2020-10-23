import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEventDetailComponent } from 'app/entities/comm-event/comm-event-detail.component';
import { CommEvent } from 'app/shared/model/comm-event.model';

describe('Component Tests', () => {
  describe('CommEvent Management Detail Component', () => {
    let comp: CommEventDetailComponent;
    let fixture: ComponentFixture<CommEventDetailComponent>;
    const route = ({ data: of({ commEvent: new CommEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commEvent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
