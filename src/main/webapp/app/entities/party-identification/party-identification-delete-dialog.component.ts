import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyIdentification } from 'app/shared/model/party-identification.model';
import { PartyIdentificationService } from './party-identification.service';

@Component({
  templateUrl: './party-identification-delete-dialog.component.html',
})
export class PartyIdentificationDeleteDialogComponent {
  partyIdentification?: IPartyIdentification;

  constructor(
    protected partyIdentificationService: PartyIdentificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyIdentificationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyIdentificationListModification');
      this.activeModal.close();
    });
  }
}
