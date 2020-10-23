import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyFacility } from 'app/shared/model/party-facility.model';
import { PartyFacilityService } from './party-facility.service';

@Component({
  templateUrl: './party-facility-delete-dialog.component.html',
})
export class PartyFacilityDeleteDialogComponent {
  partyFacility?: IPartyFacility;

  constructor(
    protected partyFacilityService: PartyFacilityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyFacilityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyFacilityListModification');
      this.activeModal.close();
    });
  }
}
