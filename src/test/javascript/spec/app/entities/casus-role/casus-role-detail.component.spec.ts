import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusRoleDetailComponent } from 'app/entities/casus-role/casus-role-detail.component';
import { CasusRole } from 'app/shared/model/casus-role.model';

describe('Component Tests', () => {
  describe('CasusRole Management Detail Component', () => {
    let comp: CasusRoleDetailComponent;
    let fixture: ComponentFixture<CasusRoleDetailComponent>;
    const route = ({ data: of({ casusRole: new CasusRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CasusRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CasusRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load casusRole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.casusRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
