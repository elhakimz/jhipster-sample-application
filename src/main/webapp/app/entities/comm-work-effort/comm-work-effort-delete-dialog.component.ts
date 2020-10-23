import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommWorkEffort } from 'app/shared/model/comm-work-effort.model';
import { CommWorkEffortService } from './comm-work-effort.service';

@Component({
  templateUrl: './comm-work-effort-delete-dialog.component.html',
})
export class CommWorkEffortDeleteDialogComponent {
  commWorkEffort?: ICommWorkEffort;

  constructor(
    protected commWorkEffortService: CommWorkEffortService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commWorkEffortService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commWorkEffortListModification');
      this.activeModal.close();
    });
  }
}
