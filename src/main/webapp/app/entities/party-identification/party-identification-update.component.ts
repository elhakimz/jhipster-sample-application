import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyIdentification, PartyIdentification } from 'app/shared/model/party-identification.model';
import { PartyIdentificationService } from './party-identification.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

@Component({
  selector: 'jhi-party-identification-update',
  templateUrl: './party-identification-update.component.html',
})
export class PartyIdentificationUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  validDateDp: any;

  editForm = this.fb.group({
    id: [],
    identificationType: [null, [Validators.required]],
    identNo: [],
    validDate: [],
    party: [],
  });

  constructor(
    protected partyIdentificationService: PartyIdentificationService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyIdentification }) => {
      this.updateForm(partyIdentification);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(partyIdentification: IPartyIdentification): void {
    this.editForm.patchValue({
      id: partyIdentification.id,
      identificationType: partyIdentification.identificationType,
      identNo: partyIdentification.identNo,
      validDate: partyIdentification.validDate,
      party: partyIdentification.party,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyIdentification = this.createFromForm();
    if (partyIdentification.id !== undefined) {
      this.subscribeToSaveResponse(this.partyIdentificationService.update(partyIdentification));
    } else {
      this.subscribeToSaveResponse(this.partyIdentificationService.create(partyIdentification));
    }
  }

  private createFromForm(): IPartyIdentification {
    return {
      ...new PartyIdentification(),
      id: this.editForm.get(['id'])!.value,
      identificationType: this.editForm.get(['identificationType'])!.value,
      identNo: this.editForm.get(['identNo'])!.value,
      validDate: this.editForm.get(['validDate'])!.value,
      party: this.editForm.get(['party'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyIdentification>>): void {
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
