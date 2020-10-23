import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEventPurposeDetailComponent } from 'app/entities/comm-event-purpose/comm-event-purpose-detail.component';
import { CommEventPurpose } from 'app/shared/model/comm-event-purpose.model';

describe('Component Tests', () => {
  describe('CommEventPurpose Management Detail Component', () => {
    let comp: CommEventPurposeDetailComponent;
    let fixture: ComponentFixture<CommEventPurposeDetailComponent>;
    const route = ({ data: of({ commEventPurpose: new CommEventPurpose(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEventPurposeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommEventPurposeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommEventPurposeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commEventPurpose on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commEventPurpose).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
