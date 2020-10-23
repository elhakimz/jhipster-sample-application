import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';

@Component({
  selector: 'jhi-contact-mech-ppos-type-detail',
  templateUrl: './contact-mech-ppos-type-detail.component.html',
})
export class ContactMechPposTypeDetailComponent implements OnInit {
  contactMechPposType: IContactMechPposType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechPposType }) => (this.contactMechPposType = contactMechPposType));
  }

  previousState(): void {
    window.history.back();
  }
}
