import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacilityRoleType, FacilityRoleType } from 'app/shared/model/facility-role-type.model';
import { FacilityRoleTypeService } from './facility-role-type.service';

@Component({
  selector: 'jhi-facility-role-type-update',
  templateUrl: './facility-role-type-update.component.html',
})
export class FacilityRoleTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
  });

  constructor(
    protected facilityRoleTypeService: FacilityRoleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityRoleType }) => {
      this.updateForm(facilityRoleType);
    });
  }

  updateForm(facilityRoleType: IFacilityRoleType): void {
    this.editForm.patchValue({
      id: facilityRoleType.id,
      code: facilityRoleType.code,
      description: facilityRoleType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityRoleType = this.createFromForm();
    if (facilityRoleType.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityRoleTypeService.update(facilityRoleType));
    } else {
      this.subscribeToSaveResponse(this.facilityRoleTypeService.create(facilityRoleType));
    }
  }

  private createFromForm(): IFacilityRoleType {
    return {
      ...new FacilityRoleType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityRoleType>>): void {
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
