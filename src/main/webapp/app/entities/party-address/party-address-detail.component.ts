import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyAddress } from 'app/shared/model/party-address.model';

@Component({
  selector: 'jhi-party-address-detail',
  templateUrl: './party-address-detail.component.html',
})
export class PartyAddressDetailComponent implements OnInit {
  partyAddress: IPartyAddress | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyAddress }) => (this.partyAddress = partyAddress));
  }

  previousState(): void {
    window.history.back();
  }
}
