import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommWorkEffort } from 'app/shared/model/comm-work-effort.model';

@Component({
  selector: 'jhi-comm-work-effort-detail',
  templateUrl: './comm-work-effort-detail.component.html',
})
export class CommWorkEffortDetailComponent implements OnInit {
  commWorkEffort: ICommWorkEffort | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commWorkEffort }) => (this.commWorkEffort = commWorkEffort));
  }

  previousState(): void {
    window.history.back();
  }
}
