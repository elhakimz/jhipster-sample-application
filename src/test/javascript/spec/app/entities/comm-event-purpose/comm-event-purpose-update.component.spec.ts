import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEventPurposeUpdateComponent } from 'app/entities/comm-event-purpose/comm-event-purpose-update.component';
import { CommEventPurposeService } from 'app/entities/comm-event-purpose/comm-event-purpose.service';
import { CommEventPurpose } from 'app/shared/model/comm-event-purpose.model';

describe('Component Tests', () => {
  describe('CommEventPurpose Management Update Component', () => {
    let comp: CommEventPurposeUpdateComponent;
    let fixture: ComponentFixture<CommEventPurposeUpdateComponent>;
    let service: CommEventPurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEventPurposeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommEventPurposeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommEventPurposeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommEventPurposeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommEventPurpose(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommEventPurpose();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
