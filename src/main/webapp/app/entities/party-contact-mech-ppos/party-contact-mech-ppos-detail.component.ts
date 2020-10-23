import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';

@Component({
  selector: 'jhi-party-contact-mech-ppos-detail',
  templateUrl: './party-contact-mech-ppos-detail.component.html',
})
export class PartyContactMechPposDetailComponent implements OnInit {
  partyContactMechPpos: IPartyContactMechPpos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContactMechPpos }) => (this.partyContactMechPpos = partyContactMechPpos));
  }

  previousState(): void {
    window.history.back();
  }
}
