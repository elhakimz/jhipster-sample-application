import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { PartyContactMechService } from './party-contact-mech.service';
import { PartyContactMechDeleteDialogComponent } from './party-contact-mech-delete-dialog.component';

@Component({
  selector: 'jhi-party-contact-mech',
  templateUrl: './party-contact-mech.component.html',
})
export class PartyContactMechComponent implements OnInit, OnDestroy {
  partyContactMeches?: IPartyContactMech[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyContactMechService: PartyContactMechService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyContactMechService.query().subscribe((res: HttpResponse<IPartyContactMech[]>) => (this.partyContactMeches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyContactMeches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyContactMech): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyContactMeches(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyContactMechListModification', () => this.loadAll());
  }

  delete(partyContactMech: IPartyContactMech): void {
    const modalRef = this.modalService.open(PartyContactMechDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyContactMech = partyContactMech;
  }
}
