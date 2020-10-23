import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICasus } from 'app/shared/model/casus.model';
import { CasusService } from './casus.service';
import { CasusDeleteDialogComponent } from './casus-delete-dialog.component';

@Component({
  selector: 'jhi-casus',
  templateUrl: './casus.component.html',
})
export class CasusComponent implements OnInit, OnDestroy {
  casuses?: ICasus[];
  eventSubscriber?: Subscription;

  constructor(protected casusService: CasusService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.casusService.query().subscribe((res: HttpResponse<ICasus[]>) => (this.casuses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCasuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICasus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCasuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('casusListModification', () => this.loadAll());
  }

  delete(casus: ICasus): void {
    const modalRef = this.modalService.open(CasusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.casus = casus;
  }
}
