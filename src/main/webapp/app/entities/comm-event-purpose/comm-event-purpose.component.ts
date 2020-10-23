import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommEventPurpose } from 'app/shared/model/comm-event-purpose.model';
import { CommEventPurposeService } from './comm-event-purpose.service';
import { CommEventPurposeDeleteDialogComponent } from './comm-event-purpose-delete-dialog.component';

@Component({
  selector: 'jhi-comm-event-purpose',
  templateUrl: './comm-event-purpose.component.html',
})
export class CommEventPurposeComponent implements OnInit, OnDestroy {
  commEventPurposes?: ICommEventPurpose[];
  eventSubscriber?: Subscription;

  constructor(
    protected commEventPurposeService: CommEventPurposeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.commEventPurposeService.query().subscribe((res: HttpResponse<ICommEventPurpose[]>) => (this.commEventPurposes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommEventPurposes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommEventPurpose): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommEventPurposes(): void {
    this.eventSubscriber = this.eventManager.subscribe('commEventPurposeListModification', () => this.loadAll());
  }

  delete(commEventPurpose: ICommEventPurpose): void {
    const modalRef = this.modalService.open(CommEventPurposeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commEventPurpose = commEventPurpose;
  }
}
