import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICasusRoleType, CasusRoleType } from 'app/shared/model/casus-role-type.model';
import { CasusRoleTypeService } from './casus-role-type.service';

@Component({
  selector: 'jhi-casus-role-type-update',
  templateUrl: './casus-role-type-update.component.html',
})
export class CasusRoleTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected casusRoleTypeService: CasusRoleTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ casusRoleType }) => {
      this.updateForm(casusRoleType);
    });
  }

  updateForm(casusRoleType: ICasusRoleType): void {
    this.editForm.patchValue({
      id: casusRoleType.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const casusRoleType = this.createFromForm();
    if (casusRoleType.id !== undefined) {
      this.subscribeToSaveResponse(this.casusRoleTypeService.update(casusRoleType));
    } else {
      this.subscribeToSaveResponse(this.casusRoleTypeService.create(casusRoleType));
    }
  }

  private createFromForm(): ICasusRoleType {
    return {
      ...new CasusRoleType(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICasusRoleType>>): void {
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
