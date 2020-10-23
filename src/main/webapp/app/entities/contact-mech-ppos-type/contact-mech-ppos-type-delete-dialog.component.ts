import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';
import { ContactMechPposTypeService } from './contact-mech-ppos-type.service';

@Component({
  templateUrl: './contact-mech-ppos-type-delete-dialog.component.html',
})
export class ContactMechPposTypeDeleteDialogComponent {
  contactMechPposType?: IContactMechPposType;

  constructor(
    protected contactMechPposTypeService: ContactMechPposTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactMechPposTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactMechPposTypeListModification');
      this.activeModal.close();
    });
  }
}
