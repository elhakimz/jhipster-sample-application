import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICasusRole } from 'app/shared/model/casus-role.model';

@Component({
  selector: 'jhi-casus-role-detail',
  templateUrl: './casus-role-detail.component.html',
})
export class CasusRoleDetailComponent implements OnInit {
  casusRole: ICasusRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ casusRole }) => (this.casusRole = casusRole));
  }

  previousState(): void {
    window.history.back();
  }
}
