import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyContact, PartyContact } from 'app/shared/model/party-contact.model';
import { PartyContactService } from './party-contact.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

@Component({
  selector: 'jhi-party-contact-update',
  templateUrl: './party-contact-update.component.html',
})
export class PartyContactUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    contactType: [null, [Validators.required]],
    number: [],
    primary: [],
    party: [],
  });

  constructor(
    protected partyContactService: PartyContactService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContact }) => {
      this.updateForm(partyContact);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(partyContact: IPartyContact): void {
    this.editForm.patchValue({
      id: partyContact.id,
      contactType: partyContact.contactType,
      number: partyContact.number,
      primary: partyContact.primary,
      party: partyContact.party,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyContact = this.createFromForm();
    if (partyContact.id !== undefined) {
      this.subscribeToSaveResponse(this.partyContactService.update(partyContact));
    } else {
      this.subscribeToSaveResponse(this.partyContactService.create(partyContact));
    }
  }

  private createFromForm(): IPartyContact {
    return {
      ...new PartyContact(),
      id: this.editForm.get(['id'])!.value,
      contactType: this.editForm.get(['contactType'])!.value,
      number: this.editForm.get(['number'])!.value,
      primary: this.editForm.get(['primary'])!.value,
      party: this.editForm.get(['party'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyContact>>): void {
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
