import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CommEventUpdateComponent } from 'app/entities/comm-event/comm-event-update.component';
import { CommEventService } from 'app/entities/comm-event/comm-event.service';
import { CommEvent } from 'app/shared/model/comm-event.model';

describe('Component Tests', () => {
  describe('CommEvent Management Update Component', () => {
    let comp: CommEventUpdateComponent;
    let fixture: ComponentFixture<CommEventUpdateComponent>;
    let service: CommEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CommEventUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommEvent(123);
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
        const entity = new CommEvent();
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
