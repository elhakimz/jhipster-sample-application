import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelationshipService } from './party-relationship.service';
import { PartyRelationshipDeleteDialogComponent } from './party-relationship-delete-dialog.component';

@Component({
  selector: 'jhi-party-relationship',
  templateUrl: './party-relationship.component.html',
})
export class PartyRelationshipComponent implements OnInit, OnDestroy {
  partyRelationships?: IPartyRelationship[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyRelationshipService: PartyRelationshipService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyRelationshipService
      .query()
      .subscribe((res: HttpResponse<IPartyRelationship[]>) => (this.partyRelationships = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyRelationships();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyRelationship): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyRelationships(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyRelationshipListModification', () => this.loadAll());
  }

  delete(partyRelationship: IPartyRelationship): void {
    const modalRef = this.modalService.open(PartyRelationshipDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyRelationship = partyRelationship;
  }
}
