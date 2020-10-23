import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyAddress, PartyAddress } from 'app/shared/model/party-address.model';
import { PartyAddressService } from './party-address.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

@Component({
  selector: 'jhi-party-address-update',
  templateUrl: './party-address-update.component.html',
})
export class PartyAddressUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    addressLine1: [],
    addressLine2: [],
    city: [],
    party: [],
  });

  constructor(
    protected partyAddressService: PartyAddressService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyAddress }) => {
      this.updateForm(partyAddress);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(partyAddress: IPartyAddress): void {
    this.editForm.patchValue({
      id: partyAddress.id,
      addressLine1: partyAddress.addressLine1,
      addressLine2: partyAddress.addressLine2,
      city: partyAddress.city,
      party: partyAddress.party,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyAddress = this.createFromForm();
    if (partyAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.partyAddressService.update(partyAddress));
    } else {
      this.subscribeToSaveResponse(this.partyAddressService.create(partyAddress));
    }
  }

  private createFromForm(): IPartyAddress {
    return {
      ...new PartyAddress(),
      id: this.editForm.get(['id'])!.value,
      addressLine1: this.editForm.get(['addressLine1'])!.value,
      addressLine2: this.editForm.get(['addressLine2'])!.value,
      city: this.editForm.get(['city'])!.value,
      party: this.editForm.get(['party'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyAddress>>): void {
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

  trackById(index: number, item: IParty): any {
    return item.id;
  }
}
