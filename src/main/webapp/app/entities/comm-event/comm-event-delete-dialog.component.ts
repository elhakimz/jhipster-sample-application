import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from './comm-event.service';

@Component({
  templateUrl: './comm-event-delete-dialog.component.html',
})
export class CommEventDeleteDialogComponent {
  commEvent?: ICommEvent;

  constructor(protected commEventService: CommEventService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commEventListModification');
      this.activeModal.close();
    });
  }
}
