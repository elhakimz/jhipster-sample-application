import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';
import { ContactMechPposTypeService } from './contact-mech-ppos-type.service';
import { ContactMechPposTypeDeleteDialogComponent } from './contact-mech-ppos-type-delete-dialog.component';

@Component({
  selector: 'jhi-contact-mech-ppos-type',
  templateUrl: './contact-mech-ppos-type.component.html',
})
export class ContactMechPposTypeComponent implements OnInit, OnDestroy {
  contactMechPposTypes?: IContactMechPposType[];
  eventSubscriber?: Subscription;

  constructor(
    protected contactMechPposTypeService: ContactMechPposTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contactMechPposTypeService
      .query()
      .subscribe((res: HttpResponse<IContactMechPposType[]>) => (this.contactMechPposTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContactMechPposTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContactMechPposType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactMechPposTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactMechPposTypeListModification', () => this.loadAll());
  }

  delete(contactMechPposType: IContactMechPposType): void {
    const modalRef = this.modalService.open(ContactMechPposTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactMechPposType = contactMechPposType;
  }
}
