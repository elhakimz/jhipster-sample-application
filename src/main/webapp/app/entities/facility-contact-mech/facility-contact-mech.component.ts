import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFacilityContactMech } from 'app/shared/model/facility-contact-mech.model';
import { FacilityContactMechService } from './facility-contact-mech.service';
import { FacilityContactMechDeleteDialogComponent } from './facility-contact-mech-delete-dialog.component';

@Component({
  selector: 'jhi-facility-contact-mech',
  templateUrl: './facility-contact-mech.component.html',
})
export class FacilityContactMechComponent implements OnInit, OnDestroy {
  facilityContactMeches?: IFacilityContactMech[];
  eventSubscriber?: Subscription;

  constructor(
    protected facilityContactMechService: FacilityContactMechService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.facilityContactMechService
      .query()
      .subscribe((res: HttpResponse<IFacilityContactMech[]>) => (this.facilityContactMeches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFacilityContactMeches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFacilityContactMech): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFacilityContactMeches(): void {
    this.eventSubscriber = this.eventManager.subscribe('facilityContactMechListModification', () => this.loadAll());
  }

  delete(facilityContactMech: IFacilityContactMech): void {
    const modalRef = this.modalService.open(FacilityContactMechDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.facilityContactMech = facilityContactMech;
  }
}
