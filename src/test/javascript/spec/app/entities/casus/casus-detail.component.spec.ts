import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusDetailComponent } from 'app/entities/casus/casus-detail.component';
import { Casus } from 'app/shared/model/casus.model';

describe('Component Tests', () => {
  describe('Casus Management Detail Component', () => {
    let comp: CasusDetailComponent;
    let fixture: ComponentFixture<CasusDetailComponent>;
    const route = ({ data: of({ casus: new Casus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CasusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CasusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load casus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.casus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
