import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from './comm-event.service';
import { CommEventDeleteDialogComponent } from './comm-event-delete-dialog.component';

@Component({
  selector: 'jhi-comm-event',
  templateUrl: './comm-event.component.html',
})
export class CommEventComponent implements OnInit, OnDestroy {
  commEvents?: ICommEvent[];
  eventSubscriber?: Subscription;

  constructor(protected commEventService: CommEventService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.commEventService.query().subscribe((res: HttpResponse<ICommEvent[]>) => (this.commEvents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommEvent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('commEventListModification', () => this.loadAll());
  }

  delete(commEvent: ICommEvent): void {
    const modalRef = this.modalService.open(CommEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commEvent = commEvent;
  }
}
