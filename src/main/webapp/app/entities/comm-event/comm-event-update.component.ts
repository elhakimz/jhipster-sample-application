import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommEvent, CommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from './comm-event.service';
import { IPartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelationshipService } from 'app/entities/party-relationship/party-relationship.service';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';
import { ICasus } from 'app/shared/model/casus.model';
import { CasusService } from 'app/entities/casus/casus.service';

type SelectableEntity = IPartyRelationship | IContactMechType | ICasus;

@Component({
  selector: 'jhi-comm-event-update',
  templateUrl: './comm-event-update.component.html',
})
export class CommEventUpdateComponent implements OnInit {
  isSaving = false;
  partyrelationships: IPartyRelationship[] = [];
  contactmechtypes: IContactMechType[] = [];
  casuses: ICasus[] = [];

  editForm = this.fb.group({
    id: [],
    eventId: [],
    started: [],
    ended: [],
    note: [],
    contextOf: [],
    occursVia: [],
    casus: [],
  });

  constructor(
    protected commEventService: CommEventService,
    protected partyRelationshipService: PartyRelationshipService,
    protected contactMechTypeService: ContactMechTypeService,
    protected casusService: CasusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commEvent }) => {
      if (!commEvent.id) {
        const today = moment().startOf('day');
        commEvent.started = today;
        commEvent.ended = today;
      }

      this.updateForm(commEvent);

      this.partyRelationshipService
        .query()
        .subscribe((res: HttpResponse<IPartyRelationship[]>) => (this.partyrelationships = res.body || []));

      this.contactMechTypeService.query().subscribe((res: HttpResponse<IContactMechType[]>) => (this.contactmechtypes = res.body || []));

      this.casusService.query().subscribe((res: HttpResponse<ICasus[]>) => (this.casuses = res.body || []));
    });
  }

  updateForm(commEvent: ICommEvent): void {
    this.editForm.patchValue({
      id: commEvent.id,
      eventId: commEvent.eventId,
      started: commEvent.started ? commEvent.started.format(DATE_TIME_FORMAT) : null,
      ended: commEvent.ended ? commEvent.ended.format(DATE_TIME_FORMAT) : null,
      note: commEvent.note,
      contextOf: commEvent.contextOf,
      occursVia: commEvent.occursVia,
      casus: commEvent.casus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commEvent = this.createFromForm();
    if (commEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.commEventService.update(commEvent));
    } else {
      this.subscribeToSaveResponse(this.commEventService.create(commEvent));
    }
  }

  private createFromForm(): ICommEvent {
    return {
      ...new CommEvent(),
      id: this.editForm.get(['id'])!.value,
      eventId: this.editForm.get(['eventId'])!.value,
      started: this.editForm.get(['started'])!.value ? moment(this.editForm.get(['started'])!.value, DATE_TIME_FORMAT) : undefined,
      ended: this.editForm.get(['ended'])!.value ? moment(this.editForm.get(['ended'])!.value, DATE_TIME_FORMAT) : undefined,
      note: this.editForm.get(['note'])!.value,
      contextOf: this.editForm.get(['contextOf'])!.value,
      occursVia: this.editForm.get(['occursVia'])!.value,
      casus: this.editForm.get(['casus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommEvent>>): void {
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
