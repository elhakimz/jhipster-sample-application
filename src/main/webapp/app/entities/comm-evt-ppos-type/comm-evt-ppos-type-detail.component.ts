import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';

@Component({
  selector: 'jhi-comm-evt-ppos-type-detail',
  templateUrl: './comm-evt-ppos-type-detail.component.html',
})
export class CommEvtPposTypeDetailComponent implements OnInit {
  commEvtPposType: ICommEvtPposType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commEvtPposType }) => (this.commEvtPposType = commEvtPposType));
  }

  previousState(): void {
    window.history.back();
  }
}
