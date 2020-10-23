import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ContactMechPposTypeDetailComponent } from 'app/entities/contact-mech-ppos-type/contact-mech-ppos-type-detail.component';
import { ContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';

describe('Component Tests', () => {
  describe('ContactMechPposType Management Detail Component', () => {
    let comp: ContactMechPposTypeDetailComponent;
    let fixture: ComponentFixture<ContactMechPposTypeDetailComponent>;
    const route = ({ data: of({ contactMechPposType: new ContactMechPposType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ContactMechPposTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContactMechPposTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactMechPposTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contactMechPposType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactMechPposType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
