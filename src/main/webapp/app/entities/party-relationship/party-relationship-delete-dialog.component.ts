import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelationshipService } from './party-relationship.service';

@Component({
  templateUrl: './party-relationship-delete-dialog.component.html',
})
export class PartyRelationshipDeleteDialogComponent {
  partyRelationship?: IPartyRelationship;

  constructor(
    protected partyRelationshipService: PartyRelationshipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyRelationshipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyRelationshipListModification');
      this.activeModal.close();
    });
  }
}
