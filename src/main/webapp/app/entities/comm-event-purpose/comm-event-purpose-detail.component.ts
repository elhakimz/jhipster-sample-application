import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommEventPurpose } from 'app/shared/model/comm-event-purpose.model';

@Component({
  selector: 'jhi-comm-event-purpose-detail',
  templateUrl: './comm-event-purpose-detail.component.html',
})
export class CommEventPurposeDetailComponent implements OnInit {
  commEventPurpose: ICommEventPurpose | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commEventPurpose }) => (this.commEventPurpose = commEventPurpose));
  }

  previousState(): void {
    window.history.back();
  }
}
