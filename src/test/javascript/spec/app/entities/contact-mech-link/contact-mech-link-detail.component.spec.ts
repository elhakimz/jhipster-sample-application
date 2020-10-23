import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechLinkDetailComponent } from 'app/entities/contact-mech-link/contact-mech-link-detail.component';
import { ContactMechLink } from 'app/shared/model/contact-mech-link.model';

describe('Component Tests', () => {
  describe('ContactMechLink Management Detail Component', () => {
    let comp: ContactMechLinkDetailComponent;
    let fixture: ComponentFixture<ContactMechLinkDetailComponent>;
    const route = ({ data: of({ contactMechLink: new ContactMechLink(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechLinkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContactMechLinkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactMechLinkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactMechLink on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactMechLink).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
