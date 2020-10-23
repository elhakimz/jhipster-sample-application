import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechComponent } from 'app/entities/contact-mech/contact-mech.component';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { ContactMech } from 'app/shared/model/contact-mech.model';

describe('Component Tests', () => {
  describe('ContactMech Management Component', () => {
    let comp: ContactMechComponent;
    let fixture: ComponentFixture<ContactMechComponent>;
    let service: ContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechComponent],
      })
        .overrideTemplate(ContactMechComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactMech(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactMeches && comp.contactMeches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
