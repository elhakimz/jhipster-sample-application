import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';
import { CommEvtPposTypeService } from './comm-evt-ppos-type.service';

@Component({
  templateUrl: './comm-evt-ppos-type-delete-dialog.component.html',
})
export class CommEvtPposTypeDeleteDialogComponent {
  commEvtPposType?: ICommEvtPposType;

  constructor(
    protected commEvtPposTypeService: CommEvtPposTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commEvtPposTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commEvtPposTypeListModification');
      this.activeModal.close();
    });
  }
}
