import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommWorkEffort, CommWorkEffort } from 'app/shared/model/comm-work-effort.model';
import { CommWorkEffortService } from './comm-work-effort.service';
import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { ICommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from 'app/entities/comm-event/comm-event.service';

type SelectableEntity = IWorkEffort | ICommEvent;

@Component({
  selector: 'jhi-comm-work-effort-update',
  templateUrl: './comm-work-effort-update.component.html',
})
export class CommWorkEffortUpdateComponent implements OnInit {
  isSaving = false;
  workefforts: IWorkEffort[] = [];
  commevents: ICommEvent[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    workEffort: [],
    communicationEvent: [],
  });

  constructor(
    protected commWorkEffortService: CommWorkEffortService,
    protected workEffortService: WorkEffortService,
    protected commEventService: CommEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commWorkEffort }) => {
      this.updateForm(commWorkEffort);

      this.workEffortService.query().subscribe((res: HttpResponse<IWorkEffort[]>) => (this.workefforts = res.body || []));

      this.commEventService.query().subscribe((res: HttpResponse<ICommEvent[]>) => (this.commevents = res.body || []));
    });
  }

  updateForm(commWorkEffort: ICommWorkEffort): void {
    this.editForm.patchValue({
      id: commWorkEffort.id,
      description: commWorkEffort.description,
      workEffort: commWorkEffort.workEffort,
      communicationEvent: commWorkEffort.communicationEvent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commWorkEffort = this.createFromForm();
    if (commWorkEffort.id !== undefined) {
      this.subscribeToSaveResponse(this.commWorkEffortService.update(commWorkEffort));
    } else {
      this.subscribeToSaveResponse(this.commWorkEffortService.create(commWorkEffort));
    }
  }

  private createFromForm(): ICommWorkEffort {
    return {
      ...new CommWorkEffort(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      workEffort: this.editForm.get(['workEffort'])!.value,
      communicationEvent: this.editForm.get(['communicationEvent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommWorkEffort>>): void {
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
