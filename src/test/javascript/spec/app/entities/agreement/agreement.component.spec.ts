import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AgreementComponent } from 'app/entities/agreement/agreement.component';
import { AgreementService } from 'app/entities/agreement/agreement.service';
import { Agreement } from 'app/shared/model/agreement.model';

describe('Component Tests', () => {
  describe('Agreement Management Component', () => {
    let comp: AgreementComponent;
    let fixture: ComponentFixture<AgreementComponent>;
    let service: AgreementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [AgreementComponent],
      })
        .overrideTemplate(AgreementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgreementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgreementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Agreement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agreements && comp.agreements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
