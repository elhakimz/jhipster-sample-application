import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityRoleTypeDetailComponent } from 'app/entities/facility-role-type/facility-role-type-detail.component';
import { FacilityRoleType } from 'app/shared/model/facility-role-type.model';

describe('Component Tests', () => {
  describe('FacilityRoleType Management Detail Component', () => {
    let comp: FacilityRoleTypeDetailComponent;
    let fixture: ComponentFixture<FacilityRoleTypeDetailComponent>;
    const route = ({ data: of({ facilityRoleType: new FacilityRoleType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityRoleTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityRoleTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityRoleTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityRoleType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityRoleType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
