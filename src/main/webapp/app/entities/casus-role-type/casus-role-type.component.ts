import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICasusRoleType } from 'app/shared/model/casus-role-type.model';
import { CasusRoleTypeService } from './casus-role-type.service';
import { CasusRoleTypeDeleteDialogComponent } from './casus-role-type-delete-dialog.component';

@Component({
  selector: 'jhi-casus-role-type',
  templateUrl: './casus-role-type.component.html',
})
export class CasusRoleTypeComponent implements OnInit, OnDestroy {
  casusRoleTypes?: ICasusRoleType[];
  eventSubscriber?: Subscription;

  constructor(
    protected casusRoleTypeService: CasusRoleTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.casusRoleTypeService.query().subscribe((res: HttpResponse<ICasusRoleType[]>) => (this.casusRoleTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCasusRoleTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICasusRoleType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCasusRoleTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('casusRoleTypeListModification', () => this.loadAll());
  }

  delete(casusRoleType: ICasusRoleType): void {
    const modalRef = this.modalService.open(CasusRoleTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.casusRoleType = casusRoleType;
  }
}
