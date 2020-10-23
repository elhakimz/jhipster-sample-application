import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICasus, Casus } from 'app/shared/model/casus.model';
import { CasusService } from './casus.service';

@Component({
  selector: 'jhi-casus-update',
  templateUrl: './casus-update.component.html',
})
export class CasusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
    start: [],
    statusType: [],
  });

  constructor(protected casusService: CasusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ casus }) => {
      if (!casus.id) {
        const today = moment().startOf('day');
        casus.start = today;
      }

      this.updateForm(casus);
    });
  }

  updateForm(casus: ICasus): void {
    this.editForm.patchValue({
      id: casus.id,
      code: casus.code,
      description: casus.description,
      start: casus.start ? casus.start.format(DATE_TIME_FORMAT) : null,
      statusType: casus.statusType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const casus = this.createFromForm();
    if (casus.id !== undefined) {
      this.subscribeToSaveResponse(this.casusService.update(casus));
    } else {
      this.subscribeToSaveResponse(this.casusService.create(casus));
    }
  }

  private createFromForm(): ICasus {
    return {
      ...new Casus(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
      start: this.editForm.get(['start'])!.value ? moment(this.editForm.get(['start'])!.value, DATE_TIME_FORMAT) : undefined,
      statusType: this.editForm.get(['statusType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICasus>>): void {
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
