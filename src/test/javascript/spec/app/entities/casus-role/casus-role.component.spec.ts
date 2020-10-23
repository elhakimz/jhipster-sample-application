import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusRoleComponent } from 'app/entities/casus-role/casus-role.component';
import { CasusRoleService } from 'app/entities/casus-role/casus-role.service';
import { CasusRole } from 'app/shared/model/casus-role.model';

describe('Component Tests', () => {
  describe('CasusRole Management Component', () => {
    let comp: CasusRoleComponent;
    let fixture: ComponentFixture<CasusRoleComponent>;
    let service: CasusRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusRoleComponent],
      })
        .overrideTemplate(CasusRoleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CasusRoleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CasusRoleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CasusRole(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.casusRoles && comp.casusRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
