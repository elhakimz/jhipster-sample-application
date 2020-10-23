import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacilityRole, FacilityRole } from 'app/shared/model/facility-role.model';
import { FacilityRoleService } from './facility-role.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IFacilityRoleType } from 'app/shared/model/facility-role-type.model';
import { FacilityRoleTypeService } from 'app/entities/facility-role-type/facility-role-type.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';

type SelectableEntity = IParty | IFacilityRoleType | IFacility;

@Component({
  selector: 'jhi-facility-role-update',
  templateUrl: './facility-role-update.component.html',
})
export class FacilityRoleUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  facilityroletypes: IFacilityRoleType[] = [];
  facilities: IFacility[] = [];

  editForm = this.fb.group({
    id: [],
    party: [],
    facilityRoleType: [],
    facility: [],
  });

  constructor(
    protected facilityRoleService: FacilityRoleService,
    protected partyService: PartyService,
    protected facilityRoleTypeService: FacilityRoleTypeService,
    protected facilityService: FacilityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityRole }) => {
      this.updateForm(facilityRole);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.facilityRoleTypeService.query().subscribe((res: HttpResponse<IFacilityRoleType[]>) => (this.facilityroletypes = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));
    });
  }

  updateForm(facilityRole: IFacilityRole): void {
    this.editForm.patchValue({
      id: facilityRole.id,
      party: facilityRole.party,
      facilityRoleType: facilityRole.facilityRoleType,
      facility: facilityRole.facility,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityRole = this.createFromForm();
    if (facilityRole.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityRoleService.update(facilityRole));
    } else {
      this.subscribeToSaveResponse(this.facilityRoleService.create(facilityRole));
    }
  }

  private createFromForm(): IFacilityRole {
    return {
      ...new FacilityRole(),
      id: this.editForm.get(['id'])!.value,
      party: this.editForm.get(['party'])!.value,
      facilityRoleType: this.editForm.get(['facilityRoleType'])!.value,
      facility: this.editForm.get(['facility'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityRole>>): void {
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
