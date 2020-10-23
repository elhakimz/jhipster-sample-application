import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusRoleTypeComponent } from 'app/entities/casus-role-type/casus-role-type.component';
import { CasusRoleTypeService } from 'app/entities/casus-role-type/casus-role-type.service';
import { CasusRoleType } from 'app/shared/model/casus-role-type.model';

describe('Component Tests', () => {
  describe('CasusRoleType Management Component', () => {
    let comp: CasusRoleTypeComponent;
    let fixture: ComponentFixture<CasusRoleTypeComponent>;
    let service: CasusRoleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusRoleTypeComponent],
      })
        .overrideTemplate(CasusRoleTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CasusRoleTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CasusRoleTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CasusRoleType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.casusRoleTypes && comp.casusRoleTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
