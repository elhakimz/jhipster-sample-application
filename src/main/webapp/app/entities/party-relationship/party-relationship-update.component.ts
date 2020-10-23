import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyRelationship, PartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelationshipService } from './party-relationship.service';
import { IPartyRole } from 'app/shared/model/party-role.model';
import { PartyRoleService } from 'app/entities/party-role/party-role.service';
import { IStatusType } from 'app/shared/model/status-type.model';
import { StatusTypeService } from 'app/entities/status-type/status-type.service';

type SelectableEntity = IPartyRole | IStatusType;

@Component({
  selector: 'jhi-party-relationship-update',
  templateUrl: './party-relationship-update.component.html',
})
export class PartyRelationshipUpdateComponent implements OnInit {
  isSaving = false;
  partyroles: IPartyRole[] = [];
  statustypes: IStatusType[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    partyRelationshipType: [null, [Validators.required]],
    fromDate: [],
    thruDate: [],
    name: [],
    description: [],
    priorityType: [],
    fromPartyRole: [],
    toPartyRole: [],
    statusType: [],
  });

  constructor(
    protected partyRelationshipService: PartyRelationshipService,
    protected partyRoleService: PartyRoleService,
    protected statusTypeService: StatusTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRelationship }) => {
      this.updateForm(partyRelationship);

      this.partyRoleService.query().subscribe((res: HttpResponse<IPartyRole[]>) => (this.partyroles = res.body || []));

      this.statusTypeService.query().subscribe((res: HttpResponse<IStatusType[]>) => (this.statustypes = res.body || []));
    });
  }

  updateForm(partyRelationship: IPartyRelationship): void {
    this.editForm.patchValue({
      id: partyRelationship.id,
      partyRelationshipType: partyRelationship.partyRelationshipType,
      fromDate: partyRelationship.fromDate,
      thruDate: partyRelationship.thruDate,
      name: partyRelationship.name,
      description: partyRelationship.description,
      priorityType: partyRelationship.priorityType,
      fromPartyRole: partyRelationship.fromPartyRole,
      toPartyRole: partyRelationship.toPartyRole,
      statusType: partyRelationship.statusType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyRelationship = this.createFromForm();
    if (partyRelationship.id !== undefined) {
      this.subscribeToSaveResponse(this.partyRelationshipService.update(partyRelationship));
    } else {
      this.subscribeToSaveResponse(this.partyRelationshipService.create(partyRelationship));
    }
  }

  private createFromForm(): IPartyRelationship {
    return {
      ...new PartyRelationship(),
      id: this.editForm.get(['id'])!.value,
      partyRelationshipType: this.editForm.get(['partyRelationshipType'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      priorityType: this.editForm.get(['priorityType'])!.value,
      fromPartyRole: this.editForm.get(['fromPartyRole'])!.value,
      toPartyRole: this.editForm.get(['toPartyRole'])!.value,
      statusType: this.editForm.get(['statusType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyRelationship>>): void {
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
