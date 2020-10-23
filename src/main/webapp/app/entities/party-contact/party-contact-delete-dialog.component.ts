import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyContact } from 'app/shared/model/party-contact.model';
import { PartyContactService } from './party-contact.service';

@Component({
  templateUrl: './party-contact-delete-dialog.component.html',
})
export class PartyContactDeleteDialogComponent {
  partyContact?: IPartyContact;

  constructor(
    protected partyContactService: PartyContactService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyContactService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyContactListModification');
      this.activeModal.close();
    });
  }
}
