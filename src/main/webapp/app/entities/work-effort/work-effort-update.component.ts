import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffort, WorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from './work-effort.service';

@Component({
  selector: 'jhi-work-effort-update',
  templateUrl: './work-effort-update.component.html',
})
export class WorkEffortUpdateComponent implements OnInit {
  isSaving = false;
  scheduledStartDp: any;
  scheduledCompletionDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: [],
    scheduledStart: [],
    scheduledCompletion: [],
    totalMoneyAllowed: [],
    totalHoursAllowed: [],
    estimatedHours: [],
    workEffortType: [],
  });

  constructor(protected workEffortService: WorkEffortService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffort }) => {
      this.updateForm(workEffort);
    });
  }

  updateForm(workEffort: IWorkEffort): void {
    this.editForm.patchValue({
      id: workEffort.id,
      code: workEffort.code,
      name: workEffort.name,
      description: workEffort.description,
      scheduledStart: workEffort.scheduledStart,
      scheduledCompletion: workEffort.scheduledCompletion,
      totalMoneyAllowed: workEffort.totalMoneyAllowed,
      totalHoursAllowed: workEffort.totalHoursAllowed,
      estimatedHours: workEffort.estimatedHours,
      workEffortType: workEffort.workEffortType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffort = this.createFromForm();
    if (workEffort.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortService.update(workEffort));
    } else {
      this.subscribeToSaveResponse(this.workEffortService.create(workEffort));
    }
  }

  private createFromForm(): IWorkEffort {
    return {
      ...new WorkEffort(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      scheduledStart: this.editForm.get(['scheduledStart'])!.value,
      scheduledCompletion: this.editForm.get(['scheduledCompletion'])!.value,
      totalMoneyAllowed: this.editForm.get(['totalMoneyAllowed'])!.value,
      totalHoursAllowed: this.editForm.get(['totalHoursAllowed'])!.value,
      estimatedHours: this.editForm.get(['estimatedHours'])!.value,
      workEffortType: this.editForm.get(['workEffortType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffort>>): void {
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
}
