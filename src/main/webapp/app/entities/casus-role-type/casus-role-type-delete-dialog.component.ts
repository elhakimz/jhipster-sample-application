import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICasusRoleType } from 'app/shared/model/casus-role-type.model';
import { CasusRoleTypeService } from './casus-role-type.service';

@Component({
  templateUrl: './casus-role-type-delete-dialog.component.html',
})
export class CasusRoleTypeDeleteDialogComponent {
  casusRoleType?: ICasusRoleType;

  constructor(
    protected casusRoleTypeService: CasusRoleTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.casusRoleTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('casusRoleTypeListModification');
      this.activeModal.close();
    });
  }
}
