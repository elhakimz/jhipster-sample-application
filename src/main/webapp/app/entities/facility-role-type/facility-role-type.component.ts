import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFacilityRoleType } from 'app/shared/model/facility-role-type.model';
import { FacilityRoleTypeService } from './facility-role-type.service';
import { FacilityRoleTypeDeleteDialogComponent } from './facility-role-type-delete-dialog.component';

@Component({
  selector: 'jhi-facility-role-type',
  templateUrl: './facility-role-type.component.html',
})
export class FacilityRoleTypeComponent implements OnInit, OnDestroy {
  facilityRoleTypes?: IFacilityRoleType[];
  eventSubscriber?: Subscription;

  constructor(
    protected facilityRoleTypeService: FacilityRoleTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.facilityRoleTypeService.query().subscribe((res: HttpResponse<IFacilityRoleType[]>) => (this.facilityRoleTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFacilityRoleTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFacilityRoleType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFacilityRoleTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('facilityRoleTypeListModification', () => this.loadAll());
  }

  delete(facilityRoleType: IFacilityRoleType): void {
    const modalRef = this.modalService.open(FacilityRoleTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.facilityRoleType = facilityRoleType;
  }
}
