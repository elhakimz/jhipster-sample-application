import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacilityRoleComponent } from 'app/entities/facility-role/facility-role.component';
import { FacilityRoleService } from 'app/entities/facility-role/facility-role.service';
import { FacilityRole } from 'app/shared/model/facility-role.model';

describe('Component Tests', () => {
  describe('FacilityRole Management Component', () => {
    let comp: FacilityRoleComponent;
    let fixture: ComponentFixture<FacilityRoleComponent>;
    let service: FacilityRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacilityRoleComponent],
      })
        .overrideTemplate(FacilityRoleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityRoleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityRoleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FacilityRole(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facilityRoles && comp.facilityRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
