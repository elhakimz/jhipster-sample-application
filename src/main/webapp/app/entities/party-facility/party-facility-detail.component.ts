import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyFacility } from 'app/shared/model/party-facility.model';

@Component({
  selector: 'jhi-party-facility-detail',
  templateUrl: './party-facility-detail.component.html',
})
export class PartyFacilityDetailComponent implements OnInit {
  partyFacility: IPartyFacility | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyFacility }) => (this.partyFacility = partyFacility));
  }

  previousState(): void {
    window.history.back();
  }
}
