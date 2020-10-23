import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParty, Party } from 'app/shared/model/party.model';
import { PartyService } from './party.service';

@Component({
  selector: 'jhi-party-update',
  templateUrl: './party-update.component.html',
})
export class PartyUpdateComponent implements OnInit {
  isSaving = false;
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    firstName: [],
    lastName: [],
    birthDate: [],
    birthPlace: [],
    gender: [],
    partyType: [],
  });

  constructor(protected partyService: PartyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ party }) => {
      this.updateForm(party);
    });
  }

  updateForm(party: IParty): void {
    this.editForm.patchValue({
      id: party.id,
      name: party.name,
      firstName: party.firstName,
      lastName: party.lastName,
      birthDate: party.birthDate,
      birthPlace: party.birthPlace,
      gender: party.gender,
      partyType: party.partyType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const party = this.createFromForm();
    if (party.id !== undefined) {
      this.subscribeToSaveResponse(this.partyService.update(party));
    } else {
      this.subscribeToSaveResponse(this.partyService.create(party));
    }
  }

  private createFromForm(): IParty {
    return {
      ...new Party(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      birthPlace: this.editForm.get(['birthPlace'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      partyType: this.editForm.get(['partyType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParty>>): void {
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
