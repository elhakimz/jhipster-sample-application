import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgreement, Agreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';
import { IPartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelationshipService } from 'app/entities/party-relationship/party-relationship.service';

@Component({
  selector: 'jhi-agreement-update',
  templateUrl: './agreement-update.component.html',
})
export class AgreementUpdateComponent implements OnInit {
  isSaving = false;
  partyrelationships: IPartyRelationship[] = [];
  agreementDateDp: any;

  editForm = this.fb.group({
    id: [],
    agreementNo: [],
    agreementDate: [],
    name: [],
    description: [],
    partyRelationship: [],
  });

  constructor(
    protected agreementService: AgreementService,
    protected partyRelationshipService: PartyRelationshipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agreement }) => {
      this.updateForm(agreement);

      this.partyRelationshipService
        .query()
        .subscribe((res: HttpResponse<IPartyRelationship[]>) => (this.partyrelationships = res.body || []));
    });
  }

  updateForm(agreement: IAgreement): void {
    this.editForm.patchValue({
      id: agreement.id,
      agreementNo: agreement.agreementNo,
      agreementDate: agreement.agreementDate,
      name: agreement.name,
      description: agreement.description,
      partyRelationship: agreement.partyRelationship,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agreement = this.createFromForm();
    if (agreement.id !== undefined) {
      this.subscribeToSaveResponse(this.agreementService.update(agreement));
    } else {
      this.subscribeToSaveResponse(this.agreementService.create(agreement));
    }
  }

  private createFromForm(): IAgreement {
    return {
      ...new Agreement(),
      id: this.editForm.get(['id'])!.value,
      agreementNo: this.editForm.get(['agreementNo'])!.value,
      agreementDate: this.editForm.get(['agreementDate'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      partyRelationship: this.editForm.get(['partyRelationship'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgreement>>): void {
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

  trackById(index: number, item: IPartyRelationship): any {
    return item.id;
  }
}
