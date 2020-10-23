import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyAddress } from 'app/shared/model/party-address.model';
import { PartyAddressService } from './party-address.service';
import { PartyAddressDeleteDialogComponent } from './party-address-delete-dialog.component';

@Component({
  selector: 'jhi-party-address',
  templateUrl: './party-address.component.html',
})
export class PartyAddressComponent implements OnInit, OnDestroy {
  partyAddresses?: IPartyAddress[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyAddressService: PartyAddressService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyAddressService.query().subscribe((res: HttpResponse<IPartyAddress[]>) => (this.partyAddresses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyAddresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyAddress): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyAddresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyAddressListModification', () => this.loadAll());
  }

  delete(partyAddress: IPartyAddress): void {
    const modalRef = this.modalService.open(PartyAddressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyAddress = partyAddress;
  }
}
