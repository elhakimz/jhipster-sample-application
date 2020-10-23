import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommEvtPposType, CommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';
import { CommEvtPposTypeService } from './comm-evt-ppos-type.service';

@Component({
  selector: 'jhi-comm-evt-ppos-type-update',
  templateUrl: './comm-evt-ppos-type-update.component.html',
})
export class CommEvtPposTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
  });

  constructor(
    protected commEvtPposTypeService: CommEvtPposTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commEvtPposType }) => {
      this.updateForm(commEvtPposType);
    });
  }

  updateForm(commEvtPposType: ICommEvtPposType): void {
    this.editForm.patchValue({
      id: commEvtPposType.id,
      code: commEvtPposType.code,
      description: commEvtPposType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commEvtPposType = this.createFromForm();
    if (commEvtPposType.id !== undefined) {
      this.subscribeToSaveResponse(this.commEvtPposTypeService.update(commEvtPposType));
    } else {
      this.subscribeToSaveResponse(this.commEvtPposTypeService.create(commEvtPposType));
    }
  }

  private createFromForm(): ICommEvtPposType {
    return {
      ...new CommEvtPposType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommEvtPposType>>): void {
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
