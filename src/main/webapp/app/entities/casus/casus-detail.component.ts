import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICasus } from 'app/shared/model/casus.model';

@Component({
  selector: 'jhi-casus-detail',
  templateUrl: './casus-detail.component.html',
})
export class CasusDetailComponent implements OnInit {
  casus: ICasus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ casus }) => (this.casus = casus));
  }

  previousState(): void {
    window.history.back();
  }
}
