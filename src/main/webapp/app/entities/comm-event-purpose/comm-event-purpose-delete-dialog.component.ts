import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommEventPurpose } from 'app/shared/model/comm-event-purpose.model';
import { CommEventPurposeService } from './comm-event-purpose.service';

@Component({
  templateUrl: './comm-event-purpose-delete-dialog.component.html',
})
export class CommEventPurposeDeleteDialogComponent {
  commEventPurpose?: ICommEventPurpose;

  constructor(
    protected commEventPurposeService: CommEventPurposeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commEventPurposeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commEventPurposeListModification');
      this.activeModal.close();
    });
  }
}
