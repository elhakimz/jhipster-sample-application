import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from './work-effort.service';
import { WorkEffortDeleteDialogComponent } from './work-effort-delete-dialog.component';

@Component({
  selector: 'jhi-work-effort',
  templateUrl: './work-effort.component.html',
})
export class WorkEffortComponent implements OnInit, OnDestroy {
  workEfforts?: IWorkEffort[];
  eventSubscriber?: Subscription;

  constructor(protected workEffortService: WorkEffortService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.workEffortService.query().subscribe((res: HttpResponse<IWorkEffort[]>) => (this.workEfforts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkEfforts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkEffort): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkEfforts(): void {
    this.eventSubscriber = this.eventManager.subscribe('workEffortListModification', () => this.loadAll());
  }

  delete(workEffort: IWorkEffort): void {
    const modalRef = this.modalService.open(WorkEffortDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workEffort = workEffort;
  }
}
