import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechLinkComponent } from 'app/entities/contact-mech-link/contact-mech-link.component';
import { ContactMechLinkService } from 'app/entities/contact-mech-link/contact-mech-link.service';
import { ContactMechLink } from 'app/shared/model/contact-mech-link.model';

describe('Component Tests', () => {
  describe('ContactMechLink Management Component', () => {
    let comp: ContactMechLinkComponent;
    let fixture: ComponentFixture<ContactMechLinkComponent>;
    let service: ContactMechLinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechLinkComponent],
      })
        .overrideTemplate(ContactMechLinkComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechLinkComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechLinkService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactMechLink(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactMechLinks && comp.contactMechLinks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
