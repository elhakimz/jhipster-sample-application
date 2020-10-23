import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommEvent } from 'app/shared/model/comm-event.model';

@Component({
  selector: 'jhi-comm-event-detail',
  templateUrl: './comm-event-detail.component.html',
})
export class CommEventDetailComponent implements OnInit {
  commEvent: ICommEvent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commEvent }) => (this.commEvent = commEvent));
  }

  previousState(): void {
    window.history.back();
  }
}
