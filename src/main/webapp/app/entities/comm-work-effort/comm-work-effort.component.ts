import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommWorkEffort } from 'app/shared/model/comm-work-effort.model';
import { CommWorkEffortService } from './comm-work-effort.service';
import { CommWorkEffortDeleteDialogComponent } from './comm-work-effort-delete-dialog.component';

@Component({
  selector: 'jhi-comm-work-effort',
  templateUrl: './comm-work-effort.component.html',
})
export class CommWorkEffortComponent implements OnInit, OnDestroy {
  commWorkEfforts?: ICommWorkEffort[];
  eventSubscriber?: Subscription;

  constructor(
    protected commWorkEffortService: CommWorkEffortService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.commWorkEffortService.query().subscribe((res: HttpResponse<ICommWorkEffort[]>) => (this.commWorkEfforts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommWorkEfforts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommWorkEffort): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommWorkEfforts(): void {
    this.eventSubscriber = this.eventManager.subscribe('commWorkEffortListModification', () => this.loadAll());
  }

  delete(commWorkEffort: ICommWorkEffort): void {
    const modalRef = this.modalService.open(CommWorkEffortDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commWorkEffort = commWorkEffort;
  }
}
