import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRoleType, RoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';

@Component({
  selector: 'jhi-role-type-update',
  templateUrl: './role-type-update.component.html',
})
export class RoleTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
  });

  constructor(protected roleTypeService: RoleTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleType }) => {
      this.updateForm(roleType);
    });
  }

  updateForm(roleType: IRoleType): void {
    this.editForm.patchValue({
      id: roleType.id,
      name: roleType.name,
      description: roleType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleType = this.createFromForm();
    if (roleType.id !== undefined) {
      this.subscribeToSaveResponse(this.roleTypeService.update(roleType));
    } else {
      this.subscribeToSaveResponse(this.roleTypeService.create(roleType));
    }
  }

  private createFromForm(): IRoleType {
    return {
      ...new RoleType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleType>>): void {
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
