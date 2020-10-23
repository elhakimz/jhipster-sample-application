import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityRole } from 'app/shared/model/facility-role.model';
import { FacilityRoleService } from './facility-role.service';

@Component({
  templateUrl: './facility-role-delete-dialog.component.html',
})
export class FacilityRoleDeleteDialogComponent {
  facilityRole?: IFacilityRole;

  constructor(
    protected facilityRoleService: FacilityRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityRoleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityRoleListModification');
      this.activeModal.close();
    });
  }
}
