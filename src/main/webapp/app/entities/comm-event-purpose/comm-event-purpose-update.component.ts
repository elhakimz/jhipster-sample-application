import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommEventPurpose, CommEventPurpose } from 'app/shared/model/comm-event-purpose.model';
import { CommEventPurposeService } from './comm-event-purpose.service';
import { ICommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from 'app/entities/comm-event/comm-event.service';
import { ICommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';
import { CommEvtPposTypeService } from 'app/entities/comm-evt-ppos-type/comm-evt-ppos-type.service';

type SelectableEntity = ICommEvent | ICommEvtPposType;

@Component({
  selector: 'jhi-comm-event-purpose-update',
  templateUrl: './comm-event-purpose-update.component.html',
})
export class CommEventPurposeUpdateComponent implements OnInit {
  isSaving = false;
  commevents: ICommEvent[] = [];
  commevtppostypes: ICommEvtPposType[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    communicationEvent: [],
    purposeType: [],
  });

  constructor(
    protected commEventPurposeService: CommEventPurposeService,
    protected commEventService: CommEventService,
    protected commEvtPposTypeService: CommEvtPposTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commEventPurpose }) => {
      this.updateForm(commEventPurpose);

      this.commEventService.query().subscribe((res: HttpResponse<ICommEvent[]>) => (this.commevents = res.body || []));

      this.commEvtPposTypeService.query().subscribe((res: HttpResponse<ICommEvtPposType[]>) => (this.commevtppostypes = res.body || []));
    });
  }

  updateForm(commEventPurpose: ICommEventPurpose): void {
    this.editForm.patchValue({
      id: commEventPurpose.id,
      description: commEventPurpose.description,
      communicationEvent: commEventPurpose.communicationEvent,
      purposeType: commEventPurpose.purposeType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commEventPurpose = this.createFromForm();
    if (commEventPurpose.id !== undefined) {
      this.subscribeToSaveResponse(this.commEventPurposeService.update(commEventPurpose));
    } else {
      this.subscribeToSaveResponse(this.commEventPurposeService.create(commEventPurpose));
    }
  }

  private createFromForm(): ICommEventPurpose {
    return {
      ...new CommEventPurpose(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      communicationEvent: this.editForm.get(['communicationEvent'])!.value,
      purposeType: this.editForm.get(['purposeType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommEventPurpose>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
