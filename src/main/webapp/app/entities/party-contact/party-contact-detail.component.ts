import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyContact } from 'app/shared/model/party-contact.model';

@Component({
  selector: 'jhi-party-contact-detail',
  templateUrl: './party-contact-detail.component.html',
})
export class PartyContactDetailComponent implements OnInit {
  partyContact: IPartyContact | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContact }) => (this.partyContact = partyContact));
  }

  previousState(): void {
    window.history.back();
  }
}
