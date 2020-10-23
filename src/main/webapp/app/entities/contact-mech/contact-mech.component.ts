import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from './contact-mech.service';
import { ContactMechDeleteDialogComponent } from './contact-mech-delete-dialog.component';

@Component({
  selector: 'jhi-contact-mech',
  templateUrl: './contact-mech.component.html',
})
export class ContactMechComponent implements OnInit, OnDestroy {
  contactMeches?: IContactMech[];
  eventSubscriber?: Subscription;

  constructor(
    protected contactMechService: ContactMechService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactMeches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContactMeches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContactMech): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactMeches(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactMechListModification', () => this.loadAll());
  }

  delete(contactMech: IContactMech): void {
    const modalRef = this.modalService.open(ContactMechDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactMech = contactMech;
  }
}
