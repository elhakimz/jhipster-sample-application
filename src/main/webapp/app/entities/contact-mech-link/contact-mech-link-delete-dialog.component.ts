import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactMechLink } from 'app/shared/model/contact-mech-link.model';
import { ContactMechLinkService } from './contact-mech-link.service';

@Component({
  templateUrl: './contact-mech-link-delete-dialog.component.html',
})
export class ContactMechLinkDeleteDialogComponent {
  contactMechLink?: IContactMechLink;

  constructor(
    protected contactMechLinkService: ContactMechLinkService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactMechLinkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactMechLinkListModification');
      this.activeModal.close();
    });
  }
}
