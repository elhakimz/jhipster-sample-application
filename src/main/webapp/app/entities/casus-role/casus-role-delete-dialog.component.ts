import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICasusRole } from 'app/shared/model/casus-role.model';
import { CasusRoleService } from './casus-role.service';

@Component({
  templateUrl: './casus-role-delete-dialog.component.html',
})
export class CasusRoleDeleteDialogComponent {
  casusRole?: ICasusRole;

  constructor(protected casusRoleService: CasusRoleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.casusRoleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('casusRoleListModification');
      this.activeModal.close();
    });
  }
}
