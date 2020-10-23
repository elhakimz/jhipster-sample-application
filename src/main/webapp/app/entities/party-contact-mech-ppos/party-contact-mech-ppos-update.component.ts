import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyContactMechPpos, PartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';
import { PartyContactMechPposService } from './party-contact-mech-ppos.service';
import { IPartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { PartyContactMechService } from 'app/entities/party-contact-mech/party-contact-mech.service';
import { IContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';
import { ContactMechPposTypeService } from 'app/entities/contact-mech-ppos-type/contact-mech-ppos-type.service';

type SelectableEntity = IPartyContactMech | IContactMechPposType;

@Component({
  selector: 'jhi-party-contact-mech-ppos-update',
  templateUrl: './party-contact-mech-ppos-update.component.html',
})
export class PartyContactMechPposUpdateComponent implements OnInit {
  isSaving = false;
  partycontactmeches: IPartyContactMech[] = [];
  contactmechppostypes: IContactMechPposType[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    partyContactMechanism: [],
    contactMechanismPurposeType: [],
  });

  constructor(
    protected partyContactMechPposService: PartyContactMechPposService,
    protected partyContactMechService: PartyContactMechService,
    protected contactMechPposTypeService: ContactMechPposTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContactMechPpos }) => {
      this.updateForm(partyContactMechPpos);

      this.partyContactMechService
        .query()
        .subscribe((res: HttpResponse<IPartyContactMech[]>) => (this.partycontactmeches = res.body || []));

      this.contactMechPposTypeService
        .query()
        .subscribe((res: HttpResponse<IContactMechPposType[]>) => (this.contactmechppostypes = res.body || []));
    });
  }

  updateForm(partyContactMechPpos: IPartyContactMechPpos): void {
    this.editForm.patchValue({
      id: partyContactMechPpos.id,
      fromDate: partyContactMechPpos.fromDate,
      thruDate: partyContactMechPpos.thruDate,
      partyContactMechanism: partyContactMechPpos.partyContactMechanism,
      contactMechanismPurposeType: partyContactMechPpos.contactMechanismPurposeType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyContactMechPpos = this.createFromForm();
    if (partyContactMechPpos.id !== undefined) {
      this.subscribeToSaveResponse(this.partyContactMechPposService.update(partyContactMechPpos));
    } else {
      this.subscribeToSaveResponse(this.partyContactMechPposService.create(partyContactMechPpos));
    }
  }

  private createFromForm(): IPartyContactMechPpos {
    return {
      ...new PartyContactMechPpos(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      partyContactMechanism: this.editForm.get(['partyContactMechanism'])!.value,
      contactMechanismPurposeType: this.editForm.get(['contactMechanismPurposeType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyContactMechPpos>>): void {
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
