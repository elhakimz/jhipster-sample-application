import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFacilityRole } from 'app/shared/model/facility-role.model';
import { FacilityRoleService } from './facility-role.service';
import { FacilityRoleDeleteDialogComponent } from './facility-role-delete-dialog.component';

@Component({
  selector: 'jhi-facility-role',
  templateUrl: './facility-role.component.html',
})
export class FacilityRoleComponent implements OnInit, OnDestroy {
  facilityRoles?: IFacilityRole[];
  eventSubscriber?: Subscription;

  constructor(
    protected facilityRoleService: FacilityRoleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.facilityRoleService.query().subscribe((res: HttpResponse<IFacilityRole[]>) => (this.facilityRoles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFacilityRoles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFacilityRole): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFacilityRoles(): void {
    this.eventSubscriber = this.eventManager.subscribe('facilityRoleListModification', () => this.loadAll());
  }

  delete(facilityRole: IFacilityRole): void {
    const modalRef = this.modalService.open(FacilityRoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.facilityRole = facilityRole;
  }
}
