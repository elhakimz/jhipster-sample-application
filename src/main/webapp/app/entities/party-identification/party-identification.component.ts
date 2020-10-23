import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyIdentification } from 'app/shared/model/party-identification.model';
import { PartyIdentificationService } from './party-identification.service';
import { PartyIdentificationDeleteDialogComponent } from './party-identification-delete-dialog.component';

@Component({
  selector: 'jhi-party-identification',
  templateUrl: './party-identification.component.html',
})
export class PartyIdentificationComponent implements OnInit, OnDestroy {
  partyIdentifications?: IPartyIdentification[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyIdentificationService: PartyIdentificationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyIdentificationService
      .query()
      .subscribe((res: HttpResponse<IPartyIdentification[]>) => (this.partyIdentifications = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyIdentifications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyIdentification): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyIdentifications(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyIdentificationListModification', () => this.loadAll());
  }

  delete(partyIdentification: IPartyIdentification): void {
    const modalRef = this.modalService.open(PartyIdentificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyIdentification = partyIdentification;
  }
}
