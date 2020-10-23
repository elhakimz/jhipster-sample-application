import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommWorkEffortDetailComponent } from 'app/entities/comm-work-effort/comm-work-effort-detail.component';
import { CommWorkEffort } from 'app/shared/model/comm-work-effort.model';

describe('Component Tests', () => {
  describe('CommWorkEffort Management Detail Component', () => {
    let comp: CommWorkEffortDetailComponent;
    let fixture: ComponentFixture<CommWorkEffortDetailComponent>;
    const route = ({ data: of({ commWorkEffort: new CommWorkEffort(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommWorkEffortDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommWorkEffortDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommWorkEffortDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commWorkEffort on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commWorkEffort).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
