import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactMechLink } from 'app/shared/model/contact-mech-link.model';

@Component({
  selector: 'jhi-contact-mech-link-detail',
  templateUrl: './contact-mech-link-detail.component.html',
})
export class ContactMechLinkDetailComponent implements OnInit {
  contactMechLink: IContactMechLink | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechLink }) => (this.contactMechLink = contactMechLink));
  }

  previousState(): void {
    window.history.back();
  }
}
