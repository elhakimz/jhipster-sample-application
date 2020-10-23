import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyFacility } from 'app/shared/model/party-facility.model';
import { PartyFacilityService } from './party-facility.service';
import { PartyFacilityDeleteDialogComponent } from './party-facility-delete-dialog.component';

@Component({
  selector: 'jhi-party-facility',
  templateUrl: './party-facility.component.html',
})
export class PartyFacilityComponent implements OnInit, OnDestroy {
  partyFacilities?: IPartyFacility[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyFacilityService: PartyFacilityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyFacilityService.query().subscribe((res: HttpResponse<IPartyFacility[]>) => (this.partyFacilities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyFacilities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyFacility): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyFacilities(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyFacilityListModification', () => this.loadAll());
  }

  delete(partyFacility: IPartyFacility): void {
    const modalRef = this.modalService.open(PartyFacilityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyFacility = partyFacility;
  }
}
