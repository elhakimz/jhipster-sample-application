import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEvtPposTypeComponent } from 'app/entities/comm-evt-ppos-type/comm-evt-ppos-type.component';
import { CommEvtPposTypeService } from 'app/entities/comm-evt-ppos-type/comm-evt-ppos-type.service';
import { CommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';

describe('Component Tests', () => {
  describe('CommEvtPposType Management Component', () => {
    let comp: CommEvtPposTypeComponent;
    let fixture: ComponentFixture<CommEvtPposTypeComponent>;
    let service: CommEvtPposTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEvtPposTypeComponent],
      })
        .overrideTemplate(CommEvtPposTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommEvtPposTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommEvtPposTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommEvtPposType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commEvtPposTypes && comp.commEvtPposTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
