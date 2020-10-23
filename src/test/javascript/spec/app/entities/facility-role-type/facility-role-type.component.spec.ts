import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityRoleTypeComponent } from 'app/entities/facility-role-type/facility-role-type.component';
import { FacilityRoleTypeService } from 'app/entities/facility-role-type/facility-role-type.service';
import { FacilityRoleType } from 'app/shared/model/facility-role-type.model';

describe('Component Tests', () => {
  describe('FacilityRoleType Management Component', () => {
    let comp: FacilityRoleTypeComponent;
    let fixture: ComponentFixture<FacilityRoleTypeComponent>;
    let service: FacilityRoleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityRoleTypeComponent],
      })
        .overrideTemplate(FacilityRoleTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityRoleTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityRoleTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FacilityRoleType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facilityRoleTypes && comp.facilityRoleTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
