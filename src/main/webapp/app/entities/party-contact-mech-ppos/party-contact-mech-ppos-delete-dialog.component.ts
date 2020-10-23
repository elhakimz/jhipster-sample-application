import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';
import { PartyContactMechPposService } from './party-contact-mech-ppos.service';

@Component({
  templateUrl: './party-contact-mech-ppos-delete-dialog.component.html',
})
export class PartyContactMechPposDeleteDialogComponent {
  partyContactMechPpos?: IPartyContactMechPpos;

  constructor(
    protected partyContactMechPposService: PartyContactMechPposService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyContactMechPposService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyContactMechPposListModification');
      this.activeModal.close();
    });
  }
}
