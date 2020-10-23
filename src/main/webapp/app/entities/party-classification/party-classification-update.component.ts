import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyClassification, PartyClassification } from 'app/shared/model/party-classification.model';
import { PartyClassificationService } from './party-classification.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

@Component({
  selector: 'jhi-party-classification-update',
  templateUrl: './party-classification-update.component.html',
})
export class PartyClassificationUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    partyClassType: [],
    party: [],
  });

  constructor(
    protected partyClassificationService: PartyClassificationService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassification }) => {
      this.updateForm(partyClassification);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(partyClassification: IPartyClassification): void {
    this.editForm.patchValue({
      id: partyClassification.id,
      fromDate: partyClassification.fromDate,
      thruDate: partyClassification.thruDate,
      partyClassType: partyClassification.partyClassType,
      party: partyClassification.party,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyClassification = this.createFromForm();
    if (partyClassification.id !== undefined) {
      this.subscribeToSaveResponse(this.partyClassificationService.update(partyClassification));
    } else {
      this.subscribeToSaveResponse(this.partyClassificationService.create(partyClassification));
    }
  }

  private createFromForm(): IPartyClassification {
    return {
      ...new PartyClassification(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      partyClassType: this.editForm.get(['partyClassType'])!.value,
      party: this.editForm.get(['party'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyClassification>>): void {
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
