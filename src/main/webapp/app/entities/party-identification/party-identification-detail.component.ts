import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyIdentification } from 'app/shared/model/party-identification.model';

@Component({
  selector: 'jhi-party-identification-detail',
  templateUrl: './party-identification-detail.component.html',
})
export class PartyIdentificationDetailComponent implements OnInit {
  partyIdentification: IPartyIdentification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyIdentification }) => (this.partyIdentification = partyIdentification));
  }

  previousState(): void {
    window.history.back();
  }
}
