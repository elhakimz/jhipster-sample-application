import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICasus } from 'app/shared/model/casus.model';
import { CasusService } from './casus.service';

@Component({
  templateUrl: './casus-delete-dialog.component.html',
})
export class CasusDeleteDialogComponent {
  casus?: ICasus;

  constructor(protected casusService: CasusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.casusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('casusListModification');
      this.activeModal.close();
    });
  }
}
