import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';
import { CommEvtPposTypeService } from './comm-evt-ppos-type.service';
import { CommEvtPposTypeDeleteDialogComponent } from './comm-evt-ppos-type-delete-dialog.component';

@Component({
  selector: 'jhi-comm-evt-ppos-type',
  templateUrl: './comm-evt-ppos-type.component.html',
})
export class CommEvtPposTypeComponent implements OnInit, OnDestroy {
  commEvtPposTypes?: ICommEvtPposType[];
  eventSubscriber?: Subscription;

  constructor(
    protected commEvtPposTypeService: CommEvtPposTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.commEvtPposTypeService.query().subscribe((res: HttpResponse<ICommEvtPposType[]>) => (this.commEvtPposTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommEvtPposTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommEvtPposType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommEvtPposTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('commEvtPposTypeListModification', () => this.loadAll());
  }

  delete(commEvtPposType: ICommEvtPposType): void {
    const modalRef = this.modalService.open(CommEvtPposTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commEvtPposType = commEvtPposType;
  }
}
