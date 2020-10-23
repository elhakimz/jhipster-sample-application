import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyFacility, PartyFacility } from 'app/shared/model/party-facility.model';
import { PartyFacilityService } from './party-facility.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';

type SelectableEntity = IParty | IFacility;

@Component({
  selector: 'jhi-party-facility-update',
  templateUrl: './party-facility-update.component.html',
})
export class PartyFacilityUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  facilities: IFacility[] = [];

  editForm = this.fb.group({
    id: [],
    party: [],
    facility: [],
  });

  constructor(
    protected partyFacilityService: PartyFacilityService,
    protected partyService: PartyService,
    protected facilityService: FacilityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyFacility }) => {
      this.updateForm(partyFacility);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));
    });
  }

  updateForm(partyFacility: IPartyFacility): void {
    this.editForm.patchValue({
      id: partyFacility.id,
      party: partyFacility.party,
      facility: partyFacility.facility,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyFacility = this.createFromForm();
    if (partyFacility.id !== undefined) {
      this.subscribeToSaveResponse(this.partyFacilityService.update(partyFacility));
    } else {
      this.subscribeToSaveResponse(this.partyFacilityService.create(partyFacility));
    }
  }

  private createFromForm(): IPartyFacility {
    return {
      ...new PartyFacility(),
      id: this.editForm.get(['id'])!.value,
      party: this.editForm.get(['party'])!.value,
      facility: this.editForm.get(['facility'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyFacility>>): void {
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
