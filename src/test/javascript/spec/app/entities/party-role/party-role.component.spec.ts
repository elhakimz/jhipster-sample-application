import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartyRoleComponent } from 'app/entities/party-role/party-role.component';
import { PartyRoleService } from 'app/entities/party-role/party-role.service';
import { PartyRole } from 'app/shared/model/party-role.model';

describe('Component Tests', () => {
  describe('PartyRole Management Component', () => {
    let comp: PartyRoleComponent;
    let fixture: ComponentFixture<PartyRoleComponent>;
    let service: PartyRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PartyRoleComponent],
      })
        .overrideTemplate(PartyRoleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyRoleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyRoleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartyRole(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partyRoles && comp.partyRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
