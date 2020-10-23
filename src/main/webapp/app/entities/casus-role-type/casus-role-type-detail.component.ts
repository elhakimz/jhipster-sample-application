import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICasusRoleType } from 'app/shared/model/casus-role-type.model';

@Component({
  selector: 'jhi-casus-role-type-detail',
  templateUrl: './casus-role-type-detail.component.html',
})
export class CasusRoleTypeDetailComponent implements OnInit {
  casusRoleType: ICasusRoleType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ casusRoleType }) => (this.casusRoleType = casusRoleType));
  }

  previousState(): void {
    window.history.back();
  }
}
