import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityRoleType } from 'app/shared/model/facility-role-type.model';
import { FacilityRoleTypeService } from './facility-role-type.service';

@Component({
  templateUrl: './facility-role-type-delete-dialog.component.html',
})
export class FacilityRoleTypeDeleteDialogComponent {
  facilityRoleType?: IFacilityRoleType;

  constructor(
    protected facilityRoleTypeService: FacilityRoleTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityRoleTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityRoleTypeListModification');
      this.activeModal.close();
    });
  }
}
