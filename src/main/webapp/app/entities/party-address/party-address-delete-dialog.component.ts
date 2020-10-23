import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyAddress } from 'app/shared/model/party-address.model';
import { PartyAddressService } from './party-address.service';

@Component({
  templateUrl: './party-address-delete-dialog.component.html',
})
export class PartyAddressDeleteDialogComponent {
  partyAddress?: IPartyAddress;

  constructor(
    protected partyAddressService: PartyAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyAddressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyAddressListModification');
      this.activeModal.close();
    });
  }
}
