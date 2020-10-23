import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechPposTypeComponent } from 'app/entities/contact-mech-ppos-type/contact-mech-ppos-type.component';
import { ContactMechPposTypeService } from 'app/entities/contact-mech-ppos-type/contact-mech-ppos-type.service';
import { ContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';

describe('Component Tests', () => {
  describe('ContactMechPposType Management Component', () => {
    let comp: ContactMechPposTypeComponent;
    let fixture: ComponentFixture<ContactMechPposTypeComponent>;
    let service: ContactMechPposTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechPposTypeComponent],
      })
        .overrideTemplate(ContactMechPposTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechPposTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechPposTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactMechPposType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactMechPposTypes && comp.contactMechPposTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
