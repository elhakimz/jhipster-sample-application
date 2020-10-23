import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';
import { AgreementDeleteDialogComponent } from './agreement-delete-dialog.component';

@Component({
  selector: 'jhi-agreement',
  templateUrl: './agreement.component.html',
})
export class AgreementComponent implements OnInit, OnDestroy {
  agreements?: IAgreement[];
  eventSubscriber?: Subscription;

  constructor(protected agreementService: AgreementService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.agreementService.query().subscribe((res: HttpResponse<IAgreement[]>) => (this.agreements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAgreements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAgreement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAgreements(): void {
    this.eventSubscriber = this.eventManager.subscribe('agreementListModification', () => this.loadAll());
  }

  delete(agreement: IAgreement): void {
    const modalRef = this.modalService.open(AgreementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agreement = agreement;
  }
}
