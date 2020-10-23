import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusRoleTypeDetailComponent } from 'app/entities/casus-role-type/casus-role-type-detail.component';
import { CasusRoleType } from 'app/shared/model/casus-role-type.model';

describe('Component Tests', () => {
  describe('CasusRoleType Management Detail Component', () => {
    let comp: CasusRoleTypeDetailComponent;
    let fixture: ComponentFixture<CasusRoleTypeDetailComponent>;
    const route = ({ data: of({ casusRoleType: new CasusRoleType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusRoleTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CasusRoleTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CasusRoleTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load casusRoleType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.casusRoleType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
