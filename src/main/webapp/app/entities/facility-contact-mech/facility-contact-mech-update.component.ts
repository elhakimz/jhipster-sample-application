import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacilityContactMech, FacilityContactMech } from 'app/shared/model/facility-contact-mech.model';
import { FacilityContactMechService } from './facility-contact-mech.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { ICommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from 'app/entities/comm-event/comm-event.service';

type SelectableEntity = IContactMech | IFacility | ICommEvent;

@Component({
  selector: 'jhi-facility-contact-mech-update',
  templateUrl: './facility-contact-mech-update.component.html',
})
export class FacilityContactMechUpdateComponent implements OnInit {
  isSaving = false;
  contactmeches: IContactMech[] = [];
  facilities: IFacility[] = [];
  commevents: ICommEvent[] = [];

  editForm = this.fb.group({
    id: [],
    contactMechanism: [],
    facility: [],
    communicationEvent: [],
  });

  constructor(
    protected facilityContactMechService: FacilityContactMechService,
    protected contactMechService: ContactMechService,
    protected facilityService: FacilityService,
    protected commEventService: CommEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityContactMech }) => {
      this.updateForm(facilityContactMech);

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.commEventService.query().subscribe((res: HttpResponse<ICommEvent[]>) => (this.commevents = res.body || []));
    });
  }

  updateForm(facilityContactMech: IFacilityContactMech): void {
    this.editForm.patchValue({
      id: facilityContactMech.id,
      contactMechanism: facilityContactMech.contactMechanism,
      facility: facilityContactMech.facility,
      communicationEvent: facilityContactMech.communicationEvent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityContactMech = this.createFromForm();
    if (facilityContactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityContactMechService.update(facilityContactMech));
    } else {
      this.subscribeToSaveResponse(this.facilityContactMechService.create(facilityContactMech));
    }
  }

  private createFromForm(): IFacilityContactMech {
    return {
      ...new FacilityContactMech(),
      id: this.editForm.get(['id'])!.value,
      contactMechanism: this.editForm.get(['contactMechanism'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      communicationEvent: this.editForm.get(['communicationEvent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityContactMech>>): void {
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
