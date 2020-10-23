import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyClassification } from 'app/shared/model/party-classification.model';
import { PartyClassificationService } from './party-classification.service';
import { PartyClassificationDeleteDialogComponent } from './party-classification-delete-dialog.component';

@Component({
  selector: 'jhi-party-classification',
  templateUrl: './party-classification.component.html',
})
export class PartyClassificationComponent implements OnInit, OnDestroy {
  partyClassifications?: IPartyClassification[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyClassificationService: PartyClassificationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyClassificationService
      .query()
      .subscribe((res: HttpResponse<IPartyClassification[]>) => (this.partyClassifications = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyClassifications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyClassification): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyClassifications(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyClassificationListModification', () => this.loadAll());
  }

  delete(partyClassification: IPartyClassification): void {
    const modalRef = this.modalService.open(PartyClassificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyClassification = partyClassification;
  }
}
