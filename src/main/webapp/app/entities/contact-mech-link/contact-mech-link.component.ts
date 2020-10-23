import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactMechLink } from 'app/shared/model/contact-mech-link.model';
import { ContactMechLinkService } from './contact-mech-link.service';
import { ContactMechLinkDeleteDialogComponent } from './contact-mech-link-delete-dialog.component';

@Component({
  selector: 'jhi-contact-mech-link',
  templateUrl: './contact-mech-link.component.html',
})
export class ContactMechLinkComponent implements OnInit, OnDestroy {
  contactMechLinks?: IContactMechLink[];
  eventSubscriber?: Subscription;

  constructor(
    protected contactMechLinkService: ContactMechLinkService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contactMechLinkService.query().subscribe((res: HttpResponse<IContactMechLink[]>) => (this.contactMechLinks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContactMechLinks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContactMechLink): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactMechLinks(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactMechLinkListModification', () => this.loadAll());
  }

  delete(contactMechLink: IContactMechLink): void {
    const modalRef = this.modalService.open(ContactMechLinkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactMechLink = contactMechLink;
  }
}
