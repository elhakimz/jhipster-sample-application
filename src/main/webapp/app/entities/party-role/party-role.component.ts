import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRole } from 'app/shared/model/party-role.model';
import { PartyRoleService } from './party-role.service';
import { PartyRoleDeleteDialogComponent } from './party-role-delete-dialog.component';

@Component({
  selector: 'jhi-party-role',
  templateUrl: './party-role.component.html',
})
export class PartyRoleComponent implements OnInit, OnDestroy {
  partyRoles?: IPartyRole[];
  eventSubscriber?: Subscription;

  constructor(protected partyRoleService: PartyRoleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.partyRoleService.query().subscribe((res: HttpResponse<IPartyRole[]>) => (this.partyRoles = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyRoles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyRole): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyRoles(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyRoleListModification', () => this.loadAll());
  }

  delete(partyRole: IPartyRole): void {
    const modalRef = this.modalService.open(PartyRoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyRole = partyRole;
  }
}
