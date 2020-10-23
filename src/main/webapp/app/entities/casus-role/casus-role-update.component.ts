import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICasusRole, CasusRole } from 'app/shared/model/casus-role.model';
import { CasusRoleService } from './casus-role.service';
import { ICasusRoleType } from 'app/shared/model/casus-role-type.model';
import { CasusRoleTypeService } from 'app/entities/casus-role-type/casus-role-type.service';

@Component({
  selector: 'jhi-casus-role-update',
  templateUrl: './casus-role-update.component.html',
})
export class CasusRoleUpdateComponent implements OnInit {
  isSaving = false;
  casusroletypes: ICasusRoleType[] = [];

  editForm = this.fb.group({
    id: [],
    roleType: [],
  });

  constructor(
    protected casusRoleService: CasusRoleService,
    protected casusRoleTypeService: CasusRoleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ casusRole }) => {
      this.updateForm(casusRole);

      this.casusRoleTypeService.query().subscribe((res: HttpResponse<ICasusRoleType[]>) => (this.casusroletypes = res.body || []));
    });
  }

  updateForm(casusRole: ICasusRole): void {
    this.editForm.patchValue({
      id: casusRole.id,
      roleType: casusRole.roleType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const casusRole = this.createFromForm();
    if (casusRole.id !== undefined) {
      this.subscribeToSaveResponse(this.casusRoleService.update(casusRole));
    } else {
      this.subscribeToSaveResponse(this.casusRoleService.create(casusRole));
    }
  }

  private createFromForm(): ICasusRole {
    return {
      ...new CasusRole(),
      id: this.editForm.get(['id'])!.value,
      roleType: this.editForm.get(['roleType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICasusRole>>): void {
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

  trackById(index: number, item: ICasusRoleType): any {
    return item.id;
  }
}
