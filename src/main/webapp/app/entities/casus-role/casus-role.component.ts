import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICasusRole } from 'app/shared/model/casus-role.model';
import { CasusRoleService } from './casus-role.service';
import { CasusRoleDeleteDialogComponent } from './casus-role-delete-dialog.component';

@Component({
  selector: 'jhi-casus-role',
  templateUrl: './casus-role.component.html',
})
export class CasusRoleComponent implements OnInit, OnDestroy {
  casusRoles?: ICasusRole[];
  eventSubscriber?: Subscription;

  constructor(protected casusRoleService: CasusRoleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.casusRoleService.query().subscribe((res: HttpResponse<ICasusRole[]>) => (this.casusRoles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCasusRoles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICasusRole): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCasusRoles(): void {
    this.eventSubscriber = this.eventManager.subscribe('casusRoleListModification', () => this.loadAll());
  }

  delete(casusRole: ICasusRole): void {
    const modalRef = this.modalService.open(CasusRoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.casusRole = casusRole;
  }
}
