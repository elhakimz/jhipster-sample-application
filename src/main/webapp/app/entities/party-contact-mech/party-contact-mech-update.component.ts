import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyContactMech, PartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { PartyContactMechService } from './party-contact-mech.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';

type SelectableEntity = IParty | IContactMech;

@Component({
  selector: 'jhi-party-contact-mech-update',
  templateUrl: './party-contact-mech-update.component.html',
})
export class PartyContactMechUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  contactmeches: IContactMech[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    nonSolicitation: [],
    party: [],
    contactMechanism: [],
  });

  constructor(
    protected partyContactMechService: PartyContactMechService,
    protected partyService: PartyService,
    protected contactMechService: ContactMechService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContactMech }) => {
      this.updateForm(partyContactMech);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));
    });
  }

  updateForm(partyContactMech: IPartyContactMech): void {
    this.editForm.patchValue({
      id: partyContactMech.id,
      fromDate: partyContactMech.fromDate,
      thruDate: partyContactMech.thruDate,
      nonSolicitation: partyContactMech.nonSolicitation,
      party: partyContactMech.party,
      contactMechanism: partyContactMech.contactMechanism,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyContactMech = this.createFromForm();
    if (partyContactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.partyContactMechService.update(partyContactMech));
    } else {
      this.subscribeToSaveResponse(this.partyContactMechService.create(partyContactMech));
    }
  }

  private createFromForm(): IPartyContactMech {
    return {
      ...new PartyContactMech(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      nonSolicitation: this.editForm.get(['nonSolicitation'])!.value,
      party: this.editForm.get(['party'])!.value,
      contactMechanism: this.editForm.get(['contactMechanism'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyContactMech>>): void {
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
