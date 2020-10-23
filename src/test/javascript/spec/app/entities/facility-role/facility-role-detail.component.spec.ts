import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityRoleDetailComponent } from 'app/entities/facility-role/facility-role-detail.component';
import { FacilityRole } from 'app/shared/model/facility-role.model';

describe('Component Tests', () => {
  describe('FacilityRole Management Detail Component', () => {
    let comp: FacilityRoleDetailComponent;
    let fixture: ComponentFixture<FacilityRoleDetailComponent>;
    const route = ({ data: of({ facilityRole: new FacilityRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityRole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
