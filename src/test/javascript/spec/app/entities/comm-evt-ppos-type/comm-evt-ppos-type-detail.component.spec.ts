import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEvtPposTypeDetailComponent } from 'app/entities/comm-evt-ppos-type/comm-evt-ppos-type-detail.component';
import { CommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';

describe('Component Tests', () => {
  describe('CommEvtPposType Management Detail Component', () => {
    let comp: CommEvtPposTypeDetailComponent;
    let fixture: ComponentFixture<CommEvtPposTypeDetailComponent>;
    const route = ({ data: of({ commEvtPposType: new CommEvtPposType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEvtPposTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommEvtPposTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommEvtPposTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commEvtPposType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commEvtPposType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
