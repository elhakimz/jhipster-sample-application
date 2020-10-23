import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';
import { PartyContactMechPposService } from './party-contact-mech-ppos.service';
import { PartyContactMechPposDeleteDialogComponent } from './party-contact-mech-ppos-delete-dialog.component';

@Component({
  selector: 'jhi-party-contact-mech-ppos',
  templateUrl: './party-contact-mech-ppos.component.html',
})
export class PartyContactMechPposComponent implements OnInit, OnDestroy {
  partyContactMechPpos?: IPartyContactMechPpos[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyContactMechPposService: PartyContactMechPposService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyContactMechPposService
      .query()
      .subscribe((res: HttpResponse<IPartyContactMechPpos[]>) => (this.partyContactMechPpos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyContactMechPpos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyContactMechPpos): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyContactMechPpos(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyContactMechPposListModification', () => this.loadAll());
  }

  delete(partyContactMechPpos: IPartyContactMechPpos): void {
    const modalRef = this.modalService.open(PartyContactMechPposDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyContactMechPpos = partyContactMechPpos;
  }
}
