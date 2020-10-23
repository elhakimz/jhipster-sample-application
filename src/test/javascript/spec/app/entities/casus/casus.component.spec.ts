import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CasusComponent } from 'app/entities/casus/casus.component';
import { CasusService } from 'app/entities/casus/casus.service';
import { Casus } from 'app/shared/model/casus.model';

describe('Component Tests', () => {
  describe('Casus Management Component', () => {
    let comp: CasusComponent;
    let fixture: ComponentFixture<CasusComponent>;
    let service: CasusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CasusComponent],
      })
        .overrideTemplate(CasusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CasusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CasusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Casus(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.casuses && comp.casuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
