import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyContact } from 'app/shared/model/party-contact.model';
import { PartyContactService } from './party-contact.service';
import { PartyContactDeleteDialogComponent } from './party-contact-delete-dialog.component';

@Component({
  selector: 'jhi-party-contact',
  templateUrl: './party-contact.component.html',
})
export class PartyContactComponent implements OnInit, OnDestroy {
  partyContacts?: IPartyContact[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyContactService: PartyContactService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyContactService.query().subscribe((res: HttpResponse<IPartyContact[]>) => (this.partyContacts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyContacts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyContact): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyContacts(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyContactListModification', () => this.loadAll());
  }

  delete(partyContact: IPartyContact): void {
    const modalRef = this.modalService.open(PartyContactDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyContact = partyContact;
  }
}
